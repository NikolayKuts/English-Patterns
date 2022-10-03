package com.example.englishpatterns.presentation

sealed class Event {

    object DisplayMainScreen : Event()
    object NavigateToPatternPracticing : Event()
    class ChangePatterHolderChoosingState(val position: Int, val  patternHolder: PatternHolder) : Event()
}