package com.example.englishpatterns.domain

import kotlinx.serialization.Serializable

@Serializable
data class PatternGroupResContainer(
    val patternGroupResource: PatternGroupResource,
    val isChosen: Boolean,
)