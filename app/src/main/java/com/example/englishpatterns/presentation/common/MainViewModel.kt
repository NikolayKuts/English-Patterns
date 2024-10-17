package com.example.englishpatterns.presentation.common

import androidx.datastore.core.DataStore
import androidx.lifecycle.viewModelScope
import com.example.englishpatterns.data.RowPatternGroupHolders
import com.example.englishpatterns.domain.RawPatternGroup
import com.example.englishpatterns.domain.RawPatternGroupHolder
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
    private val dataStore: DataStore<RowPatternGroupHolders>,
) : BaseViewModel<MainState, MainAction, MainEvent>() {

    private val rawPatternGroupHoldersState = dataStore.data
    private val chosenRawPatternGroupHolders: Flow<List<RawPatternGroupHolder>> =
        getChosenRawPatternGroupHoldersState()
    override val state = MutableStateFlow(value = MainState())

    override val eventState = MutableSharedFlow<MainEvent>()

    init {
        observeRawPatternGroupHoldersSource()
    }

    override fun sendAction(action: MainAction) {
        logD("sendAction() called. Action: $action")

        when (action) {
            is MainAction.ChangePatterHolderChoosingState -> {
                changePatterHolderChoosingState(
                    position = action.position,
                    rawPatternGroupHolder = action.rawPatternGroupHolder
                )
            }

            MainAction.NavigateToPatternPracticing -> {
                viewModelScope.launch {
                    val event = MainEvent.PatternPracticingRequired(
                        rawPatternGroups = getChosenRawPatternGroups()
                    )

                    eventState.emit(event)
                }
            }
        }
    }

    private fun observeRawPatternGroupHoldersSource() {
        viewModelScope.launch(Dispatchers.IO) {
            rawPatternGroupHoldersState.collect { holders ->
                state.update { it.copy(rowPatternGroupHolders = holders) }
            }
        }
    }

    private fun getChosenRawPatternGroupHoldersState(): Flow<List<RawPatternGroupHolder>> {
        return rawPatternGroupHoldersState.map {
            it.content.filter { holder -> holder.isChosen }
        }
    }

    private suspend fun getChosenRawPatternGroups(): List<RawPatternGroup> {
        return chosenRawPatternGroupHolders.firstOrNull()?.let { holders ->
            holders.map { holder -> holder.rawPatternGroup }
        } ?: run {
            emptyList()
        }
    }

    private fun changePatterHolderChoosingState(
        position: Int,
        rawPatternGroupHolder: RawPatternGroupHolder
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.updateData { patternHolders ->
                val updatedHolders = patternHolders.content.toMutableList()
                    .apply {
                        this[position] =
                            rawPatternGroupHolder.copy(isChosen = !rawPatternGroupHolder.isChosen)
                    }

                RowPatternGroupHolders(content = updatedHolders)
            }
        }
    }
}