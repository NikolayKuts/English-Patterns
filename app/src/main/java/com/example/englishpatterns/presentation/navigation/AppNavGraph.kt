package com.example.englishpatterns.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.englishpatterns.domain.PatternGroupResource
import kotlin.reflect.typeOf

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    mainScreenContent: @Composable () -> Unit,
    patternPracticingScreenContent: @Composable (List<PatternGroupResource>) -> Unit,
    webContentScreenContent: @Composable (url: String) -> Unit,
    irregularVerbsScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.MainScreen
    ) {
        composable<Screen.MainScreen> { mainScreenContent() }

        composable<Screen.PatternPracticingScreen>(
            typeMap = mapOf(
                typeOf<List<PatternGroupResource>>() to CustomNavType.PatternGroupResources
            )
        ) {
            val args = it.toRoute<Screen.PatternPracticingScreen>()

            patternPracticingScreenContent(args.patternGroupResources)
        }

        composable<Screen.WebContentScreen> {
            val args = it.toRoute<Screen.WebContentScreen>()

            webContentScreenContent(args.url)
        }

        composable<Screen.IrregularVerbs> { irregularVerbsScreenContent() }
    }
}