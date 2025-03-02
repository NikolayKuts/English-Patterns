package com.example.englishpatterns.presentation.common

import com.example.englishpatterns.domain.MarkColor
import com.example.englishpatterns.domain.PatternGroupResContainer

sealed interface MainAction {

    data class ChangePatternGroupResContainerChoosingState(
        val position: Int,
        val resContainer: PatternGroupResContainer,
    ) : MainAction

    data object NavigateToPatternPracticing : MainAction

    data class SetMarkColor(val patternIndex: Int, val markColor: MarkColor) : MainAction

    data object NavigateToIrregularVerbsPracticeScreen : MainAction
}