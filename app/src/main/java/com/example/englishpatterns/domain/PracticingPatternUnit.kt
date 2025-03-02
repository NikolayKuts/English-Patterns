package com.example.englishpatterns.domain

import com.example.englishpatterns.data.Pattern

data class PracticingPatternUnit(
    val pattern: Pattern,
    val position: Int,
    val groupSize: Int
)