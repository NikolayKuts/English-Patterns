package com.example.englishpatterns.presentation.patternPractisingScreen

import com.example.englishpatterns.data.common.LoadingState
import com.example.englishpatterns.domain.PracticingPatternUnit

data class PatternPracticingState(
    val practicingPatternGroups: List<PracticingPatternGroup> = emptyList(),
    val weekPracticingPatterGroup: PracticingPatternGroup = PracticingPatternGroup(),
    val currentPractisingPatternGroupUnit: PracticingPatternUnit? = null,
    val isTranslationHidden: Boolean = false,
    val isPracticingPatternGroupShuffled: Boolean = false,
    val isStoringWeekPatternEnabled: Boolean = false,
    val selectedTextInfo: LoadingState<SelectedTextInfo> = LoadingState.Non,
    val pronunciationLoadingState:  LoadingState<Unit> = LoadingState.Non,
)

data class SelectedTextInfo(
    val transcription: String = "",
    val translations: List<String> = emptyList(),
)