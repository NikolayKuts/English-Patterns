package com.example.englishpatterns.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishpatterns.domain.*
import com.example.englishpatterns.domain.Pattern.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val context: Application) : AndroidViewModel(context) {

    private val patternHolders = MutableStateFlow(value = getPatternHolders())
    private val chosenPatterns = getChosenPatterns()
    private val chosenPatternPairGroups =
        MutableStateFlow<List<PatternPairGroup>>(value = emptyList())

    private val currentPatter = MutableStateFlow<PatternPairGroupState?>(value = null)
    private var patternManager = PatternManager(
        patternPairGroup = chosenPatternPairGroups.value.mapToSingleGroup()
    )

    private val _state = MutableStateFlow<State>(
        value = State.InitialState(patternHolderSource = patternHolders)
    )
    val state = _state.asStateFlow()

    init {
        observeChosenPatterns()
    }

    fun sendEvent(event: Event) {
        when (event) {
            Event.DisplayMainScreen -> {
                _state.value = State.InitialState(patternHolderSource = patternHolders)
            }
            Event.NavigateToPatternPracticing -> {
                _state.value = State.PatternPracticingState(
                    patternPairGroups = chosenPatternPairGroups,
                    currentPattern = currentPatter
                )
            }
            is Event.ChangePatterHolderChoosingState -> {
                changePatterHolderChoosingState(
                    position = event.position,
                    patternHolder = event.patternHolder
                )
            }
            is Event.ChangePairGroupChoosingState -> {
                chosenPatternPairGroups.update {
                    it.toMutableList().apply {
                        val group = this[event.position]
                        this[event.position] = group.copy(isChosen = !group.isChosen)
                    }
                }

                patternManager = PatternManager(
                    patternPairGroup = chosenPatternPairGroups.value.filter { it.isChosen }
                        .mapToSingleGroup()
                )
                currentPatter.value = patternManager.nextPattern()
            }
            Event.NextPatterPair -> {
                currentPatter.value = patternManager.nextPattern()
            }
            Event.ShufflePatternPairs -> {
                patternManager = PatternManager(
                    patternPairGroup = chosenPatternPairGroups.value.filter { it.isChosen }
                        .mapToSingleShuffledGroup()
                )
                currentPatter.value = patternManager.nextPattern()
            }
        }
    }

    private fun getChosenPatterns(): Flow<List<PatternPair>> {
        return patternHolders.map {
            it.filter { holder -> holder.isChosen }
                .map { holder -> holder.pattern }
                .map { chosenPattern ->
                    context.getString(chosenPattern.resId).split("@")
                        .map { rowPair ->
                            PatternPair(
                                native = rowPair.substringBefore("=="),
                                translation = rowPair.substringAfter("==")
                            )
                        }
                }.flatten()
        }
    }

    private fun observeChosenPatterns() {
        viewModelScope.launch {
            chosenPatterns.collect { patternPairs ->
                chosenPatternPairGroups.value = patternPairs.chunked(size = 6)
                    .map { pairs -> PatternPairGroup(isChosen = false, pairs = pairs) }
            }
        }
    }

    private fun List<PatternPairGroup>.mapToSingleGroup(): PatternPairGroup = PatternPairGroup(
        pairs = this.map { it.pairs }.flatten()
    )

    private fun List<PatternPairGroup>.mapToSingleShuffledGroup(): PatternPairGroup = PatternPairGroup(
        pairs = this.map { it.pairs.shuffled() }.shuffled().flatten()
    )

    private fun changePatterHolderChoosingState(position: Int, patternHolder: PatternHolder) {
        patternHolders.update { holders ->
            holders.toMutableList()
                .apply {
                    this[position] = patternHolder.copy(isChosen = !patternHolder.isChosen)
                }
        }
    }

    private fun getPatternHolders(): List<PatternHolder> {
        return listOf(
            PatternHolder(pattern = PossessivePronouns(), isChosen = true),
            PatternHolder(pattern = ThisThatTheseThose(), isChosen = false),
            PatternHolder(pattern = PossessiveCaseOfNouns(), isChosen = false),
            PatternHolder(pattern = ToBeAdjectivesAffirmative(), isChosen = false),
            PatternHolder(pattern = ToBeAdjectivesQuestions(), isChosen = false),
            PatternHolder(pattern = ToBeAdjectivesNegative(), isChosen = false),
            PatternHolder(pattern = ToBeAdjectivesMixed(), isChosen = false),
            PatternHolder(pattern = ToBeSpecialQuestions(), isChosen = false),
            PatternHolder(pattern = VerbToBeArticle(), isChosen = false),
            PatternHolder(pattern = ThisIsA(), isChosen = false),
            PatternHolder(pattern = TheNounBe(), isChosen = false),
            PatternHolder(pattern = PresentSimple(), isChosen = false),
            PatternHolder(pattern = Ordinals(), isChosen = false),
            PatternHolder(pattern = TimePrepositionsAt(), isChosen = false),
            PatternHolder(pattern = TimePrepositionsIn(), isChosen = false),
            PatternHolder(pattern = TimePrepositionsOn(), isChosen = false),
            PatternHolder(pattern = TimePrepositionsMixed(), isChosen = false),
            PatternHolder(pattern = PresentSimpleTimePrepositions(), isChosen = false),
            PatternHolder(pattern = PresentSimpleFrequencyAdverbs(), isChosen = false),
            PatternHolder(pattern = PresentSimpleGo(), isChosen = false),
            PatternHolder(pattern = PresentSimplePlay(), isChosen = false),
            PatternHolder(pattern = PossessivePronounsSecond(), isChosen = false),
            PatternHolder(pattern = ObjectPronouns(), isChosen = false),
            PatternHolder(pattern = PresentSimpleLesson2(), isChosen = false),
            PatternHolder(pattern = LikeVIng(), isChosen = false),
            PatternHolder(pattern = BeFondKeenInterestedCrazy(), isChosen = false),
            PatternHolder(pattern = PresentProcess(), isChosen = false),
        )
    }
}

data class PatternHolder(
    val pattern: Pattern,
    val isChosen: Boolean,
)