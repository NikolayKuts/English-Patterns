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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

                    NavHost(
                        navController = navController,
                        startDestination = PatternListDestination
                    ) {
                        composable(PatternListDestination) {
                            PatternListScreen(
                                patternHolderSource = viewModel.patternHolders,
                                onItemClick = viewModel::changePatterHolderChoosingState,
                                onStartButtonClick = {
                                    navController.navigate(route = PatternPracticingDestination)
                                }
                            )
                        }
                        composable(route = PatternPracticingDestination) {
                            PatternPracticingScreen(patternsSource = viewModel.chosenPatterns)
                        }
                    }
                }
            }
        }
    }

    private fun NavHostController.navigateToPracticing() {

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