package com.example.englishpatterns.presentation.navigation

import com.example.englishpatterns.domain.RawPatternGroup
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(open val route: String) {

    @Serializable
    data object MainScreen : Screen(route = "MainScreen")

    @Serializable
    class PatternPracticingScreen(
         val rawPatternGroups: List<RawPatternGroup>,
    ) : Screen(route = "PatternPracticingScreen")

    @Serializable
    data class WebContentScreen(val url: String) : Screen(route = "WebContentScreen")
}