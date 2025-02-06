package com.example.englishpatterns.presentation.irregularVerbsPractice

import androidx.lifecycle.viewModelScope
import com.example.englishpatterns.domain.irregularVerbs.HidingMode
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbsGroup
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbsStorage
import com.example.englishpatterns.domain.irregularVerbs.VerbDetails
import com.example.englishpatterns.presentation.common.MviViewModel
import com.lib.lokdroid.core.logD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.util.LinkedList

class IrregularVerbsPracticeViewModel : MviViewModel<
        IrregularVerbsPracticeState,
        IrregularVerbsPracticeAction,
        IrregularVerbsPracticeEvent>() {

    private val verbsStorage = IrregularVerbsStorage
    private val verbsDetails = MutableStateFlow<LinkedList<VerbDetails>>(value = LinkedList())
    private val verbsGroupViewHolders: MutableStateFlow<List<IrregularVerbsGroupViewHolder>> =
        MutableStateFlow(value = createIrregularVerbsGroupViewHolderList())

    override val state = MutableStateFlow(
        value = IrregularVerbsPracticeState(verbsGroupViewHolders = verbsGroupViewHolders.value)
    )

    override val eventState = MutableSharedFlow<IrregularVerbsPracticeEvent>()

    init {
        verbsGroupViewHolders.onEach { holders ->
            state.update { it.copy(verbsGroupViewHolders = holders) }
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

            IrregularVerbsPracticeAction.NextVerb -> {
                handleNextVerbAction()
            }

            IrregularVerbsPracticeAction.PreviousVerb -> {
                handlePreviousVerbAction()
            }

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
                handleReturnVerbVisibilityModeAction()
            }

            is IrregularVerbsPracticeAction.ShowHiddenVerbs -> {
                handleShowHiddenVerbsAction()
            }

            is IrregularVerbsPracticeAction.SetSubGroup -> {
                handleSetSubGroupAction(action)
            }
        }
    }

    private fun createIrregularVerbsGroupViewHolderList(): List<IrregularVerbsGroupViewHolder> {
        return listOf(
            IrregularVerbsGroupViewHolder.Common(type = verbsStorage.unchanging.type),
            IrregularVerbsGroupViewHolder.Common(type = verbsStorage.partiallyChanging.type),
            IrregularVerbsGroupViewHolder.FullyChanging(
                type = verbsStorage.fullyChanging.type,
                subGroups = listOf(
                    FullyChangingSubGroupViewHolder(
                        subGroupName = verbsStorage.fullyChanging.first.name
                    ),
                    FullyChangingSubGroupViewHolder(
                        subGroupName = verbsStorage.fullyChanging.x_anX_uXX.name
                    ),
                    FullyChangingSubGroupViewHolder(
                        subGroupName = verbsStorage.fullyChanging.x_oXe_Xen.name
                    ),
                    FullyChangingSubGroupViewHolder(
                        subGroupName = verbsStorage.fullyChanging.iXe_oXe_Xen.name
                    ),
                    FullyChangingSubGroupViewHolder(
                        subGroupName = verbsStorage.fullyChanging.some.name
                    ),
                    FullyChangingSubGroupViewHolder(
                        subGroupName = verbsStorage.fullyChanging.x_ew_wn.name
                    ),
                    FullyChangingSubGroupViewHolder(
                        subGroupName = verbsStorage.fullyChanging.x_aXe_Xen.name
                    ),
                )
            ),
            IrregularVerbsGroupViewHolder.Common(type = verbsStorage.partiallyConsistent.type),
        )
    }

    private fun handleChangeVerbGroupAction(action: IrregularVerbsPracticeAction.ChangeVerbGroup) {
        changeVerbGroupHolderSelectionState(triggeredType = action.type)
        updateVerbsDetailsBySelectionState()
    }

    private fun handleNextVerbAction() {
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
        val updatedShufflingMode = state.value.isShufflingModeOn.not()

        state.update { it.copy(isShufflingModeOn = updatedShufflingMode) }

        verbsDetails.update { details ->
            val updatedDetails = if (updatedShufflingMode) {
                details.shuffled()
            } else {
                getInitialVerbDetailsBySelectedGroups()
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

    private fun handleReturnVerbVisibilityModeAction() {
        val currentMode = state.value.hidingMode as? HidingMode.ForcedShow ?: return

        state.update { it.copy(hidingMode = currentMode.previousMode) }
    }

    private fun handleShowHiddenVerbsAction() {
        state.update {
            val updatedMode = HidingMode.ForcedShow(previousMode = it.hidingMode)

            it.copy(hidingMode = updatedMode)
        }
    }

    private fun handleSetSubGroupAction(action: IrregularVerbsPracticeAction.SetSubGroup) {
        manageSubGroupSelection(action = action)
        updateVerbsDetailsBySelectionState()
    }

    private fun changeVerbGroupHolderSelectionState(triggeredType: IrregularVerbGroupType) {
        verbsGroupViewHolders.update { currentList ->
            currentList.map { groupViewHolder ->

                if (groupViewHolder.type == triggeredType) {
                    when (groupViewHolder) {
                        is IrregularVerbsGroupViewHolder.Common -> {
                            groupViewHolder.copy(isSelected = !groupViewHolder.isSelected)
                        }

                        is IrregularVerbsGroupViewHolder.FullyChanging -> {
                            groupViewHolder.copy(isSelected = !groupViewHolder.isSelected)
                        }
                    }
                } else {
                    groupViewHolder
                }
            }
        }
    }

    private fun updateVerbsDetailsBySelectionState() {
        verbsDetails.update {
            val isShufflingModeOn = state.value.isShufflingModeOn
            val newDetails = getInitialVerbDetailsBySelectedGroups()

            val updatedDetails = if (isShufflingModeOn) {
                newDetails.shuffled()
            } else {
                newDetails
            }

            LinkedList(updatedDetails)
        }
    }

    private fun List<IrregularVerbsGroup>.extractVerbsDetails(): List<VerbDetails> {
        return flatMap { selectedVerbGroup ->
            when (selectedVerbGroup) {
                is IrregularVerbsGroup.PartiallyConsistent -> {
                    selectedVerbGroup.details
                }

                is IrregularVerbsGroup.PartiallyChanging -> {
                    selectedVerbGroup.details
                }

                is IrregularVerbsGroup.Unchanging -> {
                    selectedVerbGroup.details
                }

                is IrregularVerbsGroup.FullyChanging -> {
                    val subGroups = selectedVerbGroup.subGroups.filter { subGroup ->
                        verbsGroupViewHolders.value.asSequence().filter {
                            it.type == IrregularVerbGroupType.FullyChanging
                        }.mapNotNull {
                            it as? IrregularVerbsGroupViewHolder.FullyChanging
                        }.flatMap { subGroupViewHolder ->
                            subGroupViewHolder.subGroups
                        }.filter { subGroupViewHolder ->
                            subGroupViewHolder.isSelected
                        }.map { it.subGroupName }
                            .contains(subGroup.name)
                    }

                    subGroups.flatMap { it.details }
                }
            }
        }
    }

    private fun getInitialVerbDetailsBySelectedGroups(): List<VerbDetails> {
        val selectedVerbsGroups = verbsStorage.irregularVerbsGroups.filter { verbDetailsGroup ->
            verbDetailsGroup.isAmongSelected()
        }

        return selectedVerbsGroups.extractVerbsDetails()
    }

    private fun IrregularVerbsGroup.isAmongSelected(): Boolean {
        return verbsGroupViewHolders.value.filter { holder -> holder.isSelected }
            .map { holder -> holder.type }
            .contains(this.type)
    }

    private fun manageSubGroupSelection(action: IrregularVerbsPracticeAction.SetSubGroup) {
        verbsGroupViewHolders.update { groupViewHolders ->
            groupViewHolders.map { groupViewHolder ->
                if (groupViewHolder is IrregularVerbsGroupViewHolder.FullyChanging) {
                    val updatedSubHolders = groupViewHolder.subGroups.map { subGroupViewHolder ->
                        if (subGroupViewHolder == action.group) {
                            subGroupViewHolder.copy(isSelected = subGroupViewHolder.isSelected.not())
                        } else {
                            subGroupViewHolder
                        }
                    }

                    groupViewHolder.copy(subGroups = updatedSubHolders)
                } else {
                    groupViewHolder
                }
            }
        }
    }
}