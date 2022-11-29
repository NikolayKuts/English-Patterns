package com.example.englishpatterns.presentation

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishpatterns.data.PatternHolders
import com.example.englishpatterns.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val context: Application,
    private val dataStore: DataStore<PatternHolders>,
) : ViewModel() {

    private val patternHolders = MutableStateFlow<PatternHolders?>(value = null)
    private val chosenPatterns = getChosenPatterns()
    private val chosenPatternPairGroups =
        MutableStateFlow<List<PatternPairGroup>>(value = emptyList())

    private val currentPatter = MutableStateFlow<PatternPairGroupState?>(value = null)
    private var patternManager = PatternManager(
        patternPairGroup = chosenPatternPairGroups.value.mapToSingleGroup()
    )

    private val _state = MutableStateFlow<State>(
        value = State.InitialState(patternHolderSource = patternHolders)
    )
    val state = _state.asStateFlow()

    init {
        observeChosenPatterns()
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.collect { holders ->
                patternHolders.value = holders
            }
        }
    }

    fun sendEvent(event: Event) {
        when (event) {
            Event.DisplayMainScreen -> {
                _state.value = State.InitialState(patternHolderSource = patternHolders)
                currentPatter.value = null
            }
            Event.NavigateToPatternPracticing -> {
                _state.value = State.PatternPracticingState(
                    patternPairGroups = chosenPatternPairGroups,
                    currentPattern = currentPatter
                )
            }
            is Event.ChangePatterHolderChoosingState -> {
                changePatterHolderChoosingState(
                    position = event.position,
                    patternHolder = event.patternHolder
                )
            }
            is Event.ChangePairGroupChoosingState -> {
                chosenPatternPairGroups.update {
                    it.toMutableList().apply {
                        val group = this[event.position]
                        this[event.position] = group.copy(isChosen = !group.isChosen)
                    }
                }

                patternManager = PatternManager(
                    patternPairGroup = chosenPatternPairGroups.value.filter { it.isChosen }
                        .mapToSingleGroup()
                )
                currentPatter.value = patternManager.nextPattern()
            }
            Event.NextPatterPair -> {
                currentPatter.value = patternManager.nextPattern()
            }
            Event.ShufflePatternPairs -> {
                patternManager = PatternManager(
                    patternPairGroup = chosenPatternPairGroups.value.filter { it.isChosen }
                        .mapToSingleShuffledGroup()
                )
                currentPatter.value = patternManager.nextPattern()
            }
            Event.SelectAllPairs -> {
                chosenPatternPairGroups.update { patternPairGroups ->
                    patternPairGroups.map { it.copy(isChosen = true) }
                }

                patternManager = PatternManager(
                    patternPairGroup = chosenPatternPairGroups.value.filter { it.isChosen }
                        .mapToSingleGroup()
                )
                currentPatter.value = patternManager.nextPattern()
            }
        }
    }

    private fun getChosenPatterns(): Flow<List<PatternPair>> {
        return patternHolders.map { patternHolders ->
            patternHolders?.holders?.filter { holder -> holder.isChosen }
                ?.map { holder -> holder.pattern }
                ?.map { chosenPattern ->
                    chosenPattern.resIds.map { resId -> context.getString(resId) }
                        .joinToString(separator = "@")
                        .split("@")
                        .map { rowPair ->
                            PatternPair(
                                native = rowPair.substringBefore("=="),
                                translation = rowPair.substringAfter("==")
                            )
                        }
                }?.flatten()
                ?: emptyList()
        }
    }

    private fun observeChosenPatterns() {
        viewModelScope.launch {
            chosenPatterns.collect { patternPairs ->
                chosenPatternPairGroups.value = patternPairs.chunked(size = 6)
                    .map { pairs -> PatternPairGroup(isChosen = false, pairs = pairs) }
            }
        }
    }

    private fun List<PatternPairGroup>.mapToSingleGroup(): PatternPairGroup = PatternPairGroup(
        pairs = this.map { it.pairs }.flatten()
    )

    private fun List<PatternPairGroup>.mapToSingleShuffledGroup(): PatternPairGroup =
        PatternPairGroup(
            pairs = this.map { it.pairs.shuffled() }.shuffled().flatten().shuffled()
        )

    private fun changePatterHolderChoosingState(position: Int, patternHolder: PatternHolder) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.updateData { patternHolders ->
                val updatedHolders = patternHolders.holders.toMutableList()
                    .apply {
                        this[position] = patternHolder.copy(isChosen = !patternHolder.isChosen)
                    }
                PatternHolders(holders = updatedHolders)
            }
        }
    }
}