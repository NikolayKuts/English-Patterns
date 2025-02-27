package com.example.englishpatterns.data

import android.content.Context
import androidx.datastore.dataStore
import com.example.englishpatterns.data.storageMigrations.AddMissingBothPatternGroupMigration
import com.example.englishpatterns.data.storageMigrations.AddMissingNounOfPossessivePronounPatternGroupMigration

val Context.patternStore by dataStore(
    fileName = "pattern_holders",
    serializer = PatternHoldersSerializer(),
    produceMigrations = {
        listOf(
            AddMissingBothPatternGroupMigration,
            AddMissingNounOfPossessivePronounPatternGroupMigration
        )
    }
)