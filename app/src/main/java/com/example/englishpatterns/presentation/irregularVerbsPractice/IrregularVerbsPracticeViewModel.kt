package com.example.englishpatterns.presentation.irregularVerbsPractice

import androidx.lifecycle.viewModelScope
import com.example.englishpatterns.domain.irregularVerbs.HidingMode
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbsStorage
import com.example.englishpatterns.domain.irregularVerbs.VerbDetails
import com.example.englishpatterns.presentation.common.BaseViewModel
import com.lib.lokdroid.core.logD
import com.lib.lokdroid.core.logE
import com.lib.lokdroid.core.logI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.util.LinkedList

data class VerbsGroupHolder(
    val type: IrregularVerbGroupType,
    val isSelected: Boolean = false,
)

class IrregularVerbsPracticeViewModel(
) : BaseViewModel<IrregularVerbsPracticeState, IrregularVerbsPracticeAction, IrregularVerbsPracticeEvent>() {

    private val verbsStorage = IrregularVerbsStorage
    private val verbsDetails = MutableStateFlow<LinkedList<VerbDetails>>(value = LinkedList())
    private val verbsGroupHolders = MutableStateFlow(
        value = IrregularVerbGroupType.entries.map { VerbsGroupHolder(type = it) }
    )

    override val state = MutableStateFlow(
        value = IrregularVerbsPracticeState(verbsGroupHolders = verbsGroupHolders.value)
    )

    override val eventState = MutableSharedFlow<IrregularVerbsPracticeEvent>()

    init {
        verbsGroupHolders.onEach { holders ->
            state.update { it.copy(verbsGroupHolders = holders) }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

        verbsDetails.onEach { details ->
            val currentDetails = details.firstOrNull()

            state.update { it.copy(currentVerbDetails = currentDetails) }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    override fun sendAction(action: IrregularVerbsPracticeAction) {
        logD("sendAction() called. Action: $action")

        when (action) {
            is IrregularVerbsPracticeAction.ChangeVerbGroup -> {
                handleChangeVerbGroupAction(action)
            }

            IrregularVerbsPracticeAction.NextVerb -> handleNextVerbAction()
            IrregularVerbsPracticeAction.PreviousVerb -> handlePreviousVerbAction()
            IrregularVerbsPracticeAction.ChangeVerbHighlightMode -> {
                handleChangeVerbHighlightModeAction()
            }

            is IrregularVerbsPracticeAction.ChangeShufflingMode -> {
                handleChangeShufflingModeAction()
            }

            IrregularVerbsPracticeAction.TextToSpeech -> {
                handleTextToSpeechAction()
            }

            is IrregularVerbsPracticeAction.SetHidingMode -> {
                handleSetHidingModeAction(action)
            }

            is IrregularVerbsPracticeAction.ReturnVerbVisibilityMode -> {
                val currentMode = state.value.hidingMode as? HidingMode.ForcedShow ?: return

                state.update { it.copy(hidingMode = currentMode.previousMode) }
            }

            is IrregularVerbsPracticeAction.ShowHiddenVerbs -> {
                state.update {
                    val updatedMode =  HidingMode.ForcedShow(previousMode = it.hidingMode)

                    it.copy(hidingMode = updatedMode)
                }
            }
        }
    }

    private fun handleChangeVerbGroupAction(action: IrregularVerbsPracticeAction.ChangeVerbGroup) {
        changeVerbGroupHolderSelectionState(triggeredType = action.type)
        val isShufflingModeOn = state.value.isShufflingModeOn

        verbsDetails.update {
            val newDetails = verbsStorage.allVerbsDetails.filter { verbDetails ->
                verbsGroupHolders.value.filter { it.isSelected }.map { it.type }
                    .contains(verbDetails.type)
            }

            val updatedDetails = if (isShufflingModeOn) {
                newDetails.shuffled()
            } else {
                newDetails
            }

            LinkedList(updatedDetails)
        }

    }

    private fun changeVerbGroupHolderSelectionState(triggeredType: IrregularVerbGroupType) {
        verbsGroupHolders.update { currentList ->
            currentList.map {
                if (it.type == triggeredType) {
                    it.copy(isSelected = !it.isSelected)
                } else {
                    it
                }
            }
        }
    }

    private fun handleNextVerbAction() {
        logE("handleNextVerbAction() called")

        verbsDetails.update { list ->
            LinkedList(list).apply {
                val first = removeFirstOrNull() ?: return@apply
                add(first)
            }
        }
    }

    private fun handlePreviousVerbAction() {
        verbsDetails.update { list ->
            LinkedList(list).apply { if (size > 1) addFirst(removeLast()) }
        }
    }

    private fun handleChangeVerbHighlightModeAction() {
        state.update { it.copy(verbHighlightModeOn = !it.verbHighlightModeOn) }
    }

    private fun handleChangeShufflingModeAction() {
        val isShufflingModeOn = state.value.isShufflingModeOn

        state.update {
            it.copy(isShufflingModeOn = !it.isShufflingModeOn)
        }

        verbsDetails.update { details ->
            val updatedDetails = if (isShufflingModeOn.not()) {
                details.shuffled()
            } else {
                val updated = verbsStorage.allVerbsDetails.filter { verbDetails ->
                    verbsGroupHolders.value.filter { it.isSelected }
                        .map { it.type }
                        .contains(verbDetails.type)
                }
                updated
            }

            LinkedList(updatedDetails)
        }
    }

    private fun handleTextToSpeechAction() {
        val details = state.value.currentVerbDetails ?: return
        val textToSpeech = "${details.v1.word} ${details.v2.word} ${details.v3.word}"

        eventState.launchEmit { IrregularVerbsPracticeEvent.TextToSpeech(textToSpeech) }
    }

    private fun handleSetHidingModeAction(action: IrregularVerbsPracticeAction.SetHidingMode) {
        state.update { it.copy(hidingMode = action.mode) }
    }
}