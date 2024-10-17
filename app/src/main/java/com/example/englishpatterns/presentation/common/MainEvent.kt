package com.example.englishpatterns.presentation.common

import com.example.englishpatterns.domain.RawPatternGroup

sealed interface MainEvent {

    data class PatternPracticingRequired(
        val rawPatternGroups: List<RawPatternGroup>
    ) : MainEvent
}