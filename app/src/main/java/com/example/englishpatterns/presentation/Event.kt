package com.example.englishpatterns.presentation

sealed class Event {

    object DisplayMainScreen : Event()
    object NavigateToPatternPracticing : Event()
    data class ChangePatterHolderChoosingState(val position: Int, val  patternHolder: PatternHolder) : Event()
    data class ChangePairGroupChoosingState(val position: Int) : Event()
    object NextPatterPair : Event()
    object ShufflePatternPairs : Event()
}