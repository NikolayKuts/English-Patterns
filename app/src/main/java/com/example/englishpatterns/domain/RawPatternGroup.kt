package com.example.englishpatterns.domain

import androidx.annotation.StringRes
import com.example.englishpatterns.R
import kotlinx.serialization.Serializable

@Serializable
sealed class RawPatternGroup(
    val name: String,
    @StringRes vararg val contentResIds: Int
) {

    @Serializable
    class PossessivePronouns : RawPatternGroup(
        name = "Possessive pronouns",
        contentResIds = intArrayOf(R.array.possessive_pronouns)
    )

    @Serializable
    class ThisThatTheseThose : RawPatternGroup(
        name = "This/that - these/those",
        contentResIds = intArrayOf(R.array.this_that_these_those)
    )

    @Serializable
    class PossessiveCaseOfNouns : RawPatternGroup(
        name = "Possessive case of nouns",
        contentResIds = intArrayOf(R.array.possessive_case_of_nouns)
    )

    @Serializable
    class ToBeAdjectivesAffirmative : RawPatternGroup(
        name = "To be + adjectives affirmative",
        contentResIds = intArrayOf(R.array.to_be_adjectives_affirmative)
    )

    @Serializable
    class ToBeAdjectivesQuestions : RawPatternGroup(
        name = "To be + adjectives questions",
        contentResIds = intArrayOf(R.array.to_be_adjectives_questions)
    )

    @Serializable
    class ToBeAdjectivesNegative : RawPatternGroup(
        name = "To be + adjectives negative",
        contentResIds = intArrayOf(R.array.to_be_adjectives_negative)
    )

    @Serializable
    class ToBeAdjectivesMixed : RawPatternGroup(
        name = "To be + adjectives mixed",
        contentResIds = intArrayOf(R.array.to_be_adjectives_mixed)
    )

    @Serializable
    class ToBeSpecialQuestions : RawPatternGroup(
        name = "To be + special questions",
        contentResIds = intArrayOf(R.array.to_be_special_questions)
    )

    @Serializable
    class VerbToBeArticle : RawPatternGroup(
        name = "Verb to be + article",
        contentResIds = intArrayOf(R.array.verb_to_be_article)
    )

    @Serializable
    class ThisIsA : RawPatternGroup(
        name = "This is a",
        contentResIds = intArrayOf(R.array.this_is_a)
    )

    @Serializable
    class TheNounBe : RawPatternGroup(
        name = "The + noun + be",
        contentResIds = intArrayOf(R.array.the_noun_be)
    )

    @Serializable
    class PresentSimple : RawPatternGroup(
        name = "Present Simple",
        contentResIds = intArrayOf(R.array.present_simple)
    )

    @Serializable
    class Ordinals : RawPatternGroup(
        name = "Ordinals",
        contentResIds = intArrayOf(R.array.ordinals)
    )

    @Serializable
    class TimePrepositionsAt : RawPatternGroup(
        name = "Time prepositions at",
        contentResIds = intArrayOf(R.array.time_prepositions_at)
    )

    @Serializable
    class TimePrepositionsIn : RawPatternGroup(
        name = "Time prepositions in",
        contentResIds = intArrayOf(R.array.time_prepositions_in)
    )

    @Serializable
    class TimePrepositionsOn : RawPatternGroup(
        name = "Time prepositions on",
        contentResIds = intArrayOf(R.array.time_prepositions_on)
    )

    @Serializable
    class TimePrepositionsMixed : RawPatternGroup(
        name = "Time prepositions mixed",
        contentResIds = intArrayOf(R.array.time_prepositions_mixed)
    )

    @Serializable
    class PresentSimpleTimePrepositions : RawPatternGroup(
        name = "Present Simple + time prepositions",
        contentResIds = intArrayOf(R.array.present_simple_time_prepositions)
    )

    @Serializable
    class PresentSimpleFrequencyAdverbs : RawPatternGroup(
        name = "Present Simple frequency adverbs",
        contentResIds = intArrayOf(R.array.present_simple_frequency_adverbs)
    )

    @Serializable
    class PresentSimpleGo : RawPatternGroup(
        name = "Present Simple + go",
        contentResIds = intArrayOf(R.array.present_simple_go)
    )

    @Serializable
    class PresentSimplePlay : RawPatternGroup(
        name = "Present Simple + play",
        contentResIds = intArrayOf(R.array.present_simple_play)
    )

    @Serializable
    class PossessivePronounsSecond : RawPatternGroup(
        name = "Possessive pronouns 2",
        contentResIds = intArrayOf(R.array.possessive_pronouns_2)
    )

    @Serializable
    class ObjectPronouns : RawPatternGroup(
        name = "Object pronouns",
        contentResIds = intArrayOf(R.array.object_pronouns)
    )

    @Serializable
    class PresentSimpleLesson2 : RawPatternGroup(
        name = "Present simple lessen 2",
        contentResIds = intArrayOf(R.array.present_simple_lesson_2)
    )

    @Serializable
    class LikeVIng : RawPatternGroup(
        name = "Like + Ving",
        contentResIds = intArrayOf(R.array.like_Ving)
    )

    @Serializable
    class BeFondKeenInterestedCrazy : RawPatternGroup(
        name = "Be + found of + keen on..",
        contentResIds = intArrayOf(R.array.be_fond_of_be_keen_on_be_interested_in_be_crazy_about)
    )

    @Serializable
    class PresentProcess : RawPatternGroup(
        name = "Present Process",
        contentResIds = intArrayOf(R.array.present_process)
    )

    @Serializable
    class PresentTypicalVsPresentProcess : RawPatternGroup(
        name = "Present Typical vs Present Process",
        contentResIds = intArrayOf(R.array.present_typical_vs_present_process)
    )

    @Serializable
    class LocationPrepositions : RawPatternGroup(
        name = "Location Prepositions",
        contentResIds = intArrayOf(R.array.location_prepositions)
    )

    @Serializable
    class ThereIsInstallation : RawPatternGroup(
        name = "There is installation",
        contentResIds = intArrayOf(R.array.there_is_installation)
    )

    @Serializable
    class ThereIsThereArePrepositions : RawPatternGroup(
        name = "There is / there are + prepositions",
        contentResIds = intArrayOf(R.array.there_is_there_are_prepositions)
    )

    @Serializable
    class ManyMuch : RawPatternGroup(
        name = "Many / much",
        contentResIds = intArrayOf(R.array.many_much)
    )

    @Serializable
    class MuchManyLittleFew : RawPatternGroup(
        name = "Much / many / little / few",
        contentResIds = intArrayOf(R.array.much_many_little_few)
    )

    @Serializable
    class SomeAnyNo : RawPatternGroup(
        name = "Some / any / no",
        contentResIds = intArrayOf(R.array.some_any_no)
    )

    @Serializable
    class PrepositionsInAtToOn : RawPatternGroup(
        name = "Prepositions in/at, to, on",
        contentResIds = intArrayOf(R.array.prepositions_in_at_to_on)
    )

    @Serializable
    class MovementAroundTheCity : RawPatternGroup(
        name = "Movement around the city",
        contentResIds = intArrayOf(R.array.movement_around_the_city)
    )

    @Serializable
    class PrepositionsCityThereIs : RawPatternGroup(
        name = "Prepositions City there is",
        contentResIds = intArrayOf(R.array.prepositions_city_there_is)
    )

    @Serializable
    class PresentSimpleCity : RawPatternGroup(
        name = "Present simple CITY",
        contentResIds = intArrayOf(R.array.present_simple_city)
    )

    @Serializable
    class PresentSimpleFood : RawPatternGroup(
        name = "Present Simple Food",
        contentResIds = intArrayOf(R.array.present_simple_food)
    )

    @Serializable
    class PresentProcessWorkAndLeisure : RawPatternGroup(
        name = "Present Process WORK and LEISURE",
        contentResIds = intArrayOf(R.array.present_process_work_and_leisure)
    )

    @Serializable
    class PresentProcessRelationship : RawPatternGroup(
        name = "Present Process + Relationship",
        contentResIds = intArrayOf(R.array.present_process_relationship)
    )

    @Serializable
    class PresentSimpleCont : RawPatternGroup(
        name = "Present simple cont.",
        contentResIds = intArrayOf(R.array.present_simple_cont)
    )

    @Serializable
    class PresentSimpleVsPresentProcess : RawPatternGroup(
        name = "Present Simple vs Present Process",
        contentResIds = intArrayOf(R.array.present_simple_vs_present_process)
    )

    @Serializable
    class PresentProcessResult : RawPatternGroup(
        name = "Present Process + Result",
        contentResIds = intArrayOf(R.array.present_process_result)
    )

    @Serializable
    class ProcessProcessResult : RawPatternGroup(
        name = "Process / Process + result",
        contentResIds = intArrayOf(R.array.process_process_result)
    )

    @Serializable
    class WasWere : RawPatternGroup(
        name = "Was / Were ",
        contentResIds = intArrayOf(R.array.was_were)
    )

    @Serializable
    class ThereWasThereWere : RawPatternGroup(
        name = "There was / there were",
        contentResIds = intArrayOf(R.array.there_was_there_were)
    )

    @Serializable
    class PastTypical : RawPatternGroup(
        name = "Past Typical",
        contentResIds = intArrayOf(R.array.past_typical)
    )

    @Serializable
    class PastProcess : RawPatternGroup(
        name = "Past process",
        contentResIds = intArrayOf(R.array.past_process)
    )

    @Serializable
    class PresentResult : RawPatternGroup(
        name = "Present Result",
        contentResIds = intArrayOf(R.array.present_result)
    )

    @Serializable
    class ArticlesGeography : RawPatternGroup(
        name = "Articles geography",
        contentResIds = intArrayOf(R.array.articles_geography)
    )

    @Serializable
    class CountriesAndCities : RawPatternGroup(
        name = "Countries and cities",
        contentResIds = intArrayOf(R.array.countries_and_cities)
    )

    @Serializable
    class ToBeUsedToGetUsedTo : RawPatternGroup(
        name = "To be used to / get used to",
        contentResIds = intArrayOf(R.array.to_be_used_to_get_used_to)
    )

    @Serializable
    class UsedTo : RawPatternGroup(
        name = "Used to",
        contentResIds = intArrayOf(R.array.used_to)
    )

    @Serializable
    class AsThingsStandNowAtFirst : RawPatternGroup(
        name = "As things stand now / At first",
        contentResIds = intArrayOf(R.array.as_things_stand_now_at_first)
    )

    @Serializable
    class AtLeastInMyOpinion : RawPatternGroup(
        name = "At least / In my opinion",
        contentResIds = intArrayOf(R.array.at_least_in_my_opinion)
    )

    @Serializable
    class InOtherWordsToSayTheTruth : RawPatternGroup(
        name = "In other words / To say the truth",
        contentResIds = intArrayOf(R.array.in_other_words_To_say_the_truth)
    )

    @Serializable
    class OnTheContraryAsMatterOfFact : RawPatternGroup(
        name = "On the contrary / As a matter of fact",
        contentResIds = intArrayOf(R.array.on_the_contrary_as_a_matter_of_fact)
    )

    @Serializable
    class ThusIfIAmNotMistaken : RawPatternGroup(
        name = "Thus / If I’m not mistaken",
        contentResIds = intArrayOf(R.array.thus_if_i_am_not_mistaken)
    )

    @Serializable
    class VerbsWithPrepositions : RawPatternGroup(
        name = "Verbs with prepositions",
        contentResIds = intArrayOf(R.array.verbs_with_prepositions_1, R.array.verbs_with_prepositions_2)
    )

    @Serializable
    class FutureSimpleForBeginner : RawPatternGroup(
        name = "Future Simple for beginner",
        contentResIds = intArrayOf(R.array.future_simple_for_beginner)
    )

    @Serializable
    class FutureSimpleWillV : RawPatternGroup(
        name = "Future simple will+V",
        contentResIds = intArrayOf(R.array.future_simple_will_v)
    )

    @Serializable
    class Plan100Percents : RawPatternGroup(
        name = "Plan 100%",
        contentResIds = intArrayOf(R.array.plan_100)
    )

    @Serializable
    class Plan50Percents : RawPatternGroup(
        name = "Plan 50%",
        contentResIds = intArrayOf(R.array.plan_50)
    )

    @Serializable
    class FutureProcess : RawPatternGroup(
        name = "Future Process",
        contentResIds = intArrayOf(R.array.future_process)
    )

    @Serializable
    class ZeroConditional : RawPatternGroup(
        name = "Zero conditional",
        contentResIds = intArrayOf(R.array.zero_conditional)
    )

    @Serializable
    class TheFirstConditional : RawPatternGroup(
        name = "The First Conditional",
        contentResIds = intArrayOf(R.array.the_first_conditional)
    )

    @Serializable
    class SecondConditional : RawPatternGroup(
        name = "Second conditional",
        contentResIds = intArrayOf(R.array.second_conditional)
    )

    @Serializable
    class ThirdConditional : RawPatternGroup(
        name = "Third conditional",
        contentResIds = intArrayOf(R.array.third_conditional)
    )

    @Serializable
    class UsingWish : RawPatternGroup(
        name = "Using \"Wish\"",
        contentResIds = intArrayOf(R.array.using_wish)
    )

    @Serializable
    class ThereVerb : RawPatternGroup(
        name = "There + verb",
        contentResIds = intArrayOf(R.array.there_verb)
    )

    @Serializable
    class AdjectivesMore : RawPatternGroup(
        name = "Adjectives more adj. / __er",
        contentResIds = intArrayOf(R.array.adjectives_more)
    )

    @Serializable
    class AdjectivesTheMost : RawPatternGroup(
        name = "Adjectives the most adj. / the __est",
        contentResIds = intArrayOf(R.array.adjectives_the_most)
    )

    @Serializable
    class GoodBadFarOld : RawPatternGroup(
        name = "good/bad/far/old",
        contentResIds = intArrayOf(R.array.good_bad_far_old)
    )

    @Serializable
    class AsAs : RawPatternGroup(
        name = "as …. as",
        contentResIds = intArrayOf(R.array.as_as)
    )

    @Serializable
    class NotSoAsNotAsAs : RawPatternGroup(
        name = "not so … as/ not as ... as",
        contentResIds = intArrayOf(R.array.not_so_as_not_as_as)
    )

    @Serializable
    class TwiceTimesAsAs : RawPatternGroup(
        name = "twice / 3 times as….as",
        contentResIds = intArrayOf(R.array.twice_times_as_as)
    )

    @Serializable
    class TheSameAs : RawPatternGroup(
        name = "The same…as",
        contentResIds = intArrayOf(R.array.the_same_as)
    )

    @Serializable
    class TheThe : RawPatternGroup(
        name = "The...  the",
        contentResIds = intArrayOf(R.array.the_the)
    )

    @Serializable
    class AdjectivesComparativeCity : RawPatternGroup(
        name = "Adjectives - Comparative (City)",
        contentResIds = intArrayOf(R.array.adjectives_comparative_city)
    )

    @Serializable
    class ComparativeAndSuperlativeDegreesOfAdjectives : RawPatternGroup(
        name = "Comparative and superlative degrees of adjectives",
        contentResIds = intArrayOf(R.array.comparative_and_superlative_degrees_of_adjectives)
    )

    @Serializable
    class ModalVerbCan : RawPatternGroup(
        name = "Modal verb CAN",
        contentResIds = intArrayOf(R.array.modal_verb_can)
    )

    @Serializable
    class Could : RawPatternGroup(
        name = "Could",
        contentResIds = intArrayOf(R.array.could)
    )

    @Serializable
    class HaveTo : RawPatternGroup(
        name = "Have to",
        contentResIds = intArrayOf(R.array.have_to)
    )

    @Serializable
    class CanMayMustHaveTo : RawPatternGroup(
        name = "Can, May, Must, Have to",
        contentResIds = intArrayOf(R.array.can_may_must_have_to)
    )

    @Serializable
    class HadToWillHaveTo : RawPatternGroup(
        name= "Had to & Will have to",
        contentResIds = intArrayOf(R.array.had_to_will_have_to)
    )

    @Serializable
    class Gotta : RawPatternGroup(
        name = "Gotta",
        contentResIds = intArrayOf(R.array.gotta)
    )

    @Serializable
    class GonnaGoingTo : RawPatternGroup(
        name = "Gonna = going to",
        contentResIds = intArrayOf(R.array.gonna_going_to)
    )

    @Serializable
    class ModalProbabilities : RawPatternGroup(
        name = "Modal probabilities",
        contentResIds = intArrayOf(R.array.modal_probabilities)
    )

    @Serializable
    class PresentSimplePassive : RawPatternGroup(
        name = "Present Simple Passive",
        contentResIds = intArrayOf(R.array.present_simple_passive)
    )

    @Serializable
    class PastSimplePassive : RawPatternGroup(
        name = "Past Simple Passive",
        contentResIds = intArrayOf(R.array.past_simple_passive)
    )

    @Serializable
    class PresentTypicalPassiveFood : RawPatternGroup(
        name = "Present Typical Passive (Food)",
        contentResIds = intArrayOf(R.array.present_typical_passive_food)
    )

    @Serializable
    class Passive : RawPatternGroup(
        name = "Passive",
        contentResIds = intArrayOf(R.array.passive)
    )

    @Serializable
    class WouldLikeTo : RawPatternGroup(
        name = "Would like to",
        contentResIds = intArrayOf(R.array.would_like_to)
    )

    @Serializable
    class Shall : RawPatternGroup(
        name = "Shall",
        contentResIds = intArrayOf(R.array.shall)
    )

    @Serializable
    class ArticlesFood : RawPatternGroup(
        name = "Articles food",
        contentResIds = intArrayOf(R.array.articles_food)
    )

    @Serializable
    class AtRestaurant : RawPatternGroup(
        name = "At a restaurant",
        contentResIds = intArrayOf(R.array.at_restaurant)
    )

    @Serializable
    class Pronouns : RawPatternGroup(
        name = "Pronouns",
        contentResIds = intArrayOf(R.array.pronouns)
    )

    @Serializable
    class Gerund : RawPatternGroup(
        name = "Gerund",
        contentResIds = intArrayOf(R.array.gerund)
    )

    @Serializable
    class Infinitive : RawPatternGroup(
        name = "Infinitive",
        contentResIds = intArrayOf(R.array.infinitive)
    )

    @Serializable
    class SomethingAnythingNothing : RawPatternGroup(
        name = "Something / anything / nothing",
        contentResIds = intArrayOf(R.array.something_anything_nothing)
    )

    @Serializable
    class TheOtherAnother : RawPatternGroup(
        name = "The other / another",
        contentResIds = intArrayOf(R.array.the_other_another)
    )
}