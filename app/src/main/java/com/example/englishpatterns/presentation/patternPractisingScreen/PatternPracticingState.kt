package com.example.englishpatterns.presentation.patternPractisingScreen

import com.example.englishpatterns.data.common.LoadingState
import com.example.englishpatterns.domain.PatternGroupUnitState

data class PatternPracticingState(
    val patternGroupHolders: List<PatternGroupHolder> = emptyList(),
    val currentPattern: PatternGroupUnitState? = null,
    val isTranslationHidden: Boolean = false,
    val selectedTextInfo: LoadingState<SelectedTextInfo> = LoadingState.Non,
    val pronunciationLoadingState:  LoadingState<Unit> = LoadingState.Non,
)

data class SelectedTextInfo(
    val transcription: String = "",
)