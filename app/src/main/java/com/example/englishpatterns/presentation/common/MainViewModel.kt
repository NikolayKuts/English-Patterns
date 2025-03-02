package com.example.englishpatterns.presentation.common

import androidx.datastore.core.DataStore
import androidx.lifecycle.viewModelScope
import com.example.englishpatterns.data.PatternGroupResContainers
import com.example.englishpatterns.domain.PatternGroupResContainer
import com.example.englishpatterns.domain.PatternGroupResource
import com.example.englishpatterns.presentation.patternPractisingScreen.PracticingPatternGroup
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
    private val patternStore: DataStore<PatternGroupResContainers>,
    private val weekPatternStorage: DataStore<PracticingPatternGroup>,
) : MviViewModel<MainState, MainAction, MainEvent>() {

    private val patternGroupResContainersStata = patternStore.data

    private val chosenPatternGroupResContainers: Flow<List<PatternGroupResContainer>> =
        getChosenPatternGroupResContainersState()

    override val state = MutableStateFlow(value = MainState())

    override val eventState = MutableSharedFlow<MainEvent>()

    init {
        observePatternGroupResContainersSource()
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
                    patternStore.updateData {
                        val list = it.content
                        val resContainer = list.getOrNull(action.patternIndex) ?: return@updateData it
                        val updatedPatternGroupResContainer = resContainer.patternGroupResource.toNew(
                            markColor = action.markColor
                        )
                        val updatedContent = list.toMutableList().apply {
                            this[action.patternIndex] =
                                resContainer.copy(
                                    patternGroupResource = updatedPatternGroupResContainer,
                                    isChosen = false
                                )
                        }

                        it.copy(content = updatedContent)
                    }
                }
            }

            MainAction.NavigateToIrregularVerbsPracticeScreen -> {
                eventState.launchEmit { MainEvent.IrregularVerbsPracticeRequired }
            }
        }
    }

    private fun observePatternGroupResContainersSource() {
        viewModelScope.launch(Dispatchers.IO) {
            patternGroupResContainersStata.collect { resContainers ->
                state.update { it.copy(patternGroupResContainers = resContainers) }
            }
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
            val resContainerToChange = allResContainers.content[position]

            when {
                patternStore.data.firstOrNull()?.content?.contains(resContainerToChange) == true -> {
                    patternStore.updateData { resContainers ->
                        val updatedResContainers = resContainers.content.toMutableList()
                            .apply {
                                this[position] = patternGroupResContainer.copy(
                                    isChosen = patternGroupResContainer.isChosen.not()
                                )
                            }

                        PatternGroupResContainers(content = updatedResContainers)
                    }
                }

                resContainerToChange.patternGroupResource is PatternGroupResource.WeekPatternGroupResource -> {
                    weekPatternStorage.updateData { it.copy(isChosen = !it.isChosen) }
                }
            }
        }
    }
}