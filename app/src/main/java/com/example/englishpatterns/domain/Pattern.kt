package com.example.englishpatterns.domain

import androidx.annotation.StringRes
import com.example.englishpatterns.R
import kotlinx.serialization.Serializable

@Serializable
sealed class Pattern(val name: String, @StringRes vararg val resIds: Int) {

    @Serializable
    class PossessivePronouns() : Pattern(
        name = "Possessive pronouns",
        resIds = intArrayOf(R.string.possessive_pronouns)
    )

    @Serializable
    class ThisThatTheseThose : Pattern(
        name = "This/that - these/those",
        resIds = intArrayOf(R.string.this_that_these_those)
    )

    @Serializable
    class PossessiveCaseOfNouns : Pattern(
        name = "Possessive case of nouns",
        resIds = intArrayOf(R.string.possessive_case_of_nouns)
    )

    @Serializable
    class ToBeAdjectivesAffirmative : Pattern(
        name = "To be + adjectives affirmative",
        resIds = intArrayOf(R.string.to_be_adjectives_affirmative)
    )

    @Serializable
    class ToBeAdjectivesQuestions : Pattern(
        name = "To be + adjectives questions",
        resIds = intArrayOf(R.string.to_be_adjectives_questions)
    )

    @Serializable
    class ToBeAdjectivesNegative : Pattern(
        name = "To be + adjectives negative",
        resIds = intArrayOf(R.string.to_be_adjectives_negative)
    )

    @Serializable
    class ToBeAdjectivesMixed : Pattern(
        name = "To be + adjectives mixed",
        resIds = intArrayOf(R.string.to_be_adjectives_mixed)
    )

    @Serializable
    class ToBeSpecialQuestions : Pattern(
        name = "To be + special questions",
        resIds = intArrayOf(R.string.to_be_special_questions)
    )

    @Serializable
    class VerbToBeArticle : Pattern(
        name = "Verb to be + article",
        resIds = intArrayOf(R.string.verb_to_be_article)
    )

    @Serializable
    class ThisIsA : Pattern(
        name = "This is a",
        resIds = intArrayOf(R.string.this_is_a)
    )

    @Serializable
    class TheNounBe : Pattern(
        name = "The + noun + be",
        resIds = intArrayOf(R.string.the_noun_be)
    )

    @Serializable
    class PresentSimple : Pattern(
        name = "Present Simple",
        resIds = intArrayOf(R.string.present_simple)
    )

    @Serializable
    class Ordinals : Pattern(
        name = "Ordinals",
        resIds = intArrayOf(R.string.ordinals)
    )

    @Serializable
    class TimePrepositionsAt : Pattern(
        name = "Time prepositions at",
        resIds = intArrayOf(R.string.time_prepositions_at)
    )

    @Serializable
    class TimePrepositionsIn : Pattern(
        name = "Time prepositions in",
        resIds = intArrayOf(R.string.time_prepositions_in)
    )

    @Serializable
    class TimePrepositionsOn : Pattern(
        name = "Time prepositions on",
        resIds = intArrayOf(R.string.time_prepositions_on)
    )

    @Serializable
    class TimePrepositionsMixed : Pattern(
        name = "Time prepositions mixed",
        resIds = intArrayOf(R.string.time_prepositions_mixed)
    )

    @Serializable
    class PresentSimpleTimePrepositions : Pattern(
        name = "Present Simple + time prepositions",
        resIds = intArrayOf(R.string.present_simple_time_prepositions)
    )

    @Serializable
    class PresentSimpleFrequencyAdverbs : Pattern(
        name = "Present Simple frequency adverbs",
        resIds = intArrayOf(R.string.present_simple_frequency_adverbs)
    )

    @Serializable
    class PresentSimpleGo : Pattern(
        name = "Present Simple + go",
        resIds = intArrayOf(R.string.present_simple_go)
    )

    @Serializable
    class PresentSimplePlay : Pattern(
        name = "Present Simple + play",
        resIds = intArrayOf(R.string.present_simple_play)
    )

    @Serializable
    class PossessivePronounsSecond : Pattern(
        name = "Possessive pronouns 2",
        resIds = intArrayOf(R.string.possessive_pronouns_2)
    )

    @Serializable
    class ObjectPronouns : Pattern(
        name = "Object pronouns",
        resIds = intArrayOf(R.string.object_pronouns)
    )

    @Serializable
    class PresentSimpleLesson2 : Pattern(
        name = "Present simple lessen 2",
        resIds = intArrayOf(R.string.present_simple_lesson_2)
    )

    @Serializable
    class LikeVIng : Pattern(
        name = "Like + Ving",
        resIds = intArrayOf(R.string.like_Ving)
    )

    @Serializable
    class BeFondKeenInterestedCrazy : Pattern(
        name = "Be + found of + kenn on..",
        resIds = intArrayOf(R.string.be_fond_of_be_keen_on_be_interested_in_be_crazy_about)
    )

    @Serializable
    class PresentProcess : Pattern(
        name = "Present Process",
        resIds = intArrayOf(R.string.present_process)
    )

    @Serializable
    class PresentTypicalVsPresentProcess : Pattern(
        name = "Present Typical vs Present Process",
        resIds = intArrayOf(R.string.present_typical_vs_present_process)
    )

    @Serializable
    class LocationPrepositions : Pattern(
        name = "Location Prepositions",
        resIds = intArrayOf(R.string.location_prepositions)
    )

    @Serializable
    class ThereIsInstallation : Pattern(
        name = "There is installation",
        resIds = intArrayOf(R.string.there_is_installation)
    )

    @Serializable
    class ThereIsThereArePrepositions : Pattern(
        name = "There is / there are + prepositions",
        resIds = intArrayOf(R.string.there_is_there_are_prepositions)
    )

    @Serializable
    class ManyMuch : Pattern(
        name = "Many / much",
        resIds = intArrayOf(R.string.many_much)
    )

    @Serializable
    class MuchManyLittleFew : Pattern(
        name = "Much / many / little / few",
        resIds = intArrayOf(R.string.much_many_little_few)
    )

    @Serializable
    class SomeAnyNo : Pattern(
        name = "Some / any / no",
        resIds = intArrayOf(R.string.some_any_no)
    )

    @Serializable
    class PrepositionsInAtToOn : Pattern(
        name = "Prepositions in/at, to, on",
        resIds = intArrayOf(R.string.prepositions_in_at_to_on)
    )

    @Serializable
    class MovementAroundTheCity : Pattern(
        name = "Movement around the city",
        resIds = intArrayOf(R.string.movement_around_the_city)
    )

    @Serializable
    class PrepositionsCityThereIs : Pattern(
        name = "Prepositions City there is",
        resIds = intArrayOf(R.string.prepositions_city_there_is)
    )

    @Serializable
    class PresentSimpleCity : Pattern(
        name = "Present simple CITY",
        resIds = intArrayOf(R.string.present_simple_city)
    )

    @Serializable
    class PresentSimpleFood : Pattern(
        name = "Present Simple Food",
        resIds = intArrayOf(R.string.present_simple_food)
    )

    @Serializable
    class PresentProcessWorkAndLeisure : Pattern(
        name = "Present Process WORK and LEISURE",
        resIds = intArrayOf(R.string.present_process_work_and_leisure)
    )

    @Serializable
    class PresentProcessRelationship : Pattern(
        name = "Present Process + Relationship",
        resIds = intArrayOf(R.string.present_process_relationship)
    )

    @Serializable
    class PresentSimpleCont : Pattern(
        name = "Present simple cont.",
        resIds = intArrayOf(R.string.present_simple_cont)
    )

    @Serializable
    class PresentSimpleVsPresentProcess : Pattern(
        name = "Present Simple vs Present Process",
        resIds = intArrayOf(R.string.present_simple_vs_present_process)
    )

    @Serializable
    class PresentProcessResult : Pattern(
        name = "Present Process + Result",
        resIds = intArrayOf(R.string.present_process_result)
    )

    @Serializable
    class ProcessProcessResult : Pattern(
        name = "Process / Process + result",
        resIds = intArrayOf(R.string.process_process_result)
    )

    @Serializable
    class WasWere : Pattern(
        name = "Was / Were ",
        resIds = intArrayOf(R.string.was_were)
    )

    @Serializable
    class ThereWasThereWere : Pattern(
        name = "There was / there were",
        resIds = intArrayOf(R.string.there_was_there_were)
    )

    @Serializable
    class PastTypical : Pattern(
        name = "Past Typical",
        resIds = intArrayOf(R.string.past_typical)
    )

    @Serializable
    class PastProcess : Pattern(
        name = "Past process",
        resIds = intArrayOf(R.string.past_process)
    )

    @Serializable
    class PresentResult : Pattern(
        name = "Present Result",
        resIds = intArrayOf(R.string.present_result)
    )

    @Serializable
    class ArticlesGeography : Pattern(
        name = "Articles geography",
        resIds = intArrayOf(R.string.articles_geography)
    )

    @Serializable
    class CountriesAndCities : Pattern(
        name = "Countries and cities",
        resIds = intArrayOf(R.string.countries_and_cities)
    )

    @Serializable
    class ToBeUsedToGetUsedTo : Pattern(
        name = "To be used to / get used to",
        resIds = intArrayOf(R.string.to_be_used_to_get_used_to)
    )

    @Serializable
    class UsedTo : Pattern(
        name = "Used to",
        resIds = intArrayOf(R.string.used_to)
    )

    @Serializable
    class AsThingsStandNowAtFirst : Pattern(
        name = "As things stand now / At first",
        resIds = intArrayOf(R.string.as_things_stand_now_at_first)
    )

    @Serializable
    class AtLeastInMyOpinion : Pattern(
        name = "At least / In my opinion",
        resIds = intArrayOf(R.string.at_least_in_my_opinion)
    )

    @Serializable
    class InOtherWordsToSayTheTruth : Pattern(
        name = "In other words / To say the truth",
        resIds = intArrayOf(R.string.in_other_words_To_say_the_truth)
    )

    @Serializable
    class OnTheContraryAsMatterOfFact : Pattern(
        name = "On the contrary / As a matter of fact",
        resIds = intArrayOf(R.string.on_the_contrary_as_a_matter_of_fact)
    )

    @Serializable
    class ThusIfIAmNotMistaken : Pattern(
        name = "Thus / If I’m not mistaken",
        resIds = intArrayOf(R.string.thus_if_i_am_not_mistaken)
    )

    @Serializable
    class VerbsWithPrepositions : Pattern(
        name = "Verbs with prepositions",
        resIds = intArrayOf(R.string.verbs_with_prepositions_1, R.string.verbs_with_prepositions_2)
    )

    @Serializable
    class FutureSimpleForBeginner : Pattern(
        name = "Future Simple for beginner",
        resIds = intArrayOf(R.string.future_simple_for_beginner)
    )

    @Serializable
    class FutureSimpleWillV : Pattern(
        name = "Future simple will+V",
        resIds = intArrayOf(R.string.future_simple_will_v)
    )

    @Serializable
    class Plan100Percents : Pattern(
        name = "Plan 100%",
        resIds = intArrayOf(R.string.plan_100)
    )

    @Serializable
    class Plan50Percents : Pattern(
        name = "Plan 50%",
        resIds = intArrayOf(R.string.plan_50)
    )

    @Serializable
    class FutureProcess : Pattern(
        name = "Future Process",
        resIds = intArrayOf(R.string.future_process)
    )

    @Serializable
    class ZeroConditional : Pattern(
        name = "Zero conditional",
        resIds = intArrayOf(R.string.zero_conditional)
    )

    @Serializable
    class TheFirstConditional : Pattern(
        name = "The First Conditional",
        resIds = intArrayOf(R.string.the_first_conditional)
    )

    @Serializable
    class SecondConditional : Pattern(
        name = "Second conditional",
        resIds = intArrayOf(R.string.second_conditional)
    )

    @Serializable
    class ThirdConditional : Pattern(
        name = "Third conditional",
        resIds = intArrayOf(R.string.third_conditional)
    )

    @Serializable
    class UsingWish : Pattern(
        name = "Using \"Wish\"",
        resIds = intArrayOf(R.string.using_wish)
    )

    @Serializable
    class ThereVerb : Pattern(
        name = "There + verb",
        resIds = intArrayOf(R.string.there_verb)
    )

    @Serializable
    class AdjectivesMore : Pattern(
        name = "Adjectives more adj. / __er",
        resIds = intArrayOf(R.string.adjectives_more)
    )

    @Serializable
    class AdjectivesTheMost : Pattern(
        name = "Adjectives the most adj. / the __est",
        resIds = intArrayOf(R.string.adjectives_the_most)
    )

    @Serializable
    class GoodBadFarOld : Pattern(
        name = "good/bad/far/old",
        resIds = intArrayOf(R.string.good_bad_far_old)
    )

    @Serializable
    class AsAs : Pattern(
        name = "as …. as",
        resIds = intArrayOf(R.string.as_as)
    )

    @Serializable
    class NotSoAsNotAsAs : Pattern(
        name = "not so … as/ not as ... as",
        resIds = intArrayOf(R.string.not_so_as_not_as_as)
    )

    @Serializable
    class TwiceTimesAsAs : Pattern(
        name = "twice / 3 times as….as",
        resIds = intArrayOf(R.string.twice_times_as_as)
    )

    @Serializable
    class TheSameAs : Pattern(
        name = "The same…as",
        resIds = intArrayOf(R.string.the_same_as)
    )

    @Serializable
    class TheThe : Pattern(
        name = "The...  the",
        resIds = intArrayOf(R.string.the_the)
    )

    @Serializable
    class AdjectivesComparativeCity : Pattern(
        name = "Adjectives - Comparative (City)",
        resIds = intArrayOf(R.string.adjectives_comparative_city)
    )

    @Serializable
    class ComparativeAndSuperlativeDegreesOfAdjectives : Pattern(
        name = "Comparative and superlative degrees of adjectives",
        resIds = intArrayOf(R.string.comparative_and_superlative_degrees_of_adjectives)
    )

    @Serializable
    class ModalVerbCan : Pattern(
        name = "Modal verb CAN",
        resIds = intArrayOf(R.string.modal_verb_can)
    )

    @Serializable
    class Could : Pattern(
        name = "Could",
        resIds = intArrayOf(R.string.could)
    )

    @Serializable
    class HaveTo : Pattern(
        name = "Have to",
        resIds = intArrayOf(R.string.have_to)
    )

    @Serializable
    class CanMayMustHaveTo : Pattern(
        name = "Can, May, Must, Have to",
        resIds = intArrayOf(R.string.can_may_must_have_to)
    )

    @Serializable
    class HadToWillHaveTo : Pattern(
        name= "Had to & Will have to",
        resIds = intArrayOf(R.string.had_to_will_have_to)
    )

    @Serializable
    class Gotta : Pattern(
        name = "Gotta",
        resIds = intArrayOf(R.string.gotta)
    )

    @Serializable
    class GonnaGoingTo : Pattern(
        name = "Gonna = going to",
        resIds = intArrayOf(R.string.gonna_going_to)
    )

    @Serializable
    class ModalProbabilities : Pattern(
        name = "Modal probabilities",
        resIds = intArrayOf(R.string.modal_probabilities)
    )

    @Serializable
    class PresentSimplePassive : Pattern(
        name = "Present Simple Passive",
        resIds = intArrayOf(R.string.present_simple_passive)
    )

    @Serializable
    class PastSimplePassive : Pattern(
        name = "Past Simple Passive",
        resIds = intArrayOf(R.string.past_simple_passive)
    )

    @Serializable
    class PresentTypicalPassiveFood : Pattern(
        name = "Present Typical Passive (Food)",
        resIds = intArrayOf(R.string.present_typical_passive_food)
    )

    @Serializable
    class Passive : Pattern(
        name = "Passive",
        resIds = intArrayOf(R.string.passive)
    )

    @Serializable
    class WouldLikeTo : Pattern(
        name = "Would like to",
        resIds = intArrayOf(R.string.would_like_to)
    )

    @Serializable
    class Shall : Pattern(
        name = "Shall",
        resIds = intArrayOf(R.string.shall)
    )

    @Serializable
    class ArticlesFood : Pattern(
        name = "Articles food",
        resIds = intArrayOf(R.string.articles_food)
    )

    @Serializable
    class AtRestaurant : Pattern(
        name = "At a restaurant",
        resIds = intArrayOf(R.string.at_restaurant)
    )

    @Serializable
    class Pronouns : Pattern(
        name = "Pronouns",
        resIds = intArrayOf(R.string.pronouns)
    )

    @Serializable
    class Gerund : Pattern(
        name = "Gerund",
        resIds = intArrayOf(R.string.gerund)
    )

    @Serializable
    class Infinitive : Pattern(
        name = "Infinitive",
        resIds = intArrayOf(R.string.infinitive)
    )

    @Serializable
    class SomethingAnythingNothing : Pattern(
        name = "Something / anything / nothing",
        resIds = intArrayOf(R.string.something_anything_nothing)
    )

    @Serializable
    class TheOtherAnother : Pattern(
        name = "The other / another",
        resIds = intArrayOf(R.string.the_other_another)
    )
}