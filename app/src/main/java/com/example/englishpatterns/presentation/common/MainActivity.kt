package com.example.englishpatterns.presentation.common

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.dataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.englishpatterns.data.PatternHoldersSerializer
import com.example.englishpatterns.presentation.collectWhenStarted
import com.example.englishpatterns.presentation.navigation.AppNavGraph
import com.example.englishpatterns.presentation.navigation.Screen
import com.example.englishpatterns.presentation.patternPractisingScreen.PatternPracticingAction
import com.example.englishpatterns.presentation.patternPractisingScreen.PatternPracticingScreen
import com.example.englishpatterns.presentation.patternPractisingScreen.PatternPracticingState
import com.example.englishpatterns.presentation.patternPractisingScreen.PatternPracticingViewModel
import com.example.englishpatterns.ui.theme.EnglishPatternsTheme
import com.lib.lokdroid.core.logD

class MainActivity : ComponentActivity() {

    private val Context.dataStore by dataStore(
        fileName = "pattern_holders",
        serializer = PatternHoldersSerializer()
    )

    private val viewModel: BaseViewModel<MainState, MainAction, MainEvent> by viewModels<MainViewModel> {
        MainViewModelFactory(dataStore = dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.Transparent.toArgb(),
                darkScrim = Color.Transparent.toArgb()
            ),
        )

        setContent {
            EnglishPatternsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold { paddingValues ->
                        val navController = rememberNavController()

                        LaunchedEffect(key1 = Unit) {
                            observeEventState(navController = navController)
                        }

                        AppNavGraph(
                            navHostController = navController,
                            mainScreenContent = {
                                MainScreen(
                                    modifier = Modifier.padding(paddingValues),
                                    state = viewModel.state.collectAsState().value,
                                    sendAction = viewModel::sendAction
                                )
                            },
                            patternPracticingScreenContent = { rawPatternGroups ->
                                val patternPracticingViewModel: BaseViewModel<
                                        PatternPracticingState,
                                        PatternPracticingAction,
                                        Unit
                                        > = viewModel<PatternPracticingViewModel>(
                                    factory = PatternPracticingViewModel.Factory(
                                        context = application,
                                        rawPatternGroups = rawPatternGroups
                                    )
                                )

                                PatternPracticingScreen(
                                    modifier = Modifier.padding(paddingValues),
                                    state = patternPracticingViewModel.state.collectAsState().value,
                                    sendAction = patternPracticingViewModel::sendAction
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    private fun observeEventState(navController: NavController) {
        viewModel.eventState.collectWhenStarted(lifecycleOwner = this) { event ->
            logD("event observed: $event")

            when (event) {
                is MainEvent.PatternPracticingRequired -> {
                    val destination = Screen.PatternPracticingScreen(
                        rawPatternGroups = event.rawPatternGroups
                    )

                    navController.navigate(route = destination)
                }
            }
        }
    }
}