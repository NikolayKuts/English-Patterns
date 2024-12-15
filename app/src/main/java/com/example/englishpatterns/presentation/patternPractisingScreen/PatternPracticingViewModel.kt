package com.example.englishpatterns.presentation.patternPractisingScreen

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.englishpatterns.data.SecretConstants
import com.example.englishpatterns.data.TextAudioPlayer
import com.example.englishpatterns.data.common.ClipboardUnit
import com.example.englishpatterns.data.common.Constants
import com.example.englishpatterns.data.common.LoadingState
import com.example.englishpatterns.data.yandexApi.YandexWordInfoProvider
import com.example.englishpatterns.domain.PatternGroupUnitState
import com.example.englishpatterns.domain.PatternManager
import com.example.englishpatterns.domain.RawPatternGroup
import com.lib.lokdroid.core.logD
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

        textAudioPlayer.loadingState.onEach { pronunciationLoadingState ->
            state.update { it.copy(pronunciationLoadingState = pronunciationLoadingState) }
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
                managePatternGroupSelectionStatesIfNoSelected()
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
                    val revertedFirstElementSelectionState = patternGroupHolders.all { it.isChosen }
                        .not()

                    patternGroupHolders.map { it.copy(isChosen = revertedFirstElementSelectionState) }
                }
                resetCurrentPatternGroupUnitState()
            }

            PatternPracticingAction.SelectNextPatternGroup -> {
                manageSelectingNextPatternGroup()
            }

            PatternPracticingAction.SelectPreviousPatternGroup -> {
                manageSelectingPreviousPatternGroup()
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

            is PatternPracticingAction.TextSelected -> {
                handelSelectedTextInfoRequest(action = action)
                requireCustomTabsToWarmUp(action)
            }

            is PatternPracticingAction.TextPronunciationRequired -> {
                textAudioPlayer.play()
            }

            is PatternPracticingAction.SelectedTextSearchRequired -> {
                handleSelectedTextSearchRequest(action = action)
            }

            is PatternPracticingAction.RedirectionToKlafAppRequired -> {
                handleRedirectionToKlafAppRequest(action = action)
            }

            is PatternPracticingAction.RedirectionToChatGptAppRequired -> {
                handleRedirectionToChatGptAppRequest(action = action)
            }

            is PatternPracticingAction.RedirectionToYouGlishPageRequired -> {
                handleRedirectionToYouGlishPageRequest(action = action)
            }

            is PatternPracticingAction.RedirectionToGoogleImagesPageRequired -> {
                handleRedirectionToGoogleImagesPageRequest(action = action)
            }

            is PatternPracticingAction.RedirectionToWordTemplateSearchPageRequired -> {
                handleRedirectionToWordTemplateSearchPageRequest(action = action)
            }
        }
    }

    private fun List<RawPatternGroup>.toChunkedPatternGroupHolders(): List<PatternGroupHolder> {
        val patternsList = this.flatMap { rawPatternGroup ->
            rawPatternGroup.contentResIds.flatMap { arrayResId ->
                context.resources.getStringArray(arrayResId).toList()
            }.filterNotNull()
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
                patternGroupHolder.copy(isChosen = index == (currentIndex.inc()) % it.size)
            }
        }

        resetCurrentPatternGroupUnitState()
    }

    private fun manageSelectingPreviousPatternGroup() {
        val chosenPatternsGroupHolders = patternGroupHoldersSate.value
        val currentIndex = chosenPatternsGroupHolders.indexOfFirst { it.isChosen }

        if (chosenPatternsGroupHolders.isEmpty()) return

        patternGroupHoldersSate.update {
            it.mapIndexed { index, patternGroupHolder ->
                val targetIndex = if (currentIndex == -1) {
                    it.size.dec()
                } else {
                    (currentIndex.dec() + it.size) % it.size
                }

                patternGroupHolder.copy(isChosen = index == targetIndex)
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
        action: PatternPracticingAction.TextSelected
    ) {
        if (action.text.isBlank()) return

        fetchTextInfoJob?.cancel()

        fetchTextInfoJob = viewModelScope.launch(Dispatchers.IO) {
            selectedTextProvider.fetchTextInfo(word = action.text).collect { loadingState ->
                val updatedLoadingState = if (loadingState is LoadingState.Success) {
                    val updatedTranscription = "[ ${loadingState.data.transcription} ]"
                    val updatedLoadingData = loadingState.data.copy(
                        transcription = updatedTranscription
                    )

                    loadingState.copy(data = updatedLoadingData)
                } else {
                    loadingState
                }
                state.update { it.copy(selectedTextInfo = updatedLoadingState) }
            }
        }

        textAudioPlayer.preparePronunciation(text = action.text)
    }

    private fun requireCustomTabsToWarmUp(action: PatternPracticingAction.TextSelected) {
        if (action.text.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            val url =
                "${Constants.ChatGpt.BASE_URL}${SecretConstants.GhatGpt.ENGLISH_PATTERNS_CHAT_ID}"
            val clipboardUnit = ClipboardUnit(
                text = currentPatterGroupUnitState.value?.pattern?.translation ?: ""
            )

            val event = PatternPracticingEvent.WarmupCustomTabs(
                url = url,
                clipboardUnit = clipboardUnit,
            )

            eventState.emit(value = event)
        }
    }

    private fun handleSelectedTextSearchRequest(
        action: PatternPracticingAction.SelectedTextSearchRequired
    ) {
        val url = "${Constants.WordHunt.BASE_URL}${action.text}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val clipboardUnit = ClipboardUnit(text = action.text)

        viewModelScope.launch {
            eventState.emit(
                PatternPracticingEvent.RedirectionToWordHuntAppRequired(
                    intent = intent,
                    url = url,
                    clipboardUnit = clipboardUnit
                )
            )
        }
    }

    private fun handleRedirectionToKlafAppRequest(
        action: PatternPracticingAction.RedirectionToKlafAppRequired
    ) {
        val intent = Intent(Intent.ACTION_PROCESS_TEXT).apply {
            type = Constants.Intent.Type.TEXT_PLAIN
            putExtra(Intent.EXTRA_PROCESS_TEXT, action.text)
            putExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false)
            setPackage(Constants.Klaf.PACKAGE_NAME)
        }

        viewModelScope.launch {
            eventState.emit(PatternPracticingEvent.RedirectionToKlafAppRequired(intent = intent))
        }
    }

    private fun handleRedirectionToChatGptAppRequest(
        action: PatternPracticingAction.RedirectionToChatGptAppRequired,
    ) {
        val url = "${Constants.ChatGpt.BASE_URL}${SecretConstants.GhatGpt.ENGLISH_PATTERNS_CHAT_ID}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val ruClipboardUnit = ClipboardUnit(
            text = currentPatterGroupUnitState.value?.pattern?.native ?: ""
        )
        val enClipboardUnit = ClipboardUnit(
            text = currentPatterGroupUnitState.value?.pattern?.translation ?: ""
        )
        val selectedClipboardUnit = ClipboardUnit(text = action.text)

        viewModelScope.launch {
            eventState.emit(
                PatternPracticingEvent.RedirectionToGhatGptAppRequired(
                    intent = intent,
                    url = url,
                    ruClipboardUnit = ruClipboardUnit,
                    enClipboardUnit = enClipboardUnit,
                    selectedClipboardUnit = selectedClipboardUnit
                )
            )
        }
    }

    private fun handleRedirectionToYouGlishPageRequest(
        action: PatternPracticingAction.RedirectionToYouGlishPageRequired
    ) {
        val encodedText = Uri.encode(action.text).trim()
        val url = Constants.YouGlish.BASE_URL_WITH_PLACEHOLDER.format(encodedText.lowercase())
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        viewModelScope.launch {
            eventState.emit(
                PatternPracticingEvent.RedirectionToYouGlishPageRequired(intent = intent, url = url)
            )
        }
    }

    private fun handleRedirectionToGoogleImagesPageRequest(
        action: PatternPracticingAction.RedirectionToGoogleImagesPageRequired,
    ) {
        val encodedText = Uri.encode(action.text).trim()
        val url = Constants.Google.BASE_URL_WITH_PLACEHOLDER.format(encodedText.lowercase())
        val clipboardUnit = ClipboardUnit(
            text = currentPatterGroupUnitState.value?.pattern?.translation ?: ""
        )

        viewModelScope.launch {
            eventState.emit(
                PatternPracticingEvent.RedirectionToGoogleImagesPageRequired(
                    url = url,
                    clipboardUnit = clipboardUnit,
                )
            )
        }
    }

    private fun handleRedirectionToWordTemplateSearchPageRequest(
        action: PatternPracticingAction.RedirectionToWordTemplateSearchPageRequired
    ) {
        val encodedText = Uri.encode(action.text).trim()
        val url = Constants.Sanstv.BASE_URL_WITH_PLACEHOLDER.format(encodedText.lowercase())
        val clipboardUnit = ClipboardUnit(
            text = currentPatterGroupUnitState.value?.pattern?.translation ?: ""
        )

        viewModelScope.launch {
            eventState.emit(
                PatternPracticingEvent.RedirectionToWordTemplateSearchPageRequired(
                    url = url,
                    clipboardUnit = clipboardUnit,
                )
            )
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

    private fun managePatternGroupSelectionStatesIfNoSelected() {
        if (patternGroupHoldersSate.value.isNotEmpty() && patternGroupHoldersSate.value.all { !it.isChosen }) {
            patternGroupHoldersSate.update {
                it.mapIndexed { index, patternGroupHolder ->
                    patternGroupHolder.copy(isChosen = index == 0)
                }
            }

            currentPracticePatternGroupHolderSate.value =
                patternGroupHoldersSate.value.mapToSingleChosenPatternHolderGroup()

            patternManager = PatternManager(
                patternGroupHolder = currentPracticePatternGroupHolderSate.value
            )
        }
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