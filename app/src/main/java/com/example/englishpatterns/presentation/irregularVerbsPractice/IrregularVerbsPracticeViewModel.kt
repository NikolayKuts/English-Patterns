package com.example.englishpatterns.presentation.irregularVerbsPractice

import androidx.lifecycle.viewModelScope
import com.example.englishpatterns.domain.irregularVerbs.HidingMode
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbsGroup
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbsGroup.SubGroupNameProvider
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

            is IrregularVerbsPracticeAction.ChangeHidingMode -> {
                handleSetHidingModeAction(action)
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
                        subGroupName = verbsStorage.fullyChanging.second.name
                    ),
                    FullyChangingSubGroupViewHolder(
                        subGroupName = verbsStorage.fullyChanging.third.name
                    ),
                    FullyChangingSubGroupViewHolder(
                        subGroupName = verbsStorage.fullyChanging.fourth.name
                    ),
                    FullyChangingSubGroupViewHolder(
                        subGroupName = verbsStorage.fullyChanging.fifth.name
                    ),
                    FullyChangingSubGroupViewHolder(
                        subGroupName = verbsStorage.fullyChanging.sixth.name
                    ),
                    FullyChangingSubGroupViewHolder(
                        subGroupName = verbsStorage.fullyChanging.seventh.name
                    ),
                )
            ),
            IrregularVerbsGroupViewHolder.PartiallyConsistent(
                type = verbsStorage.partiallyConsistent.type,
                subGroups = listOf(
                    PartiallyConsistentSubGroupViewHolder(
                        subGroupName = verbsStorage.partiallyConsistent.first.name
                    ),
                    PartiallyConsistentSubGroupViewHolder(
                        subGroupName = verbsStorage.partiallyConsistent.second.name
                    ),
                    PartiallyConsistentSubGroupViewHolder(
                        subGroupName = verbsStorage.partiallyConsistent.third.name
                    ),
                    PartiallyConsistentSubGroupViewHolder(
                        subGroupName = verbsStorage.partiallyConsistent.fourth.name
                    ),
                    PartiallyConsistentSubGroupViewHolder(
                        subGroupName = verbsStorage.partiallyConsistent.fifth.name
                    ),
                    PartiallyConsistentSubGroupViewHolder(
                        subGroupName = verbsStorage.partiallyConsistent.sixth.name
                    ),
                    PartiallyConsistentSubGroupViewHolder(
                        subGroupName = verbsStorage.partiallyConsistent.seventh.name
                    ),
                    PartiallyConsistentSubGroupViewHolder(
                        subGroupName = verbsStorage.partiallyConsistent.eighth.name
                    ),
                )
            ),
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

    private fun handleSetHidingModeAction(action: IrregularVerbsPracticeAction.ChangeHidingMode) {
        val currentHidingMode = state.value.hidingMode

        val updatedHidingMode = when (action.mode) {
            HidingMode.Non -> action.mode

            HidingMode.Third -> {
                when (currentHidingMode) {
                    HidingMode.SecondAndThird -> HidingMode.Second
                    HidingMode.Third -> HidingMode.Non
                    HidingMode.Second -> HidingMode.SecondAndThird
                    HidingMode.Non -> HidingMode.Third
                }
            }

            HidingMode.Second -> {
                when (currentHidingMode) {
                    HidingMode.SecondAndThird -> HidingMode.Third
                    HidingMode.Third -> HidingMode.SecondAndThird
                    HidingMode.Second -> HidingMode.Non
                    HidingMode.Non -> HidingMode.Second
                }
            }

            HidingMode.SecondAndThird -> currentHidingMode
        }

        state.update { it.copy(hidingMode = updatedHidingMode) }
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

                        is IrregularVerbsGroupViewHolder.PartiallyConsistent -> {
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
                    selectedVerbGroup.retrieveVerbsDetails(
                        groupType = IrregularVerbGroupType.PartiallyConsistent
                    )
                }

                is IrregularVerbsGroup.PartiallyChanging -> {
                    selectedVerbGroup.details
                }

                is IrregularVerbsGroup.Unchanging -> {
                    selectedVerbGroup.details
                }

                is IrregularVerbsGroup.FullyChanging -> {
                    selectedVerbGroup.retrieveVerbsDetails(
                        groupType = IrregularVerbGroupType.FullyChanging
                    )
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
                when (groupViewHolder) {
                    is IrregularVerbsGroupViewHolder.FullyChanging -> {
                        val updatedSubHolders =
                            groupViewHolder.subGroups.map { subGroupViewHolder ->
                                if (subGroupViewHolder == action.subGroupViewHolder) {
                                    subGroupViewHolder.copy(isSelected = subGroupViewHolder.isSelected.not())
                                } else {
                                    subGroupViewHolder
                                }
                            }

                        groupViewHolder.copy(subGroups = updatedSubHolders)
                    }

                    is IrregularVerbsGroupViewHolder.PartiallyConsistent -> {
                        val updatedSubHolders =
                            groupViewHolder.subGroups.map { subGroupViewHolder ->
                                if (subGroupViewHolder == action.subGroupViewHolder) {
                                    subGroupViewHolder.copy(isSelected = subGroupViewHolder.isSelected.not())
                                } else {
                                    subGroupViewHolder
                                }
                            }

                        groupViewHolder.copy(subGroups = updatedSubHolders)
                    }

                    else -> groupViewHolder
                }
            }
        }
    }

    private fun <Provider> IrregularVerbsGroup.SubGroupProvider<Provider>.retrieveVerbsDetails(
        groupType: IrregularVerbGroupType
    ): List<VerbDetails>
            where Provider : SubGroupNameProvider,
                  Provider : IrregularVerbsGroup.SubGroupDetailsProvider {
        return this.filterSelectedSubGroups(groupType = groupType).flatMap { it.details }
    }

    private fun <Provider : SubGroupNameProvider> IrregularVerbsGroup.SubGroupProvider<Provider>.filterSelectedSubGroups(
        groupType: IrregularVerbGroupType
    ): List<Provider> {
        val selectedSubGroupNames = verbsGroupViewHolders.value.asSequence()
            .filter { it.type == groupType }
            .mapNotNull { it as? IrregularVerbsGroupViewHolder.SubGroupViewHolderProvider<*> }
            .flatMap { it.subGroups }
            .filter { it.isSelected }
            .map { it.subGroupName }
            .toSet()

        return subGroups.filter { it.name in selectedSubGroupNames }
    }
}