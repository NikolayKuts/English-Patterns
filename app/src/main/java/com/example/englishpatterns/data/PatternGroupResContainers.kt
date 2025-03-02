package com.example.englishpatterns.data

import com.example.englishpatterns.domain.PatternGroupResource
import com.example.englishpatterns.domain.PatternGroupResource.*
import com.example.englishpatterns.domain.PatternGroupResContainer
import kotlinx.serialization.Serializable

@Serializable
data class PatternGroupResContainers(
    val content: List<PatternGroupResContainer>
) {

    companion object {

        val Default: PatternGroupResContainers = PatternGroupResContainers(content = getDefaultContainers())

        private fun getDefaultContainers(): List<PatternGroupResContainer> = listOf(
            PatternGroupResContainer(
                patternGroupResource = PossessivePronouns(),
                isChosen = true
            ),
        ) + composedDefaultPatternGroupResContainers(
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
            PresentProcessResult(),
            ProcessProcessResult(),
            WasWere(),
            ThereWasThereWere(),
            PastTypical(),
            PastProcess(),
            PresentResult(),
            ArticlesGeography(),
            CountriesAndCities(),
            ToBeUsedToGetUsedTo(),
            UsedTo(),
            AsThingsStandNowAtFirst(),
            AtLeastInMyOpinion(),
            InOtherWordsToSayTheTruth(),
            OnTheContraryAsMatterOfFact(),
            ThusIfIAmNotMistaken(),
            VerbsWithPrepositions(),
            FutureSimpleForBeginner(),
            FutureSimpleWillV(),
            Plan100Percents(),
            Plan50Percents(),
            FutureProcess(),
            ZeroConditional(),
            TheFirstConditional(),
            SecondConditional(),
            ThirdConditional(),
            UsingWish(),
            ThereVerb(),
            AdjectivesMore(),
            AdjectivesTheMost(),
            GoodBadFarOld(),
            AsAs(),
            NotSoAsNotAsAs(),
            TwiceTimesAsAs(),
            TheSameAs(),
            TheThe(),
            AdjectivesComparativeCity(),
            ComparativeAndSuperlativeDegreesOfAdjectives(),
            ModalVerbCan(),
            Could(),
            HaveTo(),
            CanMayMustHaveTo(),
            HadToWillHaveTo(),
            Gotta(),
            GonnaGoingTo(),
            ModalProbabilities(),
            PresentSimplePassive(),
            PastSimplePassive(),
            PresentTypicalPassiveFood(),
            Passive(),
            WouldLikeTo(),
            Shall(),
            ArticlesFood(),
            AtRestaurant(),
            Pronouns(),
            Gerund(),
            Infinitive(),
            SomethingAnythingNothing(),
            TheOtherAnother(),
            Both(),
            NounOfPossessivePronoun(),
            WeekPatternGroupResource()
        )

        private fun composedDefaultPatternGroupResContainers(
            vararg patternGroupResources: PatternGroupResource
        ): List<PatternGroupResContainer> {
            return patternGroupResources.map {
                PatternGroupResContainer(
                    patternGroupResource = it,
                    isChosen = false
                )
            }
        }
    }
}
