package com.example.englishpatterns.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.englishpatterns.ui.theme.EnglishPatternsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel by viewModels<MainViewModel>()

        setContent {
            EnglishPatternsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    lifecycleScope.launchWhenStarted {
                        viewModel.state.collect { state ->
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