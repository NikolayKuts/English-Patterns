package com.example.englishpatterns.domain

import com.example.englishpatterns.presentation.patternPractisingScreen.Pattern

data class PatternGroupUnitState(
    val pair: Pattern,
    val position: Int,
    val groupSize: Int
)