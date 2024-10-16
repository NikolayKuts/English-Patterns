package com.example.englishpatterns.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.englishpatterns.data.PatternHoldersSerializer
import com.example.englishpatterns.ui.theme.EnglishPatternsTheme

class MainActivity : ComponentActivity() {

    private val Context.dataStore by dataStore(
        fileName = "pattern_holders",
        serializer = PatternHoldersSerializer()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel by viewModels<MainViewModel> {
            MainViewModelFactory(
                context = application,
                dataStore = dataStore
            )
        }

        setContent {
            log("set content")
            EnglishPatternsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    lifecycleScope.launchWhenStarted {
                        viewModel.state.collect { state ->
                            log(state)
                            when (state) {
                                is State.InitialState -> {}
                                is State.PatternPracticingState -> {
                                    navController.navigate(route = PatternPracticingDestination)
                                }
                            }
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = PatternListDestination
                    ) {
                        composable(PatternListDestination) {
                            PatternListScreen(
                                viewModel = viewModel,
                                onItemClick = { position, patternHolder ->
                                    viewModel.sendEvent(
                                        event = Event.ChangePatterHolderChoosingState(
                                            position = position,
                                            patternHolder = patternHolder
                                        )
                                    )
                                },
                                onStartButtonClick = {
                                    viewModel.sendEvent(event = Event.NavigateToPatternPracticing)
                                }
                            )
                        }
                        composable(route = PatternPracticingDestination) {
                            PatternPracticingScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EnglishPatternsTheme {
        Greeting("Android")
    }
}