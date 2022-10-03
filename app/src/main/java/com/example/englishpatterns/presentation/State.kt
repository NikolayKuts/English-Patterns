package com.example.englishpatterns.presentation

import com.example.englishpatterns.domain.Pattern
import kotlinx.coroutines.flow.StateFlow

sealed class State {

    class InitialState(val patternHolderSource: StateFlow<List<PatternHolder>>) : State()
    class PatternPracticingState(val  patternsSource: StateFlow<List<Pattern>>) : State()
}
