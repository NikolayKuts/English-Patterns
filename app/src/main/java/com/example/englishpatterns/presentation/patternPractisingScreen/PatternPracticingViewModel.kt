package com.example.englishpatterns.presentation.patternPractisingScreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.englishpatterns.domain.PatternGroupUnitState
import com.example.englishpatterns.domain.PatternManager
import com.example.englishpatterns.domain.RawPatternGroup
import com.example.englishpatterns.presentation.common.BaseViewModel
import com.lib.lokdroid.core.logD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class PatternPracticingViewModel(
    private val context: Application,
    rawPatternGroups: List<RawPatternGroup>,
) : BaseViewModel<PatternPracticingState, PatternPracticingAction, Unit>() {

    companion object {

        private const val PATTERN_GROUP_CHUNK_SIZE = 6
    }

    override val state = MutableStateFlow(value = PatternPracticingState())

    override val eventState: SharedFlow<Unit> = MutableSharedFlow()

    private val patternGroupHoldersSate: MutableStateFlow<List<PatternGroupHolder>> =
        MutableStateFlow(value = rawPatternGroups.toChunkedPatternGroupHolders())

    private val currentPatterGroupUnitState = MutableStateFlow<PatternGroupUnitState?>(value = null)

    private var patternManager = PatternManager(
        patternGroupHolder = patternGroupHoldersSate.value.mapToSinglePatternHolderGroup()
    )

    init {
        patternGroupHoldersSate.onEach { holders ->
            state.update { it.copy(patternGroupHolders = holders) }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

        currentPatterGroupUnitState.onEach { currentPattern ->
            state.update { it.copy(currentPattern = currentPattern) }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    override fun sendAction(action: PatternPracticingAction) {
        logD("sendAction() called. Event: $action")

        when (action) {
            is PatternPracticingAction.ChangePatternGroupHolderChoosingState -> {
                patternGroupHoldersSate.update {
                    it.toMutableList().apply {
                        val group = this[action.position]
                        this[action.position] = group.copy(isChosen = !group.isChosen)
                    }
                }

                resetCurrentPatternGroupUnitState()
            }

            PatternPracticingAction.NextPatterPair -> {
                currentPatterGroupUnitState.value = patternManager.nextPatternGroupUnitState()
            }

            PatternPracticingAction.ShufflePatternPairs -> {
                patternManager = PatternManager(
                    patternGroupHolder = patternGroupHoldersSate.value.filter { it.isChosen }
                        .mapToSingleShuffledGroup()
                )
                currentPatterGroupUnitState.value = patternManager.nextPatternGroupUnitState()
            }

            PatternPracticingAction.SelectAllPatternGroups -> {
                patternGroupHoldersSate.update { patternPairGroups ->
                    patternPairGroups.map { it.copy(isChosen = true) }
                }

                resetCurrentPatternGroupUnitState()
            }

            PatternPracticingAction.SelectNextPatternGroup -> {
                manageSelectingNextPatternGroup()
            }

            is PatternPracticingAction.AddPatternAsWeaklyMemorized -> {
                manageAddingPatternAsWeaklyMemorized()
            }
        }
    }

    private fun List<RawPatternGroup>.toChunkedPatternGroupHolders(): List<PatternGroupHolder> {
        val patternsList = this.flatMapIndexed { index, rawPatternGroup ->
            rawPatternGroup.contentResIds
                .joinToString(separator = "@") { resId -> context.getString(resId) }
                .split("@")
                .map { rowPair ->
                    Pattern(
                        native = rowPair.substringBefore("=="),
                        translation = rowPair.substringAfter("==")
                    )
                }
        }

        return patternsList.chunked(size = PATTERN_GROUP_CHUNK_SIZE).map { patterns ->
            PatternGroupHolder(patterns = patterns, isChosen = false)
        }
    }

    private fun List<PatternGroupHolder>.mapToSinglePatternHolderGroup(): PatternGroupHolder =
        PatternGroupHolder(
            patterns = this.map { it.patterns }.flatten()
        )

    private fun List<PatternGroupHolder>.mapToSingleShuffledGroup(): PatternGroupHolder =
        PatternGroupHolder(
            patterns = this.map { it.patterns.shuffled() }.shuffled().flatten().shuffled()
        )

    private fun manageSelectingNextPatternGroup() {
        val chosenPatternsGroupHolders = patternGroupHoldersSate.value
        val currentIndex = chosenPatternsGroupHolders.indexOfLast { it.isChosen }

        if (chosenPatternsGroupHolders.isEmpty()) return

        patternGroupHoldersSate.update {
            it.mapIndexed { index, patternGroupHolder ->
                patternGroupHolder.copy(isChosen = index == (currentIndex + 1) % it.size)
            }
        }

        resetCurrentPatternGroupUnitState()
    }

    private fun manageAddingPatternAsWeaklyMemorized() {
        patternGroupHoldersSate.update {
            val currentPattern =
                currentPatterGroupUnitState.value?.pattern ?: return@update it
            val patternGroupHoldersToUpdate = it.toMutableList()
            val weaklyMemorizedPatternGroupHolderIndex =
                patternGroupHoldersToUpdate.indexOfFirst { holder -> holder.isWeaklyMemorized }

            if (weaklyMemorizedPatternGroupHolderIndex != -1) {
                val weaklyMemorizedPatternGroupHolder =
                    patternGroupHoldersToUpdate[weaklyMemorizedPatternGroupHolderIndex]

                if (currentPattern in weaklyMemorizedPatternGroupHolder.patterns) return@update it

                val updatedPatterns =
                    weaklyMemorizedPatternGroupHolder.patterns + currentPattern

                patternGroupHoldersToUpdate[weaklyMemorizedPatternGroupHolderIndex] =
                    weaklyMemorizedPatternGroupHolder.copy(patterns = updatedPatterns)
            } else {
                patternGroupHoldersToUpdate.add(
                    PatternGroupHolder(
                        patterns = listOf(currentPattern),
                        isWeaklyMemorized = true
                    )
                )
            }

            patternGroupHoldersToUpdate
        }

        updateCurrentPatternGroupUnitState()
    }

    private fun resetCurrentPatternGroupUnitState() {
        patternManager = PatternManager(
            patternGroupHolder = patternGroupHoldersSate.value.filter { it.isChosen }
                .mapToSinglePatternHolderGroup()
        )
        currentPatterGroupUnitState.value = patternManager.nextPatternGroupUnitState()
    }

    private fun updateCurrentPatternGroupUnitState() {
        val patterGroupHolder = patternGroupHoldersSate.value.filter { it.isChosen }
            .mapToSinglePatternHolderGroup()

        currentPatterGroupUnitState.value = patternManager.updatedPatternGroupUnitState(
            patternGroupHolder = patterGroupHolder
        )
    }


    class Factory(
        private val context: Application,
        private val rawPatternGroups: List<RawPatternGroup>,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = PatternPracticingViewModel(
            context = context,
            rawPatternGroups = rawPatternGroups
        ) as T
    }
}