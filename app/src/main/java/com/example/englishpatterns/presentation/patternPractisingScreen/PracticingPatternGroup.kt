package com.example.englishpatterns.presentation.patternPractisingScreen

import com.example.englishpatterns.data.Pattern
import kotlinx.serialization.Serializable

@Serializable
data class PracticingPatternGroup(
    val patterns: List<Pattern> = emptyList(),
    val isWeaklyMemorized: Boolean = false,
    val isChosen: Boolean = false,
)