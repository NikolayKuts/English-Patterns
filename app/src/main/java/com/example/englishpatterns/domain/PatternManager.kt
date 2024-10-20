package com.example.englishpatterns.domain

import com.example.englishpatterns.presentation.patternPractisingScreen.PatternGroupsHolder

class PatternManager(
    private val patternGroupsHolder: PatternGroupsHolder? = null,
) {

    private var position: Int = -1

    fun nextPattern(): PatternGroupUnitState? {
        return when {
            patternGroupsHolder == null -> null
            patternGroupsHolder.patterns.isEmpty() -> null
            patternGroupsHolder.patterns.lastIndex <= position -> {
                position = 0
                PatternGroupUnitState(
                    pair = patternGroupsHolder.patterns[position],
                    position = position,
                    groupSize = patternGroupsHolder.patterns.size
                )
            }
            else ->
                PatternGroupUnitState(
                    pair = patternGroupsHolder.patterns[++position],
                    position = position,
                    groupSize = patternGroupsHolder.patterns.size
                )
        }
    }
}