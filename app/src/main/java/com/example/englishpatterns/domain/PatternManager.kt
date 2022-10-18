package com.example.englishpatterns.domain

class PatternManager(
    private val patternPairGroup: PatternPairGroup? = null,
) {

    private var position: Int = -1

    fun nextPattern(): PatternPairGroupState? {
        return when {
            patternPairGroup == null -> null
            patternPairGroup.pairs.isEmpty() -> null
            patternPairGroup.pairs.lastIndex <= position -> {
                position = 0
                PatternPairGroupState(
                    pair = patternPairGroup.pairs[position],
                    position = position,
                    groupSize = patternPairGroup.pairs.size
                )
            }
            else ->
                PatternPairGroupState(
                    pair = patternPairGroup.pairs[++position],
                    position = position,
                    groupSize = patternPairGroup.pairs.size
                )
        }
    }
}