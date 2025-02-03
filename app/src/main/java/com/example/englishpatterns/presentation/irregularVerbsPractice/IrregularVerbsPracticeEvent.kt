package com.example.englishpatterns.presentation.irregularVerbsPractice

sealed interface IrregularVerbsPracticeEvent {

    data class TextToSpeech(val text: String) : IrregularVerbsPracticeEvent
}