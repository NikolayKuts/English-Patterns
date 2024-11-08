package com.example.englishpatterns.presentation.patternPractisingScreen

import com.example.englishpatterns.data.TextAudioPlayer
import com.example.englishpatterns.presentation.common.BaseViewModel

abstract class PatternPracticingBaseViewModel :
    BaseViewModel<PatternPracticingState, PatternPracticingAction, PatternPracticingEvent>() {

    abstract val textAudioPlayer: TextAudioPlayer
}