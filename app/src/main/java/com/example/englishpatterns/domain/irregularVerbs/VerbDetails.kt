package com.example.englishpatterns.domain.irregularVerbs

data class VerbDetails(
    val word: String,
    val v1: Verb,
    val v2: Verb,
    val v3: Verb,
    val type: IrregularVerbGroupType
)
