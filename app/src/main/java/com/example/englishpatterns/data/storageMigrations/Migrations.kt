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

        return currentData.copy(content = currentData.content + listOf(selectablePatternGroupResource))
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

        return currentData.copy(content = currentData.content + listOf(selectablePatternGroupResource))
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

        return currentData.copy(content = currentData.content + listOf(selectablePatternGroupResource))
    }
}