package com.example.englishpatterns.presentation.common

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.englishpatterns.data.PatternRepository
import com.example.englishpatterns.domain.PatternGroupResContainer
import com.example.englishpatterns.domain.PatternGroupResource
import com.example.englishpatterns.domain.storageKey
import com.lib.lokdroid.core.logD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val patternRepository: PatternRepository,
    savedStateHandle: SavedStateHandle,
) : MviViewModel<MainState, MainAction, MainEvent>(savedStateHandle = savedStateHandle) {

    private val patternGroupResContainersStata = patternRepository.observePatternGroupResContainers()

    private val chosenPatternGroupResContainers: Flow<List<PatternGroupResContainer>> =
        getChosenPatternGroupResContainersState()

    override val uiState = MutableStateFlow(value = MainState())

    override val eventState = MutableSharedFlow<MainEvent>()

    init {
        observePatternGroupResContainersSource()
        observeChosenPatterGroupResContainers()
    }

    override fun sendAction(action: MainAction) {
        logD("sendAction() called. Action: $action")

        when (action) {
            is MainAction.ChangePatternGroupResContainerChoosingState -> {
                changePatternGroupResContainerChoosingState(
                    position = action.position,
                    patternGroupResContainer = action.resContainer
                )
            }

            MainAction.NavigateToPatternPracticing -> {
                eventState.launchEmit {
                    MainEvent.PatternPracticingRequired(
                        patternGroupResources = getChosenSelectablePatternGroupContainers()
                    )
                }
            }

            is MainAction.SetMarkColor -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val resContainer = uiState.value.patternGroupResContainers.content
                        .getOrNull(action.patternIndex)
                        ?: return@launch

                    patternRepository.setPatternGroupMarkColor(
                        groupKey = resContainer.patternGroupResource.storageKey,
                        markColor = action.markColor
                    )
                }
            }

            MainAction.NavigateToIrregularVerbsPracticeScreen -> {
                eventState.launchEmit { MainEvent.IrregularVerbsPracticeRequired }
            }
        }
    }

    private fun observePatternGroupResContainersSource() {
        patternGroupResContainersStata.launchCollect { resContainers ->
            uiState.update { it.copy(patternGroupResContainers = resContainers) }
        }
    }

    private fun observeChosenPatterGroupResContainers() {
        chosenPatternGroupResContainers.launchCollect {
            uiState.update { state -> state.copy(isStartButtonEnabled = it.isNotEmpty()) }
        }
    }

    private fun getChosenPatternGroupResContainersState(): Flow<List<PatternGroupResContainer>> {
        return patternGroupResContainersStata.map {
            it.content.filter { container -> container.isChosen }
        }
    }

    private suspend fun getChosenSelectablePatternGroupContainers(): List<PatternGroupResource> {
        return chosenPatternGroupResContainers.firstOrNull()?.let { containers ->
            containers.map { resContainer -> resContainer.patternGroupResource }
        } ?: run {
            emptyList()
        }
    }

    private fun changePatternGroupResContainerChoosingState(
        position: Int,
        patternGroupResContainer: PatternGroupResContainer
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val allResContainers = patternGroupResContainersStata.firstOrNull() ?: return@launch
            val resContainerToChange = allResContainers.content.getOrNull(position) ?: return@launch
            patternRepository.setPatternGroupChosen(
                groupKey = resContainerToChange.patternGroupResource.storageKey,
                isChosen = patternGroupResContainer.isChosen.not()
            )
        }
    }
}
