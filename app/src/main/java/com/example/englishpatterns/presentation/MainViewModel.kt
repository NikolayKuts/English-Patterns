package com.example.englishpatterns.presentation

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import com.example.englishpatterns.R
import com.example.englishpatterns.domain.Pattern
import com.example.englishpatterns.domain.Pattern.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

class MainViewModel(context: Application) : AndroidViewModel(context) {

    val patternHolders: StateFlow<List<PatternHolder>> = MutableStateFlow(value = getPatternHolders())


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

class PatternHolder(
    val pattern: Pattern,
    val isChosen: Boolean,
)