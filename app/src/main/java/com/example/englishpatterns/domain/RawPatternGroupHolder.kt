package com.example.englishpatterns.domain

import kotlinx.serialization.Serializable

@Serializable
data class RawPatternGroupHolder(
    val rawPatternGroup: RawPatternGroup,
    val isChosen: Boolean,
)