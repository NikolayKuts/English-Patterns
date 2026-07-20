package com.example.englishpatterns.presentation.patternPractisingScreen

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.englishpatterns.data.IdentifiablePattern
import com.example.englishpatterns.data.Pattern
import com.example.englishpatterns.data.PatternRepository
import com.example.englishpatterns.data.ResourcesContentManager
import com.example.englishpatterns.data.SecretConstants
import com.example.englishpatterns.data.TextAudioPlayer
import com.example.englishpatterns.data.common.ClipboardUnit
import com.example.englishpatterns.data.common.Constants
import com.example.englishpatterns.data.common.LoadingState
import com.example.englishpatterns.data.yandexApi.YandexWordInfoProvider
import com.example.englishpatterns.domain.PatternGroupResource
import com.example.englishpatterns.domain.PracticingPatternManager
import com.example.englishpatterns.domain.PracticingPatternUnit
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
    private val patternRepository: PatternRepository,
    override val textAudioPlayer: TextAudioPlayer,
    patternGroupResources: List<PatternGroupResource>,
    savedStateHandle: SavedStateHandle,
) : PatternPracticingMviViewModel(savedStateHandle = savedStateHandle) {

    companion object {

        private const val PATTERN_GROUP_CHUNK_SIZE = 6
    }

    override val uiState = MutableStateFlow(value = PatternPracticingState())

    override val eventState = MutableSharedFlow<PatternPracticingEvent>()

    private val stateStore = PatternPracticingStateStore(savedStateHandle = savedStateHandle)

    private val practicingPatternGroupsSate: MutableStateFlow<List<IdentifiablePracticingPatternGroup>> =
        getPatternGroupsAsMutStateFlow(patternGroupResources = patternGroupResources)

    private val currentPatterGroupUnitState = MutableStateFlow<PracticingPatternUnit?>(value = null)

    private val currentChosenPracticingPatternGroupState: MutableStateFlow<IdentifiablePracticingPatternGroup?> =
        getCurrentChosenPractisingPatternGroupSate()

    private var practicingPatternManager = PracticingPatternManager(
        practicingPatternGroup = currentChosenPracticingPatternGroupState.value
    )

    private var fetchTextInfoJob: Job? = null

    init {
        updateCurrentPatterGroupUnitStateBySavedState()
        updateShufflingStateBySavedState()
        observePractisingPatternInitPositionChange()
        observePracticingPatternGroupsSate()
        observeCurrentPatterGroupUnitState()
        observeTextAudioPlayerLoadingState()
        observeWeekPatternStorageChange()
        observeCurrentChosenPracticingPatternGroupState()
        observeUIStateChange()
    }

    override fun sendAction(action: PatternPracticingAction) {
        logD("sendAction() called. action: $action")

        when (action) {
            is PatternPracticingAction.ChangePracticingPatternGroupChoosingState -> {
                practicingPatternGroupsSate.update {
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
                currentChosenPracticingPatternGroupState.value =
                    practicingPatternGroupsSate.value.mapToSingleChosenShuffledGroup()

                setShufflingState(value = true)

                currentPatterGroupUnitState.value = practicingPatternManager.updatedUnit(
                    currentChosenPracticingPatternGroupState.value
                )
            }

            PatternPracticingAction.ChangeAllPracticingPatternGroupsSelectionState -> {
                practicingPatternGroupsSate.update { practicingPatternGroups ->
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

    private fun getPatternGroupsAsMutStateFlow(
        patternGroupResources: List<PatternGroupResource>
    ): MutableStateFlow<List<IdentifiablePracticingPatternGroup>> {
        val groups = stateStore.getIdentifiedPracticingPatternGroups(
            groups = patternGroupResources.toChunkedPracticingPatternGroups()
        )

        return MutableStateFlow(value = groups)
    }

    private fun getCurrentChosenPractisingPatternGroupSate(): MutableStateFlow<IdentifiablePracticingPatternGroup?> {
        val singleGroup =
            practicingPatternGroupsSate.value.mapToSingleChosenPatternGroup()
        val savedCurrentSinglePatternGroup = stateStore.getCurrentSinglePatternGroup(
            singleGroup = singleGroup
        )

        return MutableStateFlow(value = savedCurrentSinglePatternGroup)
    }

    private fun updateCurrentPatterGroupUnitStateBySavedState() {
        stateStore.getSavedPatternUnitPosition()?.let {
            currentPatterGroupUnitState.value = practicingPatternManager.toUnit(position = it)
        }
    }

    private fun updateShufflingStateBySavedState() {
        stateStore.getSavedShufflingState()?.let { isShuffled ->
            uiState.update { it.copy(isPracticingPatternGroupShuffled = isShuffled) }
        }
    }

    private fun observePractisingPatternInitPositionChange() {
        practicingPatternManager.unitPositionState.launchCollect {
            stateStore.saveCurrentPatternUnitPosition(position = it)
        }
    }

    private fun observePracticingPatternGroupsSate() {
        practicingPatternGroupsSate.onEach { groups ->
            uiState.update {
                val weekPatternGroup = groups.firstOrNull { group -> group.isWeaklyMemorized }
                    ?.noId() ?: PracticingPatternGroup()

                val isAddingWeekPatternEnabled = isAddingWeekPatternEnabled(
                    currentPattern = currentPatterGroupUnitState.value?.pattern
                )

                it.copy(
                    practicingPatternGroups = groups.map { group -> group.noId() },
                    weekPracticingPatterGroup = weekPatternGroup,
                    isAddingWeekPatternEnabled = isAddingWeekPatternEnabled
                )
            }

            val chosenGroupsIds = groups.filter { it.isChosen }
                .map { it.id }

            stateStore.saveChosenPatternGroupsIds(ids = chosenGroupsIds)

            groups.firstOrNull { it.isWeaklyMemorized }?.let { weekGroup ->
                stateStore.saveWeekPatterns(weekGroup.identifiablePatterns)
            }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun observeCurrentPatterGroupUnitState() {
        currentPatterGroupUnitState.onEach { currentPatternGroupUnit ->
            uiState.update {
                val weekPatterns = patternRepository.getWeekPatterns()
                val currentPattern = currentPatternGroupUnit?.pattern
                val isStoringWeekPatternEnabled = if (currentPattern != null) {
                    currentPattern.noId() !in weekPatterns
                } else {
                    false
                }
                val isAddingWeekPatternEnabled = isAddingWeekPatternEnabled(
                    currentPattern = currentPatternGroupUnit?.pattern
                )

                it.copy(
                    currentPractisingPatternGroupUnit = currentPatternGroupUnit,
                    isStoringWeekPatternEnabled = isStoringWeekPatternEnabled,
                    isAddingWeekPatternEnabled = isAddingWeekPatternEnabled
                )
            }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun observeTextAudioPlayerLoadingState() {
        textAudioPlayer.loadingState.onEach { pronunciationLoadingState ->
            uiState.update { it.copy(pronunciationLoadingState = pronunciationLoadingState) }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun observeWeekPatternStorageChange() {
        patternRepository.observeWeekPatterns().onEach { weekPatterns ->
            val currentPattern = currentPatterGroupUnitState.value?.pattern
            val isStoringWeekPatternEnabled = if (currentPattern != null) {
                currentPattern.noId() !in weekPatterns
            } else {
                false
            }

            uiState.update { it.copy(isStoringWeekPatternEnabled = isStoringWeekPatternEnabled) }
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun observeCurrentChosenPracticingPatternGroupState() {
        currentChosenPracticingPatternGroupState.onEach { group ->
            stateStore.saveCurrentSingleGroupPatternsIds(group)
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun observeUIStateChange() {
        uiState.launchCollect { state ->
            stateStore.saveShufflingState(state.isPracticingPatternGroupShuffled)
        }
    }

    private fun List<PatternGroupResource>.toChunkedPracticingPatternGroups(): List<IdentifiablePracticingPatternGroup> {
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

        return patternsList.chunkToGroups()
    }

    private fun List<Pattern>.chunkToGroups(): List<IdentifiablePracticingPatternGroup> {
        var globalPatternIndex = 0

        return chunked(PATTERN_GROUP_CHUNK_SIZE).mapIndexed { groupIndex, patterns ->
            val indexedPatterns = patterns.map { pattern ->
                val identifiablePattern =
                    IdentifiablePattern(id = globalPatternIndex, value = pattern)
                globalPatternIndex++
                identifiablePattern
            }

            IdentifiablePracticingPatternGroup(
                id = groupIndex,
                identifiablePatterns = indexedPatterns,
                isChosen = false
            )
        }
    }

    private fun List<IdentifiablePracticingPatternGroup>.mapToSingleChosenPatternGroup(): IdentifiablePracticingPatternGroup {
        return IdentifiablePracticingPatternGroup(
            id = -1,
            identifiablePatterns = this.filter { it.isChosen }.map { it.identifiablePatterns }
                .flatten()
        )
    }

    private fun isAddingWeekPatternEnabled(currentPattern: IdentifiablePattern?): Boolean {
        return practicingPatternGroupsSate.value
            .filter { it.isWeaklyMemorized }
            .none { group -> group.identifiablePatterns.contains(currentPattern) }
                && currentChosenPracticingPatternGroupState.value?.identifiablePatterns?.isNotEmpty() == true
    }

    private fun List<IdentifiablePracticingPatternGroup>.mapToSingleChosenShuffledGroup(): IdentifiablePracticingPatternGroup {
        val shuffledPatterns = this.filter { it.isChosen }
            .map { it.identifiablePatterns.shuffled() }
            .shuffled()
            .flatten()
            .shuffled()

        return IdentifiablePracticingPatternGroup(id = -1, identifiablePatterns = shuffledPatterns)
    }

    private fun manageSelectingNextPatternGroup() {
        val chosenPracticingPatternGroups = practicingPatternGroupsSate.value
        val currentIndex = chosenPracticingPatternGroups.indexOfLast { it.isChosen }

        if (chosenPracticingPatternGroups.isEmpty()) return

        practicingPatternGroupsSate.update {
            it.mapIndexed { index, practicingPatternGroup ->
                practicingPatternGroup.copy(isChosen = index == (currentIndex.inc()) % it.size)
            }
        }

        resetCurrentPatternGroupUnitState()
    }

    private fun manageSelectingPreviousPatternGroup() {
        val chosenPracticingPatternGroups = practicingPatternGroupsSate.value
        val currentIndex = chosenPracticingPatternGroups.indexOfFirst { it.isChosen }

        if (chosenPracticingPatternGroups.isEmpty()) return

        practicingPatternGroupsSate.update {
            it.mapIndexed { index, patternGroup ->
                val targetIndex = if (currentIndex == -1) {
                    it.size.dec()
                } else {
                    (currentIndex.dec() + it.size) % it.size
                }

                patternGroup.copy(isChosen = index == targetIndex)
            }
        }

        resetCurrentPatternGroupUnitState()
    }

    private fun manageAddingPatternAsWeaklyMemorized() {
        val currentPattern = currentPatterGroupUnitState.value?.pattern ?: return

        practicingPatternGroupsSate.update {
            val practicingPatternGroupsToUpdate = it.toMutableList()
            val weaklyMemorizedPracticingPatternGroupIndex =
                practicingPatternGroupsToUpdate.indexOfFirst { group -> group.isWeaklyMemorized }

            if (weaklyMemorizedPracticingPatternGroupIndex != -1) {
                val weaklyMemorizedPracticingPatternGroup =
                    practicingPatternGroupsToUpdate[weaklyMemorizedPracticingPatternGroupIndex]

                if (currentPattern in weaklyMemorizedPracticingPatternGroup.identifiablePatterns) return@update it

                val updatedPatterns =
                    weaklyMemorizedPracticingPatternGroup.identifiablePatterns + currentPattern

                practicingPatternGroupsToUpdate[weaklyMemorizedPracticingPatternGroupIndex] =
                    weaklyMemorizedPracticingPatternGroup.copy(identifiablePatterns = updatedPatterns)
            } else {
                val weekGroup = IdentifiablePracticingPatternGroup(
                    id = practicingPatternGroupsToUpdate.lastIndex.inc(),
                    identifiablePatterns = listOf(currentPattern),
                    isWeaklyMemorized = true
                )
                practicingPatternGroupsToUpdate.add(weekGroup)
            }

            practicingPatternGroupsSate.value.firstOrNull { practicingPatternGroup ->
                practicingPatternGroup.isWeaklyMemorized && practicingPatternGroup.isChosen
            }?.let { updateCurrentPatternGroupUnitState(newPattern = currentPattern) }

            practicingPatternGroupsToUpdate
        }
    }

    private fun manageStoreWeeklyMemorizedPatterAction() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentPattern = currentPatterGroupUnitState.value?.pattern ?: return@launch
            val shouldNotifyAboutSuccessfulStoring = patternRepository.addWeekPattern(
                pattern = currentPattern.noId()
            )

            if (shouldNotifyAboutSuccessfulStoring) {
                eventState.launchEmit { PatternPracticingEvent.WeekPatternStored }
            }
        }
    }

    private fun manageTranslationVisibilityState() {
        uiState.update { it.copy(isTranslationHidden = it.isTranslationHidden.not()) }
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
                uiState.update { it.copy(selectedTextInfo = updatedLoadingState) }
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
                text = currentPatterGroupUnitState.value?.pattern?.value?.translation ?: ""
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
            text = currentPatterGroupUnitState.value?.pattern?.value?.native ?: ""
        )
        val enClipboardUnit = ClipboardUnit(
            text = currentPatterGroupUnitState.value?.pattern?.value?.translation ?: ""
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
            text = currentPatterGroupUnitState.value?.pattern?.value?.translation ?: ""
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
            text = currentPatterGroupUnitState.value?.pattern?.value?.translation ?: ""
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
            val text = currentPatterGroupUnitState.value?.pattern?.value?.translation ?: ""
            PatternPracticingEvent.TextToSpeech(text = text)
        }
    }

    private fun handleSelectedTextToSpeechRequired(
        action: PatternPracticingAction.SelectedTextToSpeechRequired,
    ) {
        eventState.launchEmit { PatternPracticingEvent.TextToSpeech(text = action.value) }
    }

    private fun resetCurrentPatternGroupUnitState() {
        currentChosenPracticingPatternGroupState.value =
            practicingPatternGroupsSate.value.mapToSingleChosenPatternGroup()

        setShufflingState(value = false)

        practicingPatternManager.reset(currentChosenPracticingPatternGroupState.value)

        currentPatterGroupUnitState.value = practicingPatternManager.nextUnit()
    }

    private fun managePatternGroupSelectionStatesIfNoSelected() {
        val isThereAnySelectedGroup = practicingPatternGroupsSate.value.isNotEmpty()
                && practicingPatternGroupsSate.value.none { it.isChosen }

        if (isThereAnySelectedGroup) {
            practicingPatternGroupsSate.update {
                it.mapIndexed { index, practicingPatternGroup ->
                    practicingPatternGroup.copy(isChosen = index == 0)
                }
            }

            setShufflingState(value = false)

            currentChosenPracticingPatternGroupState.value =
                practicingPatternGroupsSate.value.mapToSingleChosenPatternGroup()

            practicingPatternManager.reset(currentChosenPracticingPatternGroupState.value)
        }
    }

    /**
     * When adding a weakly memorized pattern, it is not required to update the entire practice progress.
     * Only the state of the counter needs to be updated, and that only if the group containing
     * weakly memorized patterns is one of the selected groups.
     * **/
    private fun updateCurrentPatternGroupUnitState(newPattern: IdentifiablePattern) {
        currentChosenPracticingPatternGroupState.update {
            it?.copy(identifiablePatterns = it.identifiablePatterns + newPattern)
        }

        currentPatterGroupUnitState.value = practicingPatternManager.updatedUnit(
            updatedPracticingPatternGroup = currentChosenPracticingPatternGroupState.value
        )
    }

    private fun setShufflingState(value: Boolean) {
        val shouldUpdate = value.not() || uiState.value.practicingPatternGroups.any { it.isChosen }

        if (shouldUpdate) {
            uiState.update { it.copy(isPracticingPatternGroupShuffled = value) }
        }
    }

    class Factory(
        private val resourcesContentManager: ResourcesContentManager,
        private val yandexWordInfoProvider: YandexWordInfoProvider,
        private val patternGroupResources: List<PatternGroupResource>,
        private val patternRepository: PatternRepository,
        private val textAudioPlayer: TextAudioPlayer,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras
        ): T = PatternPracticingViewModel(
            resourcesContentManager = resourcesContentManager,
            wordInfoProvider = yandexWordInfoProvider,
            patternRepository = patternRepository,
            patternGroupResources = patternGroupResources,
            textAudioPlayer = textAudioPlayer,
            savedStateHandle = extras.createSavedStateHandle()
        ) as T
    }
}
