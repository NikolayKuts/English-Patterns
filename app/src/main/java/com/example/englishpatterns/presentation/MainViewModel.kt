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

    private fun List<PatternPairGroup>.mapToSingleShuffledGroup(): PatternPairGroup =
        PatternPairGroup(
            pairs = this.map { it.pairs.shuffled() }.shuffled().flatten().shuffled()
        )

    private fun changePatterHolderChoosingState(position: Int, patternHolder: PatternHolder) {
        patternHolders.update { holders ->
            holders.toMutableList()
                .apply { this[position] = patternHolder.copy(isChosen = !patternHolder.isChosen) }
        }
    }

    private fun getPatternHolders(): List<PatternHolder> {
        return listOf(
            PatternHolder(pattern = PossessivePronouns(), isChosen = true),
        ) + composePatternHolders(
            ThisThatTheseThose(),
            PossessiveCaseOfNouns(),
            ToBeAdjectivesAffirmative(),
            ToBeAdjectivesQuestions(),
            ToBeAdjectivesNegative(),
            ToBeAdjectivesMixed(),
            ToBeSpecialQuestions(),
            VerbToBeArticle(),
            ThisIsA(),
            TheNounBe(),
            PresentSimple(),
            Ordinals(),
            TimePrepositionsAt(),
            TimePrepositionsIn(),
            TimePrepositionsOn(),
            TimePrepositionsMixed(),
            PresentSimpleTimePrepositions(),
            PresentSimpleFrequencyAdverbs(),
            PresentSimpleGo(),
            PresentSimplePlay(),
            PossessivePronounsSecond(),
            ObjectPronouns(),
            PresentSimpleLesson2(),
            LikeVIng(),
            BeFondKeenInterestedCrazy(),
            PresentProcess(),
            PresentProcess(),
            PresentTypicalVsPresentProcess(),
            LocationPrepositions(),
            ThereIsInstallation(),
            ThereIsThereArePrepositions(),
            ManyMuch(),
            MuchManyLittleFew(),
            SomeAnyNo(),
            PrepositionsInAtToOn(),
            MovementAroundTheCity(),
            PrepositionsCityThereIs(),
            PresentSimpleCity(),
            PresentSimpleFood(),
            PresentProcessWorkAndLeisure(),
            PresentProcessRelationship(),
            PresentSimpleCont(),
            PresentSimpleVsPresentProcess(),
        )
    }
}

private fun composePatternHolders(vararg pattern: Pattern): List<PatternHolder> {
    return pattern.map { PatternHolder(pattern = it, isChosen = false) }
}

data class PatternHolder(
    val pattern: Pattern,
    val isChosen: Boolean,
)