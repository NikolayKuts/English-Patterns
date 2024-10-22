package com.example.englishpatterns.domain

import com.example.englishpatterns.presentation.patternPractisingScreen.Pattern

data class PatternGroupUnitState(
    val pattern: Pattern,
    val position: Int,
    val groupSize: Int
)