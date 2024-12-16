package com.example.englishpatterns.presentation.patternPractisingScreen

import android.content.Intent
import com.example.englishpatterns.data.common.ClipboardUnit

sealed interface PatternPracticingEvent {

    data class WarmupCustomTabs(
        val url: String,
        val clipboardUnit: ClipboardUnit,
    ) : PatternPracticingEvent

    data class RedirectionToWordHuntAppRequired(
        val intent: Intent,
        val url: String,
        val clipboardUnit: ClipboardUnit,
    ) : PatternPracticingEvent

    data class RedirectionToKlafAppRequired(val intent: Intent) : PatternPracticingEvent

    data class RedirectionToGhatGptAppRequired(
        val intent: Intent,
        val url: String,
        val ruClipboardUnit: ClipboardUnit,
        val enClipboardUnit: ClipboardUnit,
        val selectedClipboardUnit: ClipboardUnit,
    ) : PatternPracticingEvent

    data class RedirectionToYouGlishPageRequired(
        val intent: Intent,
        val url: String,
    ) : PatternPracticingEvent

    data class RedirectionToGoogleImagesPageRequired(
        val url: String,
        val clipboardUnit: ClipboardUnit,
    ) : PatternPracticingEvent

    data class RedirectionToWordTemplateSearchPageRequired(
        val url: String,
        val clipboardUnit: ClipboardUnit,
    ) : PatternPracticingEvent
}