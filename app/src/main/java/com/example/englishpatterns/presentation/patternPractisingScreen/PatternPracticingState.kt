package com.example.englishpatterns.presentation.patternPractisingScreen

import com.example.englishpatterns.domain.PatternGroupUnitState

data class PatternPracticingState(
    val patternGroupHolders: List<PatternGroupHolder> = emptyList(),
    val currentPattern: PatternGroupUnitState? = null,
    val isTranslationHidden: Boolean = false,
    val selectedTextInfo: SelectedTextInfo = SelectedTextInfo(),
)

data class SelectedTextInfo(
    val transcription: String = "",
)