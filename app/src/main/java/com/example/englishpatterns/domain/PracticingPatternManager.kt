package com.example.englishpatterns.domain

import com.example.englishpatterns.presentation.patternPractisingScreen.IdentifiablePracticingPatternGroup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PracticingPatternManager(
    private var practicingPatternGroup: IdentifiablePracticingPatternGroup? = null,
) {

    private var mutPositionState = MutableStateFlow(value = -1)
    val unitPositionState = mutPositionState.asStateFlow()

    fun nextUnit(): PracticingPatternUnit? {
        val immutablePracticingPatternGroup = practicingPatternGroup ?: return null
        val patterns = immutablePracticingPatternGroup.identifiablePatterns

        if (patterns.isEmpty()) return null

        mutPositionState.value = if (mutPositionState.value >= patterns.lastIndex) {
            0
        } else {
            mutPositionState.value.inc()
        }

        return PracticingPatternUnit(
            pattern = patterns[mutPositionState.value],
            position = mutPositionState.value,
            groupSize = patterns.size
        )
    }

    fun toUnit(position: Int): PracticingPatternUnit? {
        this.mutPositionState.value = if (position < 0) position else position.dec()
        return nextUnit()
    }

    fun previousUnit(): PracticingPatternUnit? {
        val immutablePracticingPatternGroup = practicingPatternGroup ?: return null
        val patterns = immutablePracticingPatternGroup.identifiablePatterns

        if (patterns.isEmpty()) return null

        mutPositionState.value = if (mutPositionState.value <= 0) {
            patterns.lastIndex
        } else {
            mutPositionState.value.dec()
        }

        return PracticingPatternUnit(
            pattern = patterns[mutPositionState.value],
            position = mutPositionState.value,
            groupSize = patterns.size
        )
    }

    fun updatedUnit(
        updatedPracticingPatternGroup: IdentifiablePracticingPatternGroup?
    ): PracticingPatternUnit? {
        this.practicingPatternGroup = updatedPracticingPatternGroup

        val immutablePracticingPatternGroup = this.practicingPatternGroup

        return when {
            immutablePracticingPatternGroup == null -> null
            immutablePracticingPatternGroup.identifiablePatterns.isEmpty() -> null
            else -> {
                PracticingPatternUnit(
                    pattern = immutablePracticingPatternGroup.identifiablePatterns[mutPositionState.value],
                    position = mutPositionState.value,
                    groupSize = immutablePracticingPatternGroup.identifiablePatterns.size
                )
            }
        }
    }

    fun reset(
        updatedPracticingPatternGroup: IdentifiablePracticingPatternGroup?
    ) {
        this.practicingPatternGroup = updatedPracticingPatternGroup
        mutPositionState.value = -1
    }
}