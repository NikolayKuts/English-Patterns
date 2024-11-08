package com.example.englishpatterns.presentation.patternPractisingScreen

import android.content.Intent

sealed interface PatternPracticingEvent {

    data class SearchSelectedTextRequired(val intent: Intent) : PatternPracticingEvent

    data class RedirectionToKlafAppRequired(val intent: Intent) : PatternPracticingEvent
}