package com.example.englishpatterns.data.storageMigrations

import androidx.datastore.core.DataMigration
import com.example.englishpatterns.data.RowPatternGroupHolders
import com.example.englishpatterns.domain.RawPatternGroup
import com.example.englishpatterns.domain.RawPatternGroupHolder

object AddMissingBothPatternGroupMigration : DataMigration<RowPatternGroupHolders> {

    override suspend fun cleanUp() {}

    override suspend fun shouldMigrate(currentData: RowPatternGroupHolders): Boolean {
        return currentData.content.none { it.rawPatternGroup is RawPatternGroup.Both }
    }

    override suspend fun migrate(currentData: RowPatternGroupHolders): RowPatternGroupHolders {
        val bothRowPatternGroupHolder = RawPatternGroupHolder(
            rawPatternGroup = RawPatternGroup.Both(),
            isChosen = false,
        )

        return currentData.copy(content = currentData.content + listOf(bothRowPatternGroupHolder))
    }
}

object AddMissingNounOfPossessivePronounPatternGroupMigration :
    DataMigration<RowPatternGroupHolders> {

    override suspend fun cleanUp() {}

    override suspend fun shouldMigrate(currentData: RowPatternGroupHolders): Boolean {
        return currentData.content.none {
            it.rawPatternGroup is RawPatternGroup.NounOfPossessivePronoun
        }
    }

    override suspend fun migrate(currentData: RowPatternGroupHolders): RowPatternGroupHolders {
        val bothRowPatternGroupHolder = RawPatternGroupHolder(
            rawPatternGroup = RawPatternGroup.NounOfPossessivePronoun(),
            isChosen = false,
        )

        return currentData.copy(content = currentData.content + listOf(bothRowPatternGroupHolder))
    }
}