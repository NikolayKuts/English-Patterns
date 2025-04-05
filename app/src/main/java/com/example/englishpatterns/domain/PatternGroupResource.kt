package com.example.englishpatterns.domain

import androidx.annotation.StringRes
import com.example.englishpatterns.R
import kotlinx.serialization.Serializable

@Serializable
sealed class PatternGroupResource(
    val name: String,
    @StringRes vararg val contentResIds: Int
) {
    abstract val markColor: MarkColor

    abstract fun toNew(markColor: MarkColor): PatternGroupResource

    @Serializable
    data class PossessivePronouns(
        override val markColor: MarkColor = MarkColor.Non
    ) : PatternGroupResource(
        name = "Possessive pronouns",
        contentResIds = intArrayOf(R.array.possessive_pronouns),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ThisThatTheseThose(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "This/that - these/those",
        contentResIds = intArrayOf(R.array.this_that_these_those),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PossessiveCaseOfNouns(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Possessive case of nouns",
        contentResIds = intArrayOf(R.array.possessive_case_of_nouns),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ToBeAdjectivesAffirmative(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "To be + adjectives affirmative",
        contentResIds = intArrayOf(R.array.to_be_adjectives_affirmative),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ToBeAdjectivesQuestions(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "To be + adjectives questions",
        contentResIds = intArrayOf(R.array.to_be_adjectives_questions),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ToBeAdjectivesNegative(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "To be + adjectives negative",
        contentResIds = intArrayOf(R.array.to_be_adjectives_negative),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ToBeAdjectivesMixed(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "To be + adjectives mixed",
        contentResIds = intArrayOf(R.array.to_be_adjectives_mixed),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ToBeSpecialQuestions(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "To be + special questions",
        contentResIds = intArrayOf(R.array.to_be_special_questions),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class VerbToBeArticle(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Verb to be + article",
        contentResIds = intArrayOf(R.array.verb_to_be_article),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ThisIsA(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "This is a",
        contentResIds = intArrayOf(R.array.this_is_a),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class TheNounBe(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "The + noun + be",
        contentResIds = intArrayOf(R.array.the_noun_be),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentSimple(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present Simple",
        contentResIds = intArrayOf(R.array.present_simple),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class Ordinals(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Ordinals",
        contentResIds = intArrayOf(R.array.ordinals),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class TimePrepositionsAt(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Time prepositions at",
        contentResIds = intArrayOf(R.array.time_prepositions_at),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class TimePrepositionsIn(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Time prepositions in",
        contentResIds = intArrayOf(R.array.time_prepositions_in),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class TimePrepositionsOn(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Time prepositions on",
        contentResIds = intArrayOf(R.array.time_prepositions_on),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class TimePrepositionsMixed(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Time prepositions mixed",
        contentResIds = intArrayOf(R.array.time_prepositions_mixed),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentSimpleTimePrepositions(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present Simple + time prepositions",
        contentResIds = intArrayOf(R.array.present_simple_time_prepositions),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentSimpleFrequencyAdverbs(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present Simple frequency adverbs",
        contentResIds = intArrayOf(R.array.present_simple_frequency_adverbs),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentSimpleGo(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present Simple + go",
        contentResIds = intArrayOf(R.array.present_simple_go),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentSimplePlay(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present Simple + play",
        contentResIds = intArrayOf(R.array.present_simple_play),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PossessivePronounsSecond(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Possessive pronouns 2",
        contentResIds = intArrayOf(R.array.possessive_pronouns_2),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ObjectPronouns(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Object pronouns",
        contentResIds = intArrayOf(R.array.object_pronouns),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentSimpleLesson2(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present simple lessen 2",
        contentResIds = intArrayOf(R.array.present_simple_lesson_2),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class LikeVIng(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Like + Ving",
        contentResIds = intArrayOf(R.array.like_Ving),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class BeFondKeenInterestedCrazy(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Be + found of + keen on..",
        contentResIds = intArrayOf(R.array.be_fond_of_be_keen_on_be_interested_in_be_crazy_about),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentProcess(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present Process",
        contentResIds = intArrayOf(R.array.present_process),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentTypicalVsPresentProcess(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present Typical vs Present Process",
        contentResIds = intArrayOf(R.array.present_typical_vs_present_process),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class LocationPrepositions(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Location Prepositions",
        contentResIds = intArrayOf(R.array.location_prepositions),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ThereIsInstallation(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "There is installation",
        contentResIds = intArrayOf(R.array.there_is_installation),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ThereIsThereArePrepositions(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "There is / there are + prepositions",
        contentResIds = intArrayOf(R.array.there_is_there_are_prepositions),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ManyMuch(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Many / much",
        contentResIds = intArrayOf(R.array.many_much),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class MuchManyLittleFew(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Much / many / little / few",
        contentResIds = intArrayOf(R.array.much_many_little_few),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class SomeAnyNo(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Some / any / no",
        contentResIds = intArrayOf(R.array.some_any_no),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PrepositionsInAtToOn(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Prepositions in/at, to, on",
        contentResIds = intArrayOf(R.array.prepositions_in_at_to_on),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class MovementAroundTheCity(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Movement around the city",
        contentResIds = intArrayOf(R.array.movement_around_the_city),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PrepositionsCityThereIs(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Prepositions City there is",
        contentResIds = intArrayOf(R.array.prepositions_city_there_is),
    ) {
        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentSimpleCity(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present simple CITY",
        contentResIds = intArrayOf(R.array.present_simple_city),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }


    @Serializable
    data class PresentSimpleFood(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present Simple Food",
        contentResIds = intArrayOf(R.array.present_simple_food),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentProcessWorkAndLeisure(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present Process WORK and LEISURE",
        contentResIds = intArrayOf(R.array.present_process_work_and_leisure),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentProcessRelationship(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present Process + Relationship",
        contentResIds = intArrayOf(R.array.present_process_relationship),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentSimpleCont(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present simple cont.",
        contentResIds = intArrayOf(R.array.present_simple_cont),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentSimpleVsPresentProcess(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present Simple vs Present Process",
        contentResIds = intArrayOf(R.array.present_simple_vs_present_process),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentProcessResult(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present Process + Result",
        contentResIds = intArrayOf(R.array.present_process_result),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ProcessProcessResult(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Process / Process + result",
        contentResIds = intArrayOf(R.array.process_process_result),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class WasWere(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Was / Were ",
        contentResIds = intArrayOf(R.array.was_were),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ThereWasThereWere(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "There was / there were",
        contentResIds = intArrayOf(R.array.there_was_there_were),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PastTypical(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Past Typical",
        contentResIds = intArrayOf(R.array.past_typical),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PastProcess(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Past process",
        contentResIds = intArrayOf(R.array.past_process),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentResult(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present Result",
        contentResIds = intArrayOf(R.array.present_result),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ArticlesGeography(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Articles geography",
        contentResIds = intArrayOf(R.array.articles_geography),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class CountriesAndCities(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Countries and cities",
        contentResIds = intArrayOf(R.array.countries_and_cities),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ToBeUsedToGetUsedTo(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "To be used to / get used to",
        contentResIds = intArrayOf(R.array.to_be_used_to_get_used_to),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class UsedTo(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Used to",
        contentResIds = intArrayOf(R.array.used_to),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class AsThingsStandNowAtFirst(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "As things stand now / At first",
        contentResIds = intArrayOf(R.array.as_things_stand_now_at_first),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class AtLeastInMyOpinion(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "At least / In my opinion",
        contentResIds = intArrayOf(R.array.at_least_in_my_opinion),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class InOtherWordsToSayTheTruth(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "In other words / To say the truth",
        contentResIds = intArrayOf(R.array.in_other_words_To_say_the_truth),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class OnTheContraryAsMatterOfFact(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "On the contrary / As a matter of fact",
        contentResIds = intArrayOf(R.array.on_the_contrary_as_a_matter_of_fact),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ThusIfIAmNotMistaken(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Thus / If I’m not mistaken",
        contentResIds = intArrayOf(R.array.thus_if_i_am_not_mistaken),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class VerbsWithPrepositions(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Verbs with prepositions",
        contentResIds = intArrayOf(
            R.array.verbs_with_prepositions_1,
            R.array.verbs_with_prepositions_2
        ),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class FutureSimpleForBeginner(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Future Simple for beginner",
        contentResIds = intArrayOf(R.array.future_simple_for_beginner),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class FutureSimpleWillV(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Future simple will+V",
        contentResIds = intArrayOf(R.array.future_simple_will_v),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class Plan100Percents(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Plan 100%",
        contentResIds = intArrayOf(R.array.plan_100),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class Plan50Percents(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Plan 50%",
        contentResIds = intArrayOf(R.array.plan_50),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class FutureProcess(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Future Process",
        contentResIds = intArrayOf(R.array.future_process),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ZeroConditional(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Zero conditional",
        contentResIds = intArrayOf(R.array.zero_conditional),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class TheFirstConditional(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "The First Conditional",
        contentResIds = intArrayOf(R.array.the_first_conditional),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class SecondConditional(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Second conditional",
        contentResIds = intArrayOf(R.array.second_conditional),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ThirdConditional(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Third conditional",
        contentResIds = intArrayOf(R.array.third_conditional),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class UsingWish(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Using \"Wish\"",
        contentResIds = intArrayOf(R.array.using_wish),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ThereVerb(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "There + verb",
        contentResIds = intArrayOf(R.array.there_verb),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class AdjectivesMore(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Adjectives more adj. / __er",
        contentResIds = intArrayOf(R.array.adjectives_more),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class AdjectivesTheMost(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Adjectives the most adj. / the __est",
        contentResIds = intArrayOf(R.array.adjectives_the_most),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class GoodBadFarOld(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "good/bad/far/old",
        contentResIds = intArrayOf(R.array.good_bad_far_old),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class AsAs(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "as …. as",
        contentResIds = intArrayOf(R.array.as_as),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class NotSoAsNotAsAs(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "not so … as/ not as ... as",
        contentResIds = intArrayOf(R.array.not_so_as_not_as_as),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class TwiceTimesAsAs(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "twice / 3 times as….as",
        contentResIds = intArrayOf(R.array.twice_times_as_as),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class TheSameAs(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "The same…as",
        contentResIds = intArrayOf(R.array.the_same_as),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class TheThe(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "The...  the",
        contentResIds = intArrayOf(R.array.the_the),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class AdjectivesComparativeCity(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Adjectives - Comparative (City)",
        contentResIds = intArrayOf(R.array.adjectives_comparative_city),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ComparativeAndSuperlativeDegreesOfAdjectives(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Comparative and superlative degrees of adjectives",
        contentResIds = intArrayOf(R.array.comparative_and_superlative_degrees_of_adjectives),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ModalVerbCan(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Modal verb CAN",
        contentResIds = intArrayOf(R.array.modal_verb_can),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class Could(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Could",
        contentResIds = intArrayOf(R.array.could),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class HaveTo(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Have to",
        contentResIds = intArrayOf(R.array.have_to),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class CanMayMustHaveTo(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Can, May, Must, Have to",
        contentResIds = intArrayOf(R.array.can_may_must_have_to),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class HadToWillHaveTo(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Had to & Will have to",
        contentResIds = intArrayOf(R.array.had_to_will_have_to),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class Gotta(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Gotta",
        contentResIds = intArrayOf(R.array.gotta),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class GonnaGoingTo(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Gonna = going to",
        contentResIds = intArrayOf(R.array.gonna_going_to),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ModalProbabilities(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Modal probabilities",
        contentResIds = intArrayOf(R.array.modal_probabilities),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentSimplePassive(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present Simple Passive",
        contentResIds = intArrayOf(R.array.present_simple_passive),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PastSimplePassive(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Past Simple Passive",
        contentResIds = intArrayOf(R.array.past_simple_passive),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class PresentTypicalPassiveFood(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Present Typical Passive (Food)",
        contentResIds = intArrayOf(R.array.present_typical_passive_food),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class Passive(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Passive",
        contentResIds = intArrayOf(R.array.passive),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class WouldLikeTo(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Would like to",
        contentResIds = intArrayOf(R.array.would_like_to),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class Shall(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Shall",
        contentResIds = intArrayOf(R.array.shall),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class ArticlesFood(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Articles food",
        contentResIds = intArrayOf(R.array.articles_food),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class AtRestaurant(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "At a restaurant",
        contentResIds = intArrayOf(R.array.at_restaurant),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class Pronouns(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Pronouns",
        contentResIds = intArrayOf(R.array.pronouns),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class Gerund(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Gerund",
        contentResIds = intArrayOf(R.array.gerund),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class Infinitive(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Infinitive",
        contentResIds = intArrayOf(R.array.infinitive),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class SomethingAnythingNothing(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Something / anything / nothing",
        contentResIds = intArrayOf(R.array.something_anything_nothing),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class TheOtherAnother(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "The other / another",
        contentResIds = intArrayOf(R.array.the_other_another),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class Both(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Both",
        contentResIds = intArrayOf(R.array.both),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class NounOfPossessivePronoun(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "A noun of possessive pronoun",
        contentResIds = intArrayOf(R.array.noun_of_possessive_pronoun),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class JobPhrasesCollocations(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Job phrases and collocations",
        contentResIds = intArrayOf(R.array.job_phrases_and_collocations),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class DescribingJobsAdjectives(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Describing jobs (adjectives)",
        contentResIds = intArrayOf(R.array.describing_job_adjectives),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class AdvancedPresentSimpleAndContinuous(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Advanced present simple and continuous",
        contentResIds = intArrayOf(R.array.advanced_present_simple_and_сontinuous),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class TalkingAboutJobsVocabulary(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Talking about jobs B1+/B2 vocabulary",
        contentResIds = intArrayOf(R.array.talking_about_jobs_vocabulary),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }

    @Serializable
    data class WeekPatternGroupResource(
        override val markColor: MarkColor = MarkColor.Non,
    ) : PatternGroupResource(
        name = "Week patterns",
        contentResIds = intArrayOf(1234134123),
    ) {

        override fun toNew(markColor: MarkColor): PatternGroupResource = copy(markColor = markColor)
    }
}