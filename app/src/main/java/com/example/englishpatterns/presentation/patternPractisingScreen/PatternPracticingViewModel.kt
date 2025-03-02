package com.example.englishpatterns.presentation.patternPractisingScreen

import android.content.Intent
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.englishpatterns.data.Pattern
import com.example.englishpatterns.data.ResourcesContentManager
import com.example.englishpatterns.data.SecretConstants
import com.example.englishpatterns.data.TextAudioPlayer
import com.example.englishpatterns.data.common.ClipboardUnit
import com.example.englishpatterns.data.common.Constants
import com.example.englishpatterns.data.common.LoadingState
import com.example.englishpatterns.data.yandexApi.YandexWordInfoProvider
import com.example.englishpatterns.domain.PracticingPatternUnit
import com.example.englishpatterns.domain.PracticingPatternManager
import com.example.englishpatterns.domain.PatternGroupResource
import com.lib.lokdroid.core.logD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PatternPracticingViewModel(
    private val resourcesContentManager: ResourcesContentManager,
    private val wordInfoProvider: YandexWordInfoProvider,
    private val weekPatternStorage: DataStore<PracticingPatternGroup>,
    override val textAudioPlayer: TextAudioPlayer,
    patternGroupResources: List<PatternGroupResource>,
) : PatternPracticingMviViewModel() {

    companion object {

        private const val PATTERN_GROUP_CHUNK_SIZE = 6
    }

    override val state = MutableStateFlow(value = PatternPracticingState())

    override val eventState = MutableSharedFlow<PatternPracticingEvent>()

    private val practicingPatternGroupSate: MutableStateFlow<List<PracticingPatternGroup>> =
        MutableStateFlow(value = patternGroupResources.toChunkedPracticingPatternGroups())

    private val currentPatterGroupUnitState = MutableStateFlow<PracticingPatternUnit?>(value = null)

    private val currentPracticingPatternGroupSate = MutableStateFlow<PracticingPatternGroup?>(
        value = practicingPatternGroupSate.value.mapToSingleChosenPracticingPatternGroup()
    )

    private var practicingPatternManager = PracticingPatternManager(
        practicingPatternGroup = currentPracticingPatternGroupSate.value
    )

    private var fetchTextInfoJob: Job? = null

    init {
        practicingPatternGroupSate.onEach { groups ->
            state.update {
                val weekPracticingPatternGroup = groups.firstOrNull { group ->
                    group.isWeaklyMemorized
                } ?: PracticingPatternGroup()

                it.copy(
                    practicingPatternGroups = groups,
                    weekPracticingPatterGroup = weekPracticingPatternGroup
                )
            }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

        currentPatterGroupUnitState.onEach { currentPatternGroupUnit ->
            state.update {
                val weekPatterns = weekPatternStorage.data.firstOrNull()?.patterns ?: return@onEach
                val currentPattern = currentPatternGroupUnit?.pattern
                val isStoringWeekPatternEnabled = if (currentPattern != null) {
                    currentPattern !in weekPatterns
                } else {
                    false
                }

                it.copy(
                    currentPractisingPatternGroupUnit = currentPatternGroupUnit,
                    isStoringWeekPatternEnabled = isStoringWeekPatternEnabled
                )
            }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

        textAudioPlayer.loadingState.onEach { pronunciationLoadingState ->
            state.update { it.copy(pronunciationLoadingState = pronunciationLoadingState) }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

        weekPatternStorage.data.onEach { practicingPatternGroup ->
            val currentPattern = currentPatterGroupUnitState.value?.pattern
            val isStoringWeekPatternEnabled = if (currentPattern != null) {
                currentPattern !in practicingPatternGroup.patterns
            } else {
                false
            }

            state.update { it.copy(isStoringWeekPatternEnabled = isStoringWeekPatternEnabled) }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    override fun sendAction(action: PatternPracticingAction) {
        logD("sendAction() called. action: $action")

        when (action) {
            is PatternPracticingAction.ChangePracticingPatternGroupChoosingState -> {
                practicingPatternGroupSate.update {
                    it.toMutableList().apply {
                        val group = this[action.position]
                        this[action.position] = group.copy(isChosen = !group.isChosen)
                    }
                }

                resetCurrentPatternGroupUnitState()
            }

            PatternPracticingAction.NextPatter -> {
                managePatternGroupSelectionStatesIfNoSelected()
                currentPatterGroupUnitState.value = practicingPatternManager.nextUnit()
            }

            PatternPracticingAction.ShufflePatternPairs -> {
                currentPracticingPatternGroupSate.value =
                    practicingPatternGroupSate.value.mapToSingleChosenShuffledGroup()

                setShufflingState(value = true)

                practicingPatternManager = PracticingPatternManager(
                    practicingPatternGroup = currentPracticingPatternGroupSate.value
                )
                currentPatterGroupUnitState.value = practicingPatternManager.nextUnit()
            }

            PatternPracticingAction.ChangeAllPracticingPatternGroupsSelectionState -> {
                practicingPatternGroupSate.update { practicingPatternGroups ->
                    val revertedFirstElementSelectionState =
                        practicingPatternGroups.all { it.isChosen }
                            .not()

                    practicingPatternGroups.map { it.copy(isChosen = revertedFirstElementSelectionState) }
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

            is PatternPracticingAction.StoreWeaklyMemorizedPattern -> {
                manageStoreWeeklyMemorizedPatterAction()
            }

            PatternPracticingAction.ChangeTranslationVisibilityState -> {
                manageTranslationVisibilityState()
            }

            PatternPracticingAction.PreviousPatter -> {
                currentPatterGroupUnitState.value =
                    practicingPatternManager.previousUnit()
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

            is PatternPracticingAction.TextToSpeechRequired -> {
                handleTextToSpeechRequired()
            }

            is PatternPracticingAction.SelectedTextToSpeechRequired -> {
                handleSelectedTextToSpeechRequired(action = action)
            }
        }
    }

    private fun List<PatternGroupResource>.toChunkedPracticingPatternGroups(): List<PracticingPatternGroup> {
        val patternsList = this.flatMap { rawPatternGroup ->
            rawPatternGroup.contentResIds.flatMap { arrayResId ->
                runBlocking { resourcesContentManager.getStringArray(arrayResId).toList() }
            }.map { rowPair ->
                Pattern(
                    native = rowPair.substringBefore("=="),
                    translation = rowPair.substringAfter("==")
                )
            }
        }

        return patternsList.chunked(size = PATTERN_GROUP_CHUNK_SIZE).map { patterns ->
            PracticingPatternGroup(patterns = patterns, isChosen = false)
        }
    }

    private fun List<PracticingPatternGroup>.mapToSingleChosenPracticingPatternGroup(): PracticingPatternGroup {
        return PracticingPatternGroup(
            patterns = this.filter { it.isChosen }.map { it.patterns }.flatten()
        )
    }

    private fun List<PracticingPatternGroup>.mapToSingleChosenShuffledGroup(): PracticingPatternGroup {
        val shuffledPatterns = this.filter { it.isChosen }
            .map { it.patterns.shuffled() }
            .shuffled()
            .flatten()
            .shuffled()

        return PracticingPatternGroup(patterns = shuffledPatterns)
    }

    private fun manageSelectingNextPatternGroup() {
        val chosenPracticingPatternGroups = practicingPatternGroupSate.value
        val currentIndex = chosenPracticingPatternGroups.indexOfLast { it.isChosen }

        if (chosenPracticingPatternGroups.isEmpty()) return

        practicingPatternGroupSate.update {
            it.mapIndexed { index, practicingPatternGroup ->
                practicingPatternGroup.copy(isChosen = index == (currentIndex.inc()) % it.size)
            }
        }

        resetCurrentPatternGroupUnitState()
    }

    private fun manageSelectingPreviousPatternGroup() {
        val chosenPracticingPatternGroups = practicingPatternGroupSate.value
        val currentIndex = chosenPracticingPatternGroups.indexOfFirst { it.isChosen }

        if (chosenPracticingPatternGroups.isEmpty()) return

        practicingPatternGroupSate.update {
            it.mapIndexed { index, PracticingPatternGroup ->
                val targetIndex = if (currentIndex == -1) {
                    it.size.dec()
                } else {
                    (currentIndex.dec() + it.size) % it.size
                }

                PracticingPatternGroup.copy(isChosen = index == targetIndex)
            }
        }

        resetCurrentPatternGroupUnitState()
    }

    private fun manageAddingPatternAsWeaklyMemorized() {
        val currentPattern = currentPatterGroupUnitState.value?.pattern ?: return

        practicingPatternGroupSate.update {
            val practicingPatternGroupsToUpdate = it.toMutableList()
            val weaklyMemorizedPracticingPatternGroupIndex =
                practicingPatternGroupsToUpdate.indexOfFirst { group -> group.isWeaklyMemorized }

            if (weaklyMemorizedPracticingPatternGroupIndex != -1) {
                val weaklyMemorizedPracticingPatternGroup =
                    practicingPatternGroupsToUpdate[weaklyMemorizedPracticingPatternGroupIndex]

                if (currentPattern in weaklyMemorizedPracticingPatternGroup.patterns) return@update it

                val updatedPatterns =
                    weaklyMemorizedPracticingPatternGroup.patterns + currentPattern

                practicingPatternGroupsToUpdate[weaklyMemorizedPracticingPatternGroupIndex] =
                    weaklyMemorizedPracticingPatternGroup.copy(patterns = updatedPatterns)
            } else {
                practicingPatternGroupsToUpdate.add(
                    PracticingPatternGroup(
                        patterns = listOf(currentPattern),
                        isWeaklyMemorized = true
                    )
                )
            }

            practicingPatternGroupSate.value.firstOrNull { practicingPatternGroup ->
                practicingPatternGroup.isWeaklyMemorized && practicingPatternGroup.isChosen
            }?.let { updateCurrentPatternGroupUnitState(newPattern = currentPattern) }

            practicingPatternGroupsToUpdate
        }
    }

    private fun manageStoreWeeklyMemorizedPatterAction() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentPattern = currentPatterGroupUnitState.value?.pattern ?: return@launch
            var shouldNotifyAboutSuccessfulStoring = false

            weekPatternStorage.updateData { practicingPatternGroup ->
                if (practicingPatternGroup.patterns.none { pattern -> pattern == currentPattern }) {
                    val updatedPatterns = practicingPatternGroup.patterns + listOf(currentPattern)
                    shouldNotifyAboutSuccessfulStoring = true
                    practicingPatternGroup.copy(patterns = updatedPatterns)
                } else {
                    practicingPatternGroup
                }
            }

            if (shouldNotifyAboutSuccessfulStoring) {
                eventState.launchEmit { PatternPracticingEvent.WeekPatternStored }
            }
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
            wordInfoProvider.fetchTextInfo(word = action.text).collect { loadingState ->
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

        eventState.launchEmit {
            PatternPracticingEvent.RedirectionToWordHuntAppRequired(
                intent = intent,
                url = url,
                clipboardUnit = clipboardUnit
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

        eventState.launchEmit {
            PatternPracticingEvent.RedirectionToKlafAppRequired(intent = intent)
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

        eventState.launchEmit {
            PatternPracticingEvent.RedirectionToGhatGptAppRequired(
                intent = intent,
                url = url,
                ruClipboardUnit = ruClipboardUnit,
                enClipboardUnit = enClipboardUnit,
                selectedClipboardUnit = selectedClipboardUnit
            )
        }
    }

    private fun handleRedirectionToYouGlishPageRequest(
        action: PatternPracticingAction.RedirectionToYouGlishPageRequired
    ) {
        val encodedText = Uri.encode(action.text).trim()
        val url = Constants.YouGlish.BASE_URL_WITH_PLACEHOLDER.format(encodedText.lowercase())
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        eventState.launchEmit {
            PatternPracticingEvent.RedirectionToYouGlishPageRequired(intent = intent, url = url)
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


        eventState.launchEmit {
            PatternPracticingEvent.RedirectionToGoogleImagesPageRequired(
                url = url,
                clipboardUnit = clipboardUnit,
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

        eventState.launchEmit {
            PatternPracticingEvent.RedirectionToWordTemplateSearchPageRequired(
                url = url,
                clipboardUnit = clipboardUnit,
            )
        }
    }

    private fun handleTextToSpeechRequired() {
        eventState.launchEmit {
            val text = currentPatterGroupUnitState.value?.pattern?.translation ?: ""
            PatternPracticingEvent.TextToSpeech(text = text)
        }
    }

    private fun handleSelectedTextToSpeechRequired(
        action: PatternPracticingAction.SelectedTextToSpeechRequired,
    ) {
        eventState.launchEmit { PatternPracticingEvent.TextToSpeech(text = action.value) }
    }

    private fun resetCurrentPatternGroupUnitState() {
        currentPracticingPatternGroupSate.value =
            practicingPatternGroupSate.value.mapToSingleChosenPracticingPatternGroup()

        setShufflingState(value = false)

        practicingPatternManager = PracticingPatternManager(
            practicingPatternGroup = currentPracticingPatternGroupSate.value
        )

        currentPatterGroupUnitState.value = practicingPatternManager.nextUnit()
    }

    private fun managePatternGroupSelectionStatesIfNoSelected() {
        if (practicingPatternGroupSate.value.isNotEmpty() && practicingPatternGroupSate.value.all { !it.isChosen }) {
            practicingPatternGroupSate.update {
                it.mapIndexed { index, practicingPatternGroup ->
                    practicingPatternGroup.copy(isChosen = index == 0)
                }
            }

            setShufflingState(value = false)

            currentPracticingPatternGroupSate.value =
                practicingPatternGroupSate.value.mapToSingleChosenPracticingPatternGroup()

            practicingPatternManager = PracticingPatternManager(
                practicingPatternGroup = currentPracticingPatternGroupSate.value
            )
        }
    }

    /**
     * When adding a weakly memorized pattern, it is not required to update the entire practice progress.
     * Only the state of the counter needs to be updated, and that only if the group containing
     * weakly memorized patterns is one of the selected groups.
     * **/
    private fun updateCurrentPatternGroupUnitState(newPattern: Pattern) {
        currentPracticingPatternGroupSate.update {
            it?.copy(patterns = it.patterns + newPattern)
        }

        currentPatterGroupUnitState.value = practicingPatternManager.updatedUnit(
            updatedPracticingPatternGroup = currentPracticingPatternGroupSate.value
        )
    }

    private fun setShufflingState(value: Boolean) {
        val shouldUpdate = value.not() || state.value.practicingPatternGroups.any { it.isChosen }

        if (shouldUpdate) {
            state.update { it.copy(isPracticingPatternGroupShuffled = value) }
        }
    }

    class Factory(
        private val resourcesContentManager: ResourcesContentManager,
        private val yandexWordInfoProvider: YandexWordInfoProvider,
        private val patternGroupResources: List<PatternGroupResource>,
        private val weekPatternStorage: DataStore<PracticingPatternGroup>,
        private val textAudioPlayer: TextAudioPlayer,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = PatternPracticingViewModel(
            resourcesContentManager = resourcesContentManager,
            wordInfoProvider = yandexWordInfoProvider,
            weekPatternStorage = weekPatternStorage,
            patternGroupResources = patternGroupResources,
            textAudioPlayer = textAudioPlayer,
        ) as T
    }
}