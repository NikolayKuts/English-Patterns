package com.example.englishpatterns.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.englishpatterns.domain.RawPatternGroup
import kotlin.reflect.typeOf

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    mainScreenContent: @Composable () -> Unit,
    patternPracticingScreenContent: @Composable (List<RawPatternGroup>) -> Unit,
    webContentScreenContent: @Composable (url: String) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.MainScreen
    ) {
        composable<Screen.MainScreen> { mainScreenContent() }

        composable<Screen.PatternPracticingScreen>(
            typeMap = mapOf(
                typeOf<List<RawPatternGroup>>() to CustomNavType.RawPatternGroupList
            )
        ) {
            val args = it.toRoute<Screen.PatternPracticingScreen>()

            patternPracticingScreenContent(args.rawPatternGroups)
        }

        composable<Screen.WebContentScreen> {
            val args = it.toRoute<Screen.WebContentScreen>()

            webContentScreenContent(args.url)
        }
    }
}