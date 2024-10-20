package com.example.englishpatterns.presentation.patternPractisingScreen

import com.example.englishpatterns.domain.PatternGroupUnitState

data class PatternPracticingState(
    val patternGroupHolders: List<PatternGroupsHolder> = emptyList(),
    val currentPattern: PatternGroupUnitState? = null,
)