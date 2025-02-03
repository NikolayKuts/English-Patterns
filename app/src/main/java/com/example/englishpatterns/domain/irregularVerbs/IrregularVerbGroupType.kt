package com.example.englishpatterns.domain.irregularVerbs

enum class IrregularVerbGroupType(val pattern: String) {

    Unchanging("○○○"),
    PartiallyChanging("○∆○"),
    FullyChanging("○∆□"),
    PartiallyConsistent("○∆∆")
    // "○○∆",
}