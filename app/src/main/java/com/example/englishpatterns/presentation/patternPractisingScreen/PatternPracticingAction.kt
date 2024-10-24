package com.example.englishpatterns.presentation.patternPractisingScreen

sealed interface PatternPracticingAction {

    data class ChangePatternGroupHolderChoosingState(val position: Int) : PatternPracticingAction

    data object NextPatterPair : PatternPracticingAction

    data object ShufflePatternPairs : PatternPracticingAction

    data object ChangeAllPatternGroupHoldersSelectionState : PatternPracticingAction

    data object SelectNextPatternGroup : PatternPracticingAction

    data object AddPatternAsWeaklyMemorized : PatternPracticingAction
}