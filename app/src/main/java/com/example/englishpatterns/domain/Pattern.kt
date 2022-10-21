package com.example.englishpatterns.domain

import androidx.annotation.StringRes
import com.example.englishpatterns.R

sealed class Pattern(val name: String, @StringRes vararg val resIds: Int) {

    class PossessivePronouns() : Pattern(
        name = "Possessive pronouns",
        resIds = intArrayOf(R.string.possessive_pronouns)
    )

    class ThisThatTheseThose : Pattern(
        name = "This/that - these/those",
        resIds = intArrayOf(R.string.this_that_these_those)
    )

    class PossessiveCaseOfNouns : Pattern(
        name = "Possessive case of nouns",
        resIds = intArrayOf(R.string.possessive_case_of_nouns)
    )

    class ToBeAdjectivesAffirmative : Pattern(
        name = "To be + adjectives affirmative",
        resIds = intArrayOf(R.string.to_be_adjectives_affirmative)
    )

    class ToBeAdjectivesQuestions : Pattern(
        name = "To be + adjectives questions",
        resIds = intArrayOf(R.string.to_be_adjectives_questions)
    )

    class ToBeAdjectivesNegative : Pattern(
        name = "To be + adjectives negative",
        resIds = intArrayOf(R.string.to_be_adjectives_negative)
    )

    class ToBeAdjectivesMixed : Pattern(
        name = "To be + adjectives mixed",
        resIds = intArrayOf(R.string.to_be_adjectives_mixed)
    )

    class ToBeSpecialQuestions : Pattern(
        name = "To be + special questions",
        resIds = intArrayOf(R.string.to_be_special_questions)
    )

    class VerbToBeArticle : Pattern(
        name = "Verb to be + article",
        resIds = intArrayOf(R.string.verb_to_be_article)
    )

    class ThisIsA : Pattern(
        name = "This is a",
        resIds = intArrayOf(R.string.this_is_a)
    )

    class TheNounBe : Pattern(
        name = "The + noun + be",
        resIds = intArrayOf(R.string.the_noun_be)
    )

    class PresentSimple : Pattern(
        name = "Present Simple",
        resIds = intArrayOf(R.string.present_simple)
    )

    class Ordinals : Pattern(
        name = "Ordinals",
        resIds = intArrayOf(R.string.ordinals)
    )

    class TimePrepositionsAt : Pattern(
        name = "Time prepositions at",
        resIds = intArrayOf(R.string.time_prepositions_at)
    )

    class TimePrepositionsIn : Pattern(
        name = "Time prepositions in",
        resIds = intArrayOf(R.string.time_prepositions_in)
    )

    class TimePrepositionsOn : Pattern(
        name = "Time prepositions on",
        resIds = intArrayOf(R.string.time_prepositions_on)
    )

    class TimePrepositionsMixed : Pattern(
        name = "Time prepositions mixed",
        resIds = intArrayOf(R.string.time_prepositions_mixed)
    )

    class PresentSimpleTimePrepositions : Pattern(
        name = "Present Simple + time prepositions",
        resIds = intArrayOf(R.string.present_simple_time_prepositions)
    )

    class PresentSimpleFrequencyAdverbs : Pattern(
        name = "Present Simple frequency adverbs",
        resIds = intArrayOf(R.string.present_simple_frequency_adverbs)
    )

    class PresentSimpleGo : Pattern(
        name = "Present Simple + go",
        resIds = intArrayOf(R.string.present_simple_go)
    )

    class PresentSimplePlay : Pattern(
        name = "Present Simple + play",
        resIds = intArrayOf(R.string.present_simple_play)
    )

    class PossessivePronounsSecond : Pattern(
        name = "Possessive pronouns 2",
        resIds = intArrayOf(R.string.possessive_pronouns_2)
    )

    class ObjectPronouns : Pattern(
        name = "Object pronouns",
        resIds = intArrayOf(R.string.object_pronouns)
    )

    class PresentSimpleLesson2 : Pattern(
        name = "Present simple lessen 2",
        resIds = intArrayOf(R.string.present_simple_lesson_2)
    )

    class LikeVIng : Pattern(
        name = "Like + Ving",
        resIds = intArrayOf(R.string.like_Ving)
    )

    class BeFondKeenInterestedCrazy : Pattern(
        name = "Be + found of + kenn on..",
        resIds = intArrayOf(R.string.be_fond_of_be_keen_on_be_interested_in_be_crazy_about)
    )

    class PresentProcess : Pattern(
        name = "Present Process",
        resIds = intArrayOf(R.string.present_process)
    )

    class PresentTypicalVsPresentProcess : Pattern(
        name = "Present Typical vs Present Process",
        resIds = intArrayOf(R.string.present_typical_vs_present_process)
    )

    class LocationPrepositions : Pattern(
        name = "Location Prepositions",
        resIds = intArrayOf(R.string.location_prepositions)
    )

    class ThereIsInstallation : Pattern(
        name = "There is installation",
        resIds = intArrayOf(R.string.there_is_installation)
    )

    class ThereIsThereArePrepositions : Pattern(
        name = "There is / there are + prepositions",
        resIds = intArrayOf(R.string.there_is_there_are_prepositions)
    )

    class ManyMuch : Pattern(
        name = "Many / much",
        resIds = intArrayOf(R.string.many_much)
    )

    class MuchManyLittleFew : Pattern(
        name = "Much / many / little / few",
        resIds = intArrayOf(R.string.much_many_little_few)
    )

    class SomeAnyNo : Pattern(
        name = "Some / any / no",
        resIds = intArrayOf(R.string.some_any_no)
    )

    class PrepositionsInAtToOn : Pattern(
        name = "Prepositions in/at, to, on",
        resIds = intArrayOf(R.string.prepositions_in_at_to_on)
    )

    class MovementAroundTheCity : Pattern(
        name = "Movement around the city",
        resIds = intArrayOf(R.string.movement_around_the_city)
    )

    class PrepositionsCityThereIs : Pattern(
        name = "Prepositions City there is",
        resIds = intArrayOf(R.string.prepositions_city_there_is)
    )

    class PresentSimpleCity : Pattern(
        name = "Present simple CITY",
        resIds = intArrayOf(R.string.present_simple_city)
    )

    class PresentSimpleFood : Pattern(
        name = "Present Simple Food",
        resIds = intArrayOf(R.string.present_simple_food)
    )

    class PresentProcessWorkAndLeisure : Pattern(
        name = "Present Process WORK and LEISURE",
        resIds = intArrayOf(R.string.present_process_work_and_leisure)
    )

    class PresentProcessRelationship : Pattern(
        name = "Present Process + Relationship",
        resIds = intArrayOf(R.string.present_process_relationship)
    )

    class PresentSimpleCont : Pattern(
        name = "Present simple cont.",
        resIds = intArrayOf(R.string.present_simple_cont)
    )

    class PresentSimpleVsPresentProcess : Pattern(
        name = "Present Simple vs Present Process",
        resIds = intArrayOf(R.string.present_simple_vs_present_process)
    )

    class PresentProcessResult : Pattern(
        name = "Present Process + Result",
        resIds = intArrayOf(R.string.present_process_result)
    )

    class ProcessProcessResult : Pattern(
        name = "Process / Process + result",
        resIds = intArrayOf(R.string.process_process_result)
    )

    class WasWere : Pattern(
        name = "Was / Were ",
        resIds = intArrayOf(R.string.was_were)
    )

    class ThereWasThereWere : Pattern(
        name = "There was / there were",
        resIds = intArrayOf(R.string.there_was_there_were)
    )

    class PastTypical : Pattern(
        name = "Past Typical",
        resIds = intArrayOf(R.string.past_typical)
    )

    class PastProcess : Pattern(
        name = "Past process",
        resIds = intArrayOf(R.string.past_process)
    )

    class PresentResult : Pattern(
        name = "Present Result",
        resIds = intArrayOf(R.string.present_result)
    )

    class ArticlesGeography : Pattern(
        name = "Articles geography",
        resIds = intArrayOf(R.string.articles_geography)
    )

    class CountriesAndCities : Pattern(
        name = "Countries and cities",
        resIds = intArrayOf(R.string.countries_and_cities)
    )

    class ToBeUsedToGetUsedTo : Pattern(
        name = "To be used to / get used to",
        resIds = intArrayOf(R.string.to_be_used_to_get_used_to)
    )

    class UsedTo : Pattern(
        name = "Used to",
        resIds = intArrayOf(R.string.used_to)
    )

    class AsThingsStandNowAtFirst : Pattern(
        name = "As things stand now / At first",
        resIds = intArrayOf(R.string.as_things_stand_now_at_first)
    )

    class AtLeastInMyOpinion : Pattern(
        name = "At least / In my opinion",
        resIds = intArrayOf(R.string.at_least_in_my_opinion)
    )

    class InOtherWordsToSayTheTruth : Pattern(
        name = "In other words / To say the truth",
        resIds = intArrayOf(R.string.in_other_words_To_say_the_truth)
    )

    class OnTheContraryAsMatterOfFact : Pattern(
        name = "On the contrary / As a matter of fact",
        resIds = intArrayOf(R.string.on_the_contrary_as_a_matter_of_fact)
    )

    class ThusIfIAmNotMistaken : Pattern(
        name = "Thus / If I’m not mistaken",
        resIds = intArrayOf(R.string.thus_if_i_am_not_mistaken)
    )

    class VerbsWithPrepositions : Pattern(
        name = "Verbs with prepositions",
        resIds = intArrayOf(R.string.verbs_with_prepositions_1, R.string.verbs_with_prepositions_2)
    )

    class FutureSimpleForBeginner : Pattern(
        name = "Future Simple for beginner",
        resIds = intArrayOf(R.string.future_simple_for_beginner)
    )

    class FutureSimpleWillV : Pattern(
        name = "Future simple will+V",
        resIds = intArrayOf(R.string.future_simple_will_v)
    )

    class Plan100Percents : Pattern(
        name = "Plan 100%",
        resIds = intArrayOf(R.string.plan_100)
    )

    class Plan50Percents : Pattern(
        name = "Plan 50%",
        resIds = intArrayOf(R.string.plan_50)
    )

    class FutureProcess : Pattern(
        name = "Future Process",
        resIds = intArrayOf(R.string.future_process)
    )

    class ZeroConditional : Pattern(
        name = "Zero conditional",
        resIds = intArrayOf(R.string.zero_conditional)
    )

    class TheFirstConditional : Pattern(
        name = "The First Conditional",
        resIds = intArrayOf(R.string.the_first_conditional)
    )

    class SecondConditional : Pattern(
        name = "Second conditional",
        resIds = intArrayOf(R.string.second_conditional)
    )

    class ThirdConditional : Pattern(
        name = "Third conditional",
        resIds = intArrayOf(R.string.third_conditional)
    )

    class UsingWish : Pattern(
        name = "Using \"Wish\"",
        resIds = intArrayOf(R.string.using_wish)
    )

    class ThereVerb : Pattern(
        name = "There + verb",
        resIds = intArrayOf(R.string.there_verb)
    )

    class AdjectivesMore : Pattern(
        name = "Adjectives more adj. / __er",
        resIds = intArrayOf(R.string.adjectives_more)
    )

    class AdjectivesTheMost : Pattern(
        name = "Adjectives the most adj. / the __est",
        resIds = intArrayOf(R.string.adjectives_the_most)
    )

    class GoodBadFarOld : Pattern(
        name = "good/bad/far/old",
        resIds = intArrayOf(R.string.good_bad_far_old)
    )

    class AsAs : Pattern(
        name = "as …. as",
        resIds = intArrayOf(R.string.as_as)
    )

    class NotSoAsNotAsAs : Pattern(
        name = "not so … as/ not as ... as",
        resIds = intArrayOf(R.string.not_so_as_not_as_as)
    )

    class TwiceTimesAsAs : Pattern(
        name = "twice / 3 times as….as",
        resIds = intArrayOf(R.string.twice_times_as_as)
    )

    class TheSameAs : Pattern(
        name = "The same…as",
        resIds = intArrayOf(R.string.the_same_as)
    )

    class TheThe : Pattern(
        name = "The...  the",
        resIds = intArrayOf(R.string.the_the)
    )

    class AdjectivesComparativeCity : Pattern(
        name = "Adjectives - Comparative (City)",
        resIds = intArrayOf(R.string.adjectives_comparative_city)
    )

    class ComparativeAndSuperlativeDegreesOfAdjectives : Pattern(
        name = "Comparative and superlative degrees of adjectives",
        resIds = intArrayOf(R.string.comparative_and_superlative_degrees_of_adjectives)
    )

    class ModalVerbCan : Pattern(
        name = "Modal verb CAN",
        resIds = intArrayOf(R.string.modal_verb_can)
    )

    class Could : Pattern(
        name = "Could",
        resIds = intArrayOf(R.string.could)
    )

    class HaveTo : Pattern(
        name = "Have to",
        resIds = intArrayOf(R.string.have_to)
    )

    class CanMayMustHaveTo : Pattern(
        name = "Can, May, Must, Have to",
        resIds = intArrayOf(R.string.can_may_must_have_to)
    )

    class HadToWillHaveTo : Pattern(
        name= "Had to & Will have to",
        resIds = intArrayOf(R.string.had_to_will_have_to)
    )

    class Gotta : Pattern(
        name = "Gotta",
        resIds = intArrayOf(R.string.gotta)
    )

    class GonnaGoingTo : Pattern(
        name = "Gonna = going to",
        resIds = intArrayOf(R.string.gonna_going_to)
    )

    class ModalProbabilities : Pattern(
        name = "Modal probabilities",
        resIds = intArrayOf(R.string.modal_probabilities)
    )
}