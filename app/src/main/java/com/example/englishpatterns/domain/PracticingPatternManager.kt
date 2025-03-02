package com.example.englishpatterns.domain

import com.example.englishpatterns.presentation.patternPractisingScreen.PracticingPatternGroup

class PracticingPatternManager(
    private var practicingPatternGroup: PracticingPatternGroup? = null,
) {

    private var position: Int = -1

    fun nextUnit(): PracticingPatternUnit? {
        val immutablePracticingPatternGroup = practicingPatternGroup ?: return null
        val patterns = immutablePracticingPatternGroup.patterns

        if (patterns.isEmpty()) return null

        position = if (position >= patterns.lastIndex) 0 else position.inc()

        return PracticingPatternUnit(
            pattern = patterns[position],
            position = position,
            groupSize = patterns.size
        )
    }

    fun previousUnit(): PracticingPatternUnit? {
        val immutablePracticingPatternGroup = practicingPatternGroup ?: return null
        val patterns = immutablePracticingPatternGroup.patterns

        if (patterns.isEmpty()) return null

        position = if (position <= 0) patterns.lastIndex else position.dec()

        return PracticingPatternUnit(
            pattern = patterns[position],
            position = position,
            groupSize = patterns.size
        )
    }

    fun updatedUnit(
        updatedPracticingPatternGroup: PracticingPatternGroup?
    ): PracticingPatternUnit? {
        this.practicingPatternGroup = updatedPracticingPatternGroup

        val immutablePracticingPatternGroup = this.practicingPatternGroup

        return when {
            immutablePracticingPatternGroup == null -> null
            immutablePracticingPatternGroup.patterns.isEmpty() -> null
            else -> {
                PracticingPatternUnit(
                    pattern = immutablePracticingPatternGroup.patterns[position],
                    position = position,
                    groupSize = immutablePracticingPatternGroup.patterns.size
                )
            }
        }
    }
}