package com.example.englishpatterns.presentation.navigation

import com.example.englishpatterns.domain.PatternGroupResource
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(open val route: String) {

    @Serializable
    data object MainScreen : Screen(route = "MainScreen")

    @Serializable
    class PatternPracticingScreen(
        val patternGroupResources: List<PatternGroupResource>,
    ) : Screen(route = "PatternPracticingScreen")

    @Serializable
    data class WebContentScreen(val url: String) : Screen(route = "WebContentScreen")

    @Serializable
    data object IrregularVerbs : Screen(route = "IrregularVerbs")
}