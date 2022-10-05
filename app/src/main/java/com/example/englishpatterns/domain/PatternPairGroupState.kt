package com.example.englishpatterns.domain

data class PatternPairGroupState(
    val pair: PatternPair,
    val position: Int,
    val groupSize: Int
)