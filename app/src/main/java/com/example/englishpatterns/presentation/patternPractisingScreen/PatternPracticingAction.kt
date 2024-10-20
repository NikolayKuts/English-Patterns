package com.example.englishpatterns.presentation.patternPractisingScreen

sealed interface PatternPracticingAction {

    data class ChangePairGroupChoosingState(val position: Int) : PatternPracticingAction

    data object NextPatterPair : PatternPracticingAction

    data object ShufflePatternPairs : PatternPracticingAction

    data object SelectAllPatternGroups : PatternPracticingAction

    data object SelectNextPatternGroup : PatternPracticingAction
}