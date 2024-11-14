package com.example.englishpatterns.presentation.patternPractisingScreen

data class PatternGroupHolder(
    val patterns: List<Pattern>,
    val isWeaklyMemorized: Boolean = false,
    val isChosen: Boolean = false,
)