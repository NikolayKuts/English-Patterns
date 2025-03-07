package com.example.englishpatterns.data

import kotlinx.serialization.Serializable

@Serializable
data class Pattern(
    val native: String,
    val translation: String,
)

@Serializable
data class IdentifiablePattern(
    val id: Int,
    val value: Pattern
) {

    fun noId(): Pattern = Pattern(
        native = value.native,
        translation = value.translation
    )
}