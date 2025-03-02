package com.example.englishpatterns.data

import android.content.Context
import androidx.datastore.dataStore
import com.example.englishpatterns.data.storageMigrations.AddMissingBothPatternGroupMigration
import com.example.englishpatterns.data.storageMigrations.AddMissingNounOfPossessivePronounPatternGroupMigration
import com.example.englishpatterns.data.storageMigrations.AddMissingWeekPatternGroupMigration

val Context.patternStore by dataStore(
    fileName = "pattern_group_res_containers",
    serializer = PatternGroupResContainersSerializer(),
    produceMigrations = {
        listOf(
            AddMissingBothPatternGroupMigration,
            AddMissingNounOfPossessivePronounPatternGroupMigration,
            AddMissingWeekPatternGroupMigration,
        )
    }
)

val Context.weekPatternStorage by dataStore(
    fileName = "week_pattern_group_res_containers",
    serializer = WeekPatternGroupResContainersSerializer(),
)