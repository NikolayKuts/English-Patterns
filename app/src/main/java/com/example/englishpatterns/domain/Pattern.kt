package com.example.englishpatterns.domain

import androidx.annotation.StringRes
import com.example.englishpatterns.R

sealed class Pattern(val name: String, @StringRes val resId: Int) {

    class PossessivePronouns() : Pattern(
        name = "Possessive pronouns",
        resId = R.string.possessive_pronouns
    )
    class ThisThatTheseThose : Pattern(
        name = "This/that - these/those",
        resId = R.string.this_that_these_those
    )
    class PossessiveCaseOfNouns : Pattern(
        name = "Possessive case of nouns",
        resId = R.string.possessive_case_of_nouns
    )
    class ToBeAdjectivesAffirmative : Pattern(
        name = "To be + adjectives affirmative",
        resId = R.string.to_be_adjectives_affirmative
    )
    class ToBeAdjectivesQuestions : Pattern(
        name = "To be + adjectives questions",
        resId = R.string.to_be_adjectives_questions
    )
    class ToBeAdjectivesNegative : Pattern(
        name = "To be + adjectives negative",
        resId = R.string.to_be_adjectives_negative
    )
    class ToBeAdjectivesMixed : Pattern(
        name = "To be + adjectives mixed",
        resId = R.string.to_be_adjectives_mixed
    )
    class ToBeSpecialQuestions : Pattern(
        name = "To be + special questions",
        resId = R.string.to_be_special_questions
    )
    class VerbToBeArticle : Pattern(
        name = "Verb to be + article",
        resId = R.string.verb_to_be_article
    )
    class ThisIsA : Pattern(
        name = "This is a",
        resId = R.string.this_is_a
    )
    class TheNounBe : Pattern(
        name = "The + noun + be",
        resId = R.string.the_noun_be
    )
    class PresentSimple : Pattern(
        name = "Present Simple",
        resId = R.string.present_simple
    )
    class Ordinals : Pattern(
        name = "Ordinals",
        resId = R.string.ordinals
    )

    class TimePrepositionsAt : Pattern(
        name = "Time prepositions at",
        resId = R.string.time_prepositions_at
    )
    class TimePrepositionsIn : Pattern(
        name = "Time prepositions in",
        resId = R.string.time_prepositions_in
    )
    class TimePrepositionsOn : Pattern(
        name = "Time prepositions on",
        resId = R.string.time_prepositions_on
    )
    class TimePrepositionsMixed : Pattern(
        name = "Time prepositions mixed",
        resId = R.string.time_prepositions_mixed
    )

    class PresentSimpleTimePrepositions : Pattern(
        name = "Present Simple + time prepositions",
        resId = R.string.present_simple_time_prepositions
    )
    class PresentSimpleFrequencyAdverbs : Pattern(
        name = "Present Simple frequency adverbs",
        resId = R.string.present_simple_frequency_adverbs
    )
    class PresentSimpleGo : Pattern(
        name = "Present Simple + go",
        resId = R.string.present_simple_go
    )
    class PresentSimplePlay : Pattern(
        name = "Present Simple + play",
        resId = R.string.present_simple_play
    )
    class PossessivePronounsSecond : Pattern(
        name = "Possessive pronouns 2",
        resId = R.string.possessive_pronouns_2
    )

    class ObjectPronouns : Pattern(
        name = "Object pronouns",
        resId = R.string.object_pronouns
    )

    class PresentSimpleLesson2 : Pattern(
        name = "Present simple lessen 2",
        resId = R.string.present_simple_lesson_2
    )

    class LikeVIng : Pattern(
        name = "Like + Ving",
        resId = R.string.like_Ving
    )

    class BeFondKeenInterestedCrazy : Pattern(
        name = "Be + found of + kenn on..",
        resId = R.string.be_fond_of_be_keen_on_be_interested_in_be_crazy_about
    )

    class PresentProcess : Pattern(
        name = "Present Process",
        resId = R.string.present_process
    )

    class PresentTypicalVsPresentProcess : Pattern(
        name = "PresentTypicalVsPresentProcess",
        resId = R.string.present_typical_vs_present_process
    )
}