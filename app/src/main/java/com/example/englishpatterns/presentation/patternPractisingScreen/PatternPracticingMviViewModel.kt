package com.example.englishpatterns.presentation.patternPractisingScreen

import androidx.lifecycle.SavedStateHandle
import com.example.englishpatterns.data.TextAudioPlayer
import com.example.englishpatterns.presentation.common.MviViewModel

abstract class PatternPracticingMviViewModel(
    savedStateHandle: SavedStateHandle
) : MviViewModel<PatternPracticingState, PatternPracticingAction, PatternPracticingEvent>(
    savedStateHandle = savedStateHandle
) {

    abstract val textAudioPlayer: TextAudioPlayer
}