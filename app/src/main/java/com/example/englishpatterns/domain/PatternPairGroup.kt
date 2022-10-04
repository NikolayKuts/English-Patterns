package com.example.englishpatterns.domain

data class PatternPairGroup(
    val pairs: List<PatternPair>,
    val isChosen: Boolean = false,
)