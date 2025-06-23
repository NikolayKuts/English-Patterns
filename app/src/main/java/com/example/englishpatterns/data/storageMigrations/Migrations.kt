package com.example.englishpatterns.data.storageMigrations

import androidx.datastore.core.DataMigration
import com.example.englishpatterns.data.PatternGroupResContainers
import com.example.englishpatterns.domain.PatternGroupResource
import com.example.englishpatterns.domain.PatternGroupResContainer

object AddMissingBothPatternGroupMigration : DataMigration<PatternGroupResContainers> {

    override suspend fun cleanUp() {}

    override suspend fun shouldMigrate(currentData: PatternGroupResContainers): Boolean {
        return currentData.content.none { it.patternGroupResource is PatternGroupResource.Both }
    }

    override suspend fun migrate(currentData: PatternGroupResContainers): PatternGroupResContainers {
        val selectablePatternGroupResource = PatternGroupResContainer(
            patternGroupResource = PatternGroupResource.Both(),
            isChosen = false,
        )

        return currentData.copy(content = currentData.content + selectablePatternGroupResource)
    }
}

object AddMissingNounOfPossessivePronounPatternGroupMigration :
    DataMigration<PatternGroupResContainers> {

    override suspend fun cleanUp() {}

    override suspend fun shouldMigrate(currentData: PatternGroupResContainers): Boolean {
        return currentData.content.none {
            it.patternGroupResource is PatternGroupResource.NounOfPossessivePronoun
        }
    }

    override suspend fun migrate(currentData: PatternGroupResContainers): PatternGroupResContainers {
        val selectablePatternGroupResource = PatternGroupResContainer(
            patternGroupResource = PatternGroupResource.NounOfPossessivePronoun(),
            isChosen = false,
        )

        return currentData.copy(content = currentData.content + selectablePatternGroupResource)
    }
}

object AddMissingWeekPatternGroupMigration : DataMigration<PatternGroupResContainers> {

    override suspend fun cleanUp() {}

    override suspend fun shouldMigrate(currentData: PatternGroupResContainers): Boolean {
        return currentData.content.none {
            it.patternGroupResource is PatternGroupResource.WeekPatternGroupResource
        }
    }

    override suspend fun migrate(currentData: PatternGroupResContainers): PatternGroupResContainers {
        val selectablePatternGroupResource = PatternGroupResContainer(
            patternGroupResource = PatternGroupResource.WeekPatternGroupResource(),
            isChosen = false,
        )

        return currentData.copy(content = currentData.content + selectablePatternGroupResource)
    }
}

object AddMissingJobPhrasesAndCollocationsPatternGroupMigration :
    DataMigration<PatternGroupResContainers> by createPatternGroupMigration(
        insert = { PatternGroupResource.JobPhrasesCollocations() }
    )

object AddMissingDescribingJobsAdjectivesPatternGroupMigration :
    DataMigration<PatternGroupResContainers> by createPatternGroupMigration(
        insert = { PatternGroupResource.DescribingJobsAdjectives() }
    )

object AddMissingAdvancedPresentSimpleAndContinuousPatternGroupMigration :
    DataMigration<PatternGroupResContainers> by createPatternGroupMigration(
        insert = { PatternGroupResource.AdvancedPresentSimpleAndContinuous() }
    )

object AddMissingTalkingAboutJobsVocabularyPatternGroupMigration :
    DataMigration<PatternGroupResContainers> by createPatternGroupMigration(
        insert = { PatternGroupResource.TalkingAboutJobsVocabulary() }
    )

object AddMissingAdditionLinkersPatternGroupMigration :
    DataMigration<PatternGroupResContainers> by createPatternGroupMigration(
        insert = { PatternGroupResource.AdditionLinkers() }
    )

object AddMissingGerundsAndInfinitivesWithVerbsPatternGroupMigration :
    DataMigration<PatternGroupResContainers> by createPatternGroupMigration(
        insert = { PatternGroupResource.GerundsAndInfinitivesWithVerbs() }
    )

object PhraseVerbsPatternGroupMigration :
    DataMigration<PatternGroupResContainers> by createPatternGroupMigration(
    insert = { PatternGroupResource.PhrasalVerbs() }
)

object AndroidInterviewTermsPatternGroupMigration :
    DataMigration<PatternGroupResContainers> by createPatternGroupMigration(
        insert = { PatternGroupResource.AndroidInterviewTerms() }
    )

inline fun <reified T : PatternGroupResource> createPatternGroupMigration(
    crossinline insert: () -> T
): DataMigration<PatternGroupResContainers> {
    return object : DataMigration<PatternGroupResContainers> {

        override suspend fun cleanUp() {}

        override suspend fun shouldMigrate(currentData: PatternGroupResContainers): Boolean {
            return currentData.content.none { it.patternGroupResource is T }
        }

        override suspend fun migrate(currentData: PatternGroupResContainers): PatternGroupResContainers {
            val resContainerToInsert = PatternGroupResContainer(
                patternGroupResource = insert(),
                isChosen = false,
            )

            val indexToInsert = currentData.content.indexOfFirst {
                it.patternGroupResource is PatternGroupResource.WeekPatternGroupResource
            }

            val updatedContent = if (indexToInsert == -1) {
                currentData.content
            } else {
                currentData.content.toMutableList()
                    .apply { add(index = indexToInsert, resContainerToInsert) }
            }

            return currentData.copy(content = updatedContent)
        }
    }
}