package com.example.englishpatterns.presentation.patternPractisingScreen

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.englishpatterns.data.TextAudioPlayer
import com.example.englishpatterns.data.common.Constants
import com.example.englishpatterns.data.common.LoadingState
import com.example.englishpatterns.data.yandexApi.YandexWordInfoProvider
import com.example.englishpatterns.domain.PatternGroupUnitState
import com.example.englishpatterns.domain.PatternManager
import com.example.englishpatterns.domain.RawPatternGroup
import com.lib.lokdroid.core.logD
import com.lib.lokdroid.core.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PatternPracticingViewModel(
    private val context: Application,
    private val selectedTextProvider: YandexWordInfoProvider = YandexWordInfoProvider(),
    override val textAudioPlayer: TextAudioPlayer = TextAudioPlayer(),
    rawPatternGroups: List<RawPatternGroup>,
) : PatternPracticingBaseViewModel() {

    companion object {

        private const val PATTERN_GROUP_CHUNK_SIZE = 6
    }

    override val state = MutableStateFlow(value = PatternPracticingState())

    override val eventState = MutableSharedFlow<PatternPracticingEvent>()

    private val patternGroupHoldersSate: MutableStateFlow<List<PatternGroupHolder>> =
        MutableStateFlow(value = rawPatternGroups.toChunkedPatternGroupHolders())

    private val currentPatterGroupUnitState = MutableStateFlow<PatternGroupUnitState?>(value = null)

    private val currentPracticePatternGroupHolderSate = MutableStateFlow<PatternGroupHolder?>(
        value = patternGroupHoldersSate.value.mapToSingleChosenPatternHolderGroup()
    )

    private var patternManager = PatternManager(
        patternGroupHolder = currentPracticePatternGroupHolderSate.value
    )

    private var fetchTextInfoJob: Job? = null

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
        logD("sendAction() called. action: $action")

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

            PatternPracticingAction.NextPatter -> {
                currentPatterGroupUnitState.value = patternManager.nextPatternGroupUnitState()
            }

            PatternPracticingAction.ShufflePatternPairs -> {
                currentPracticePatternGroupHolderSate.value =
                    patternGroupHoldersSate.value.mapToSingleChosenShuffledPatternGroupHolder()

                patternManager = PatternManager(
                    patternGroupHolder = currentPracticePatternGroupHolderSate.value
                )
                currentPatterGroupUnitState.value = patternManager.nextPatternGroupUnitState()
            }

            PatternPracticingAction.ChangeAllPatternGroupHoldersSelectionState -> {
                patternGroupHoldersSate.update { patternGroupHolders ->
                    val revertedFirstElementSelectionState = patternGroupHolders.firstOrNull()
                        ?.isChosen
                        ?.not()
                        ?: false

                    patternGroupHolders.map { it.copy(isChosen = revertedFirstElementSelectionState) }
                }
                resetCurrentPatternGroupUnitState()
            }

            PatternPracticingAction.SelectNextPatternGroup -> {
                manageSelectingNextPatternGroup()
            }

            is PatternPracticingAction.AddPatternAsWeaklyMemorized -> {
                manageAddingPatternAsWeaklyMemorized()
            }

            PatternPracticingAction.ChangeTranslationVisibilityState -> {
                manageTranslationVisibilityState()
            }

            PatternPracticingAction.PreviousPatter -> {
                currentPatterGroupUnitState.value = patternManager.previousPatternGroupUnitState()
            }

            is PatternPracticingAction.SelectedTextInfoRequired -> {
                handelSelectedTextInfoRequest(action = action)
            }

            is PatternPracticingAction.TextPronunciationRequired -> {
                textAudioPlayer.play()
            }

            is PatternPracticingAction.SelectedTextSearchRequired -> {
                handleSelectedTextSearchRequest(action = action)
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

    private fun List<PatternGroupHolder>.mapToSingleChosenPatternHolderGroup(): PatternGroupHolder {
        return PatternGroupHolder(
            patterns = this.filter { it.isChosen }.map { it.patterns }.flatten()
        )
    }

    private fun List<PatternGroupHolder>.mapToSingleChosenShuffledPatternGroupHolder(): PatternGroupHolder {
        val shuffledPatterns = this.filter { it.isChosen }
            .map { it.patterns.shuffled() }
            .shuffled()
            .flatten()
            .shuffled()

        return PatternGroupHolder(patterns = shuffledPatterns)
    }

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
        val currentPattern = currentPatterGroupUnitState.value?.pattern ?: return

        patternGroupHoldersSate.update {
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

            patternGroupHoldersSate.value.firstOrNull { holder ->
                holder.isWeaklyMemorized && holder.isChosen
            }?.let { updateCurrentPatternGroupUnitState(newPattern = currentPattern) }

            patternGroupHoldersToUpdate
        }
    }

    private fun manageTranslationVisibilityState() {
        state.update { it.copy(isTranslationHidden = it.isTranslationHidden.not()) }
    }

    private fun handelSelectedTextInfoRequest(
        action: PatternPracticingAction.SelectedTextInfoRequired
    ) {
        if (action.text.isBlank()) return

        fetchTextInfoJob?.cancel()

        fetchTextInfoJob = viewModelScope.launch(Dispatchers.IO) {
            selectedTextProvider.fetchTextInfo(word = action.text).collect { loadingState ->
                logE("fetchTextInfo() collector: $loadingState")

                val textTranslation = when (loadingState) {
                    LoadingState.Non -> ""
                    is LoadingState.Error -> ""
                    LoadingState.Loading -> ""
                    is LoadingState.Success -> "[ ${loadingState.data.transcription} ]"
                }

                textAudioPlayer.preparePronunciation(text = action.text)

                state.update {
                    val updatedSelectedTextTranslation = it.selectedTextInfo.copy(
                        transcription = textTranslation
                    )

                    it.copy(selectedTextInfo = updatedSelectedTextTranslation)
                }
            }
        }
    }

    private fun handleSelectedTextSearchRequest(
        action: PatternPracticingAction.SelectedTextSearchRequired
    ) {
        val url = "${Constants.WordHunt.BASE_URL}${action.text}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        viewModelScope.launch {
            eventState.emit(PatternPracticingEvent.SearchSelectedTextRequired(intent = intent))
        }
    }

    private fun resetCurrentPatternGroupUnitState() {
        currentPracticePatternGroupHolderSate.value =
            patternGroupHoldersSate.value.mapToSingleChosenPatternHolderGroup()

        patternManager = PatternManager(
            patternGroupHolder = currentPracticePatternGroupHolderSate.value
        )
        currentPatterGroupUnitState.value = patternManager.nextPatternGroupUnitState()
    }

    /**
     * When adding a weakly memorized pattern, it is not required to update the entire practice progress.
     * Only the state of the counter needs to be updated, and that only if the group containing
     * weakly memorized patterns is one of the selected groups.
     * **/
    private fun updateCurrentPatternGroupUnitState(newPattern: Pattern) {
        currentPracticePatternGroupHolderSate.update {
            it?.copy(patterns = it.patterns + newPattern)
        }

        currentPatterGroupUnitState.value = patternManager.updatedPatternGroupUnitState(
            updatedPatternGroupHolder = currentPracticePatternGroupHolderSate.value
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