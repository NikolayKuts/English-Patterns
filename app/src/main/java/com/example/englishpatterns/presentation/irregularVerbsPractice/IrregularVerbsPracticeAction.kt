package com.example.englishpatterns.presentation.irregularVerbsPractice

import com.example.englishpatterns.domain.irregularVerbs.HidingMode
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType

sealed interface IrregularVerbsPracticeAction {

    data object NextVerb : IrregularVerbsPracticeAction

    data object PreviousVerb : IrregularVerbsPracticeAction

    data class ChangeVerbGroup(val type: IrregularVerbGroupType) : IrregularVerbsPracticeAction

    data object ChangeVerbHighlightMode : IrregularVerbsPracticeAction

    data object ChangeShufflingMode : IrregularVerbsPracticeAction

    data object TextToSpeech : IrregularVerbsPracticeAction

    data class ChangeHidingMode(val mode: HidingMode) : IrregularVerbsPracticeAction

    data class ChangeSubGroupSelection(
        val subGroupViewHolder: IrregularVerbsGroupViewHolder.SubGroupViewHolder
    ) : IrregularVerbsPracticeAction

    data class ManageAllSubGroupsSelection(val type: IrregularVerbGroupType) : IrregularVerbsPracticeAction

    data object ShowHiddenVerbs : IrregularVerbsPracticeAction

    data class ReturnVerbVisibilityMode(val mode: HidingMode) : IrregularVerbsPracticeAction
}