package com.example.englishpatterns.presentation.patternPractisingScreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.englishpatterns.domain.PatternManager
import com.example.englishpatterns.domain.PatternGroupUnitState
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

    override val state = MutableStateFlow(value = PatternPracticingState())

    override val eventState: SharedFlow<Unit> = MutableSharedFlow()

    private val chosenPatternGroupHoldersSate: MutableStateFlow<List<PatternGroupsHolder>> =
        MutableStateFlow(value = rawPatternGroups.toPatternGroupHolders())

    private val currentPatterState = MutableStateFlow<PatternGroupUnitState?>(value = null)

    private var patternManager = PatternManager(
        patternGroupsHolder = chosenPatternGroupHoldersSate.value.mapToSingleGroup()
    )

    init {
        chosenPatternGroupHoldersSate.onEach {
            state.update { it.copy(patternGroupHolders = chosenPatternGroupHoldersSate.value) }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

        currentPatterState.onEach { currentPattern ->
            state.update { it.copy(currentPattern = currentPattern) }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    override fun sendAction(action: PatternPracticingAction) {
        logD("sendAction() called. Event: $action")

        when (action) {
            is PatternPracticingAction.ChangePairGroupChoosingState -> {
                chosenPatternGroupHoldersSate.update {
                    it.toMutableList().apply {
                        val group = this[action.position]
                        this[action.position] = group.copy(isChosen = !group.isChosen)
                    }
                }

                patternManager = PatternManager(
                    patternGroupsHolder = chosenPatternGroupHoldersSate.value.filter { it.isChosen }
                        .mapToSingleGroup()
                )
                currentPatterState.value = patternManager.nextPattern()
            }

            PatternPracticingAction.NextPatterPair -> {
                currentPatterState.value = patternManager.nextPattern()
            }

            PatternPracticingAction.ShufflePatternPairs -> {
                patternManager = PatternManager(
                    patternGroupsHolder = chosenPatternGroupHoldersSate.value.filter { it.isChosen }
                        .mapToSingleShuffledGroup()
                )
                currentPatterState.value = patternManager.nextPattern()
            }

            PatternPracticingAction.SelectAllPairs -> {
                chosenPatternGroupHoldersSate.update { patternPairGroups ->
                    patternPairGroups.map { it.copy(isChosen = true) }
                }

                patternManager = PatternManager(
                    patternGroupsHolder = chosenPatternGroupHoldersSate.value.filter { it.isChosen }
                        .mapToSingleGroup()
                )
                currentPatterState.value = patternManager.nextPattern()
            }
        }
    }

    private fun List<RawPatternGroup>.toPatternGroupHolders(): List<PatternGroupsHolder> {
        val patternsList = this.flatMap { rawPatternGroup ->
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

        return patternsList.chunked(6).map { patterns ->
            PatternGroupsHolder(patterns = patterns, isChosen = false)
        }
    }

    private fun List<PatternGroupsHolder>.mapToSingleGroup(): PatternGroupsHolder =
        PatternGroupsHolder(
            patterns = this.map { it.patterns }.flatten()
        )

    private fun List<PatternGroupsHolder>.mapToSingleShuffledGroup(): PatternGroupsHolder =
        PatternGroupsHolder(
            patterns = this.map { it.patterns.shuffled() }.shuffled().flatten().shuffled()
        )


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