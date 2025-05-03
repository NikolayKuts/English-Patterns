package com.example.englishpatterns.domain.irregularVerbs

enum class IrregularVerbGroupType(val pattern: String) {

    Unchanging("○○○"),
    SecondChanging("○∆○"),
    FullyChanging("○∆□"),
    PartiallyConsistent("○∆∆"),
    EndChanging("○○∆"),
    Mixed("???"),
    // "○○∆",
}