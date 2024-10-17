package com.example.englishpatterns.presentation.common

import com.example.englishpatterns.domain.RawPatternGroupHolder

sealed interface MainAction {

    data class ChangePatterHolderChoosingState(
        val position: Int,
        val rawPatternGroupHolder: RawPatternGroupHolder,
    ) : MainAction

    data object NavigateToPatternPracticing : MainAction
}