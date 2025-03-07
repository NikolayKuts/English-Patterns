package com.example.englishpatterns.presentation.patternPractisingScreen

import androidx.lifecycle.SavedStateHandle
import com.example.englishpatterns.data.IdentifiablePattern

class PatternPracticingStateStore(private val savedStateHandle: SavedStateHandle) {

    companion object {

        private const val CHOSEN_PATTERN_GROUP_IDS_KEY = "chosen_pattern_group_ids"
        private const val IS_SHUFFLED_KEY = "is_shuffled"
        private const val CURRENT_SINGLE_GROUP_PATTERNS_IDS_KEY = "current_groups"
        private const val CURRENT_UNIT_POSITION_KEY = "current_unit_position"
        private const val WEEK_PATTERN_IDS_KEY = "week_pattern_ids"
    }

    fun saveChosenPatternGroupsIds(ids: List<Int>) {
        savedStateHandle[CHOSEN_PATTERN_GROUP_IDS_KEY] = ids
    }

    fun getIdentifiedPracticingPatternGroups(
        groups: List<IdentifiablePracticingPatternGroup>
    ): List<IdentifiablePracticingPatternGroup> {
        val chosenGroupIds = savedStateHandle.get<ArrayList<Int>>(CHOSEN_PATTERN_GROUP_IDS_KEY)
            ?: return groups

        return groups.map { it.copy(isChosen = it.id in chosenGroupIds) }
            .let { updatedGroup ->
                val weekPatternIds = savedStateHandle.get<ArrayList<Int>>(WEEK_PATTERN_IDS_KEY)
                val groupsToUpdate = updatedGroup.toMutableList()

                if (weekPatternIds != null) {
                    val weekPatterns = updatedGroup.retrieveWeekPatterns(
                        weekPatternIds = weekPatternIds
                    )

                    val weekPatternGroup = IdentifiablePracticingPatternGroup(
                        id = groupsToUpdate.lastIndex.inc(),
                        identifiablePatterns = weekPatterns,
                        isWeaklyMemorized = true,
                        isChosen = groupsToUpdate.lastIndex.inc() in chosenGroupIds
                    )

                    groupsToUpdate.add(weekPatternGroup)
                }

                groupsToUpdate
            }
    }

    fun saveShufflingState(isShuffled: Boolean) {
        savedStateHandle[IS_SHUFFLED_KEY] = isShuffled
    }

    fun getSavedShufflingState(): Boolean? {
        return savedStateHandle.get<Boolean>(IS_SHUFFLED_KEY)
    }

    fun saveCurrentPatternUnitPosition(position: Int) {
        savedStateHandle[CURRENT_UNIT_POSITION_KEY] = position
    }

    fun getSavedPatternUnitPosition(): Int? = savedStateHandle.get<Int>(CURRENT_UNIT_POSITION_KEY)

    fun saveCurrentSingleGroupPatternsIds(group: IdentifiablePracticingPatternGroup?) {
        group?.identifiablePatterns?.map { pattern -> pattern.id }
            .also { patternIds ->
                savedStateHandle[CURRENT_SINGLE_GROUP_PATTERNS_IDS_KEY] = patternIds
            }
    }

    fun getCurrentSinglePatternGroup(
        singleGroup: IdentifiablePracticingPatternGroup?,
    ): IdentifiablePracticingPatternGroup? {
        singleGroup ?: return null

        val savedCurrentSingleGroupPatternsIds = savedStateHandle.get<ArrayList<Int>>(
            CURRENT_SINGLE_GROUP_PATTERNS_IDS_KEY
        ) ?: return null

        val savedIdsToIndexesMap: Map<Int, Int> = savedCurrentSingleGroupPatternsIds.withIndex()
            .associate { it.value to it.index }

        val sortedPatterns = singleGroup.identifiablePatterns.sortedBy { pattern ->
            savedIdsToIndexesMap[pattern.id] }

        return singleGroup.copy(identifiablePatterns = sortedPatterns)
    }

    fun saveWeekPatterns(identifiablePattern: List<IdentifiablePattern>) {
        savedStateHandle[WEEK_PATTERN_IDS_KEY] = identifiablePattern.map { it.id }
    }

    private fun List<IdentifiablePracticingPatternGroup>.retrieveWeekPatterns(
        weekPatternIds: ArrayList<Int>,
    ): List<IdentifiablePattern> = flatMap { group -> group.identifiablePatterns }
        .filter { identifiedPattern -> identifiedPattern.id in weekPatternIds }
}