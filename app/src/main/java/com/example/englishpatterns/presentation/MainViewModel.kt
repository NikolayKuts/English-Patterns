package com.example.englishpatterns.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishpatterns.domain.Pattern
import com.example.englishpatterns.domain.Pattern.*
import kotlinx.coroutines.flow.*

class MainViewModel(context: Application) : AndroidViewModel(context) {

    private val _patternHolders = MutableStateFlow(value = getPatternHolders())
    private val chosenPatterns = _patternHolders.map {
        it.filter { holder -> holder.isChosen }
            .map { holder -> holder.pattern }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    private val _state = MutableStateFlow<State>(
        value = State.InitialState(patternHolderSource = _patternHolders)
    )
    val state = _state.asStateFlow()

    fun sendEvent(event: Event) {
        when (event) {
            Event.DisplayMainScreen -> {
                _state.value = State.InitialState(patternHolderSource = _patternHolders)
            }
            Event.NavigateToPatternPracticing -> {
                _state.value = State.PatternPracticingState(patternsSource = chosenPatterns)
            }

            is Event.ChangePatterHolderChoosingState -> {
                changePatterHolderChoosingState(
                    position = event.position,
                    patternHolder = event.patternHolder
                )
            }
        }
    }

    private fun changePatterHolderChoosingState(position: Int, patternHolder: PatternHolder) {
        _patternHolders.update { holders ->
            holders.toMutableList()
                .apply { this[position] = patternHolder.copy(isChosen = !patternHolder.isChosen) }
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
        )
    }
}

data class PatternHolder(
    val pattern: Pattern,
    val isChosen: Boolean,
)