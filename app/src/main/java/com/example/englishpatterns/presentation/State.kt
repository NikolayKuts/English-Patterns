package com.example.englishpatterns.presentation

import com.example.englishpatterns.data.PatternHolders
import com.example.englishpatterns.domain.PatternPair
import com.example.englishpatterns.domain.PatternPairGroup
import com.example.englishpatterns.domain.PatternPairGroupState
import kotlinx.coroutines.flow.StateFlow

sealed class State {

    class InitialState(val patternHolderSource: StateFlow<PatternHolders?>) : State()
    class PatternPracticingState(
        val patternPairGroups: StateFlow<List<PatternPairGroup>>,
        val currentPattern: StateFlow<PatternPairGroupState?>,
    ) : State()
}
