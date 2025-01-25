package com.example.englishpatterns.domain

import kotlinx.serialization.Serializable

@Serializable
sealed interface MarkColor {

    @Serializable
    data object Non : MarkColor

    @Serializable
    data object Accented : MarkColor

    @Serializable
    data object  Warning : MarkColor

    @Serializable
    data object Positive : MarkColor
}