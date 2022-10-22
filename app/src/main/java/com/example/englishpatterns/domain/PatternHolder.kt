package com.example.englishpatterns.domain

import kotlinx.serialization.Serializable

@Serializable
data class PatternHolder(
    val pattern: Pattern,
    val isChosen: Boolean,
)