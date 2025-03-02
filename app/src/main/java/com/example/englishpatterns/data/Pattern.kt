package com.example.englishpatterns.data

import kotlinx.serialization.Serializable

@Serializable
data class Pattern(
    val native: String,
    val translation: String,
)