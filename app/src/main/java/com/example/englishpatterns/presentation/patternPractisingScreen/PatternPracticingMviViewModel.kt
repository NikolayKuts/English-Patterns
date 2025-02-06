package com.example.englishpatterns.presentation.patternPractisingScreen

import com.example.englishpatterns.data.TextAudioPlayer
import com.example.englishpatterns.presentation.common.MviViewModel

abstract class PatternPracticingMviViewModel :
    MviViewModel<PatternPracticingState, PatternPracticingAction, PatternPracticingEvent>() {

    abstract val textAudioPlayer: TextAudioPlayer
}