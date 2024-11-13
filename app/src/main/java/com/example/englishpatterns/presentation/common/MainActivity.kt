package com.example.englishpatterns.presentation.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.datastore.dataStore
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.englishpatterns.data.PatternHoldersSerializer
import com.example.englishpatterns.presentation.collectWhenStarted
import com.example.englishpatterns.presentation.navigation.AppNavGraph
import com.example.englishpatterns.presentation.navigation.Screen
import com.example.englishpatterns.presentation.patternPractisingScreen.PatternPracticingBaseViewModel
import com.example.englishpatterns.presentation.patternPractisingScreen.PatternPracticingEvent
import com.example.englishpatterns.presentation.patternPractisingScreen.PatternPracticingScreen
import com.example.englishpatterns.presentation.patternPractisingScreen.PatternPracticingViewModel
import com.example.englishpatterns.presentation.webConten.WebContentScreen
import com.example.englishpatterns.presentation.webConten.WebContentState
import com.example.englishpatterns.ui.theme.EnglishPatternsTheme
import com.lib.lokdroid.core.logD
import com.lib.lokdroid.core.logW
import kotlinx.coroutines.flow.collectLatest

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

        enableEdgeToEdge()

        setContent {
            EnglishPatternsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = EnglishPatternsTheme.colors.surface
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
                                val patternPracticingViewModel: PatternPracticingBaseViewModel =
                                    viewModel<PatternPracticingViewModel>(
                                        factory = PatternPracticingViewModel.Factory(
                                            context = application,
                                            rawPatternGroups = rawPatternGroups
                                        )
                                    )

                                LaunchedEffect(key1 = Unit) {
                                    patternPracticingViewModel.eventState.collectLatest {

                                        when (it) {
                                            is PatternPracticingEvent.RedirectionToWordHuntAppRequired -> {
//                                                startActivityWithCheck(intent = it.intent)
                                                copyToClipboard(clipboardUnit = it.clipboardUnit)
                                                navController.navigateToWebContentScreen(url = it.url)
                                            }

                                            is PatternPracticingEvent.RedirectionToKlafAppRequired -> {
                                                startActivityWithCheck(intent = it.intent)
                                            }

                                            is PatternPracticingEvent.RedirectionToGhatGptAppRequired -> {
                                                copyToClipboard(clipboardUnit = it.clipboardUnit)

                                                startActivityWithCheck(intent = it.intent)
//                                                navController.navigateToWebContentScreen(url = it.url)
                                            }

                                            is PatternPracticingEvent.RedirectionToYouGlishPageRequired -> {
//                                                startActivityWithCheck(intent = it.intent)
                                                navController.navigateToWebContentScreen(url = it.url)
                                            }
                                        }
                                    }
                                }

                                val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

                                DisposableEffect(lifecycleOwner) {
                                    lifecycleOwner.lifecycle.addObserver(
                                        patternPracticingViewModel.textAudioPlayer
                                    )

                                    onDispose {
                                        lifecycleOwner.lifecycle.removeObserver(
                                            patternPracticingViewModel.textAudioPlayer
                                        )
                                    }
                                }

                                PatternPracticingScreen(
                                    modifier = Modifier.padding(paddingValues),
                                    state = patternPracticingViewModel.state.collectAsState().value,
                                    sendAction = patternPracticingViewModel::sendAction
                                )
                            },
                            webContentScreenContent = { url ->
                                WebContentScreen(
                                    modifier = Modifier.padding(paddingValues),
                                    state = WebContentState(url = url)
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

    private fun startActivityWithCheck(intent: Intent) {
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            logW("Application not found")
        }
    }

    private fun NavController.navigateToWebContentScreen(url: String) {
        val destination = Screen.WebContentScreen(url = url)

        navigate(route = destination)
    }
}