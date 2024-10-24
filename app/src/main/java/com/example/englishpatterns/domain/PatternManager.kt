package com.example.englishpatterns.domain

import com.example.englishpatterns.presentation.patternPractisingScreen.PatternGroupHolder

class PatternManager(
    private var patternGroupHolder: PatternGroupHolder? = null,
) {

    private var position: Int = -1

    fun nextPatternGroupUnitState(): PatternGroupUnitState? {
        val immutablePatternGroupHolder = patternGroupHolder ?: return null
        val patterns = immutablePatternGroupHolder.patterns

        if (patterns.isEmpty()) return null

        position = if (position >= patterns.lastIndex) 0 else position + 1

        return PatternGroupUnitState(
            pattern = patterns[position],
            position = position,
            groupSize = patterns.size
        )
    }

    fun updatedPatternGroupUnitState(
        updatedPatternGroupHolder: PatternGroupHolder?
    ): PatternGroupUnitState? {
        this.patternGroupHolder = updatedPatternGroupHolder

        val immutablePatternGroupHolder = this.patternGroupHolder

        return when {
            immutablePatternGroupHolder == null -> null
            immutablePatternGroupHolder.patterns.isEmpty() -> null
            else -> {
                PatternGroupUnitState(
                    pattern = immutablePatternGroupHolder.patterns[position],
                    position = position,
                    groupSize = immutablePatternGroupHolder.patterns.size
                )
            }
        }
    }
}