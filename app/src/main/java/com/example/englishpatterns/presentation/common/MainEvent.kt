package com.example.englishpatterns.presentation.common

import com.example.englishpatterns.domain.PatternGroupResource

sealed interface MainEvent {

    data class PatternPracticingRequired(
        val patternGroupResources: List<PatternGroupResource>
    ) : MainEvent

    data object IrregularVerbsPracticeRequired : MainEvent
}