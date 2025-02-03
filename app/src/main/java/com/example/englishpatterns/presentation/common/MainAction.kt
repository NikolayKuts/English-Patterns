package com.example.englishpatterns.presentation.common

import com.example.englishpatterns.domain.MarkColor
import com.example.englishpatterns.domain.RawPatternGroupHolder

sealed interface MainAction {

    data class ChangePatterHolderChoosingState(
        val position: Int,
        val rawPatternGroupHolder: RawPatternGroupHolder,
    ) : MainAction

    data object NavigateToPatternPracticing : MainAction

    data class SetMarkColor(val patternIndex: Int, val markColor: MarkColor) : MainAction

    data object NavigateToIrregularVerbsPracticeScreen : MainAction
}