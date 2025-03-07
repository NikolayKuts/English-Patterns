package com.example.englishpatterns.domain

import com.example.englishpatterns.data.IdentifiablePattern

data class PracticingPatternUnit(
    val pattern: IdentifiablePattern,
    val position: Int,
    val groupSize: Int
)