package com.example.englishpatterns.domain

class PatternManager(
    private val patternPairGroup: PatternPairGroup? = null,
) {

    private var position: Int = -1

    fun nextPattern(): PatternPair? {
        return when {
            patternPairGroup == null -> null
            patternPairGroup.pairs.isEmpty() -> null
            patternPairGroup.pairs.lastIndex <= position -> {
                position = 0
                patternPairGroup.pairs[position]
            }
            else -> patternPairGroup.pairs[++position]
        }
    }
}