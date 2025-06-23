package com.example.englishpatterns.data

import android.content.Context
import androidx.datastore.dataStore
import com.example.englishpatterns.data.storageMigrations.AddMissingAdditionLinkersPatternGroupMigration
import com.example.englishpatterns.data.storageMigrations.AddMissingAdvancedPresentSimpleAndContinuousPatternGroupMigration
import com.example.englishpatterns.data.storageMigrations.AddMissingBothPatternGroupMigration
import com.example.englishpatterns.data.storageMigrations.AddMissingDescribingJobsAdjectivesPatternGroupMigration
import com.example.englishpatterns.data.storageMigrations.AddMissingGerundsAndInfinitivesWithVerbsPatternGroupMigration
import com.example.englishpatterns.data.storageMigrations.AddMissingJobPhrasesAndCollocationsPatternGroupMigration
import com.example.englishpatterns.data.storageMigrations.AddMissingNounOfPossessivePronounPatternGroupMigration
import com.example.englishpatterns.data.storageMigrations.AddMissingTalkingAboutJobsVocabularyPatternGroupMigration
import com.example.englishpatterns.data.storageMigrations.AddMissingWeekPatternGroupMigration
import com.example.englishpatterns.data.storageMigrations.AndroidInterviewTermsPatternGroupMigration
import com.example.englishpatterns.data.storageMigrations.PhraseVerbsPatternGroupMigration

val Context.patternStore by dataStore(
    fileName = "pattern_group_res_containers",
    serializer = PatternGroupResContainersSerializer(),
    produceMigrations = {
        listOf(
            AddMissingBothPatternGroupMigration,
            AddMissingNounOfPossessivePronounPatternGroupMigration,
            AddMissingWeekPatternGroupMigration,
            AddMissingJobPhrasesAndCollocationsPatternGroupMigration,
            AddMissingDescribingJobsAdjectivesPatternGroupMigration,
            AddMissingAdvancedPresentSimpleAndContinuousPatternGroupMigration,
            AddMissingTalkingAboutJobsVocabularyPatternGroupMigration,
            AddMissingAdditionLinkersPatternGroupMigration,
            AddMissingGerundsAndInfinitivesWithVerbsPatternGroupMigration,
            PhraseVerbsPatternGroupMigration,
            AndroidInterviewTermsPatternGroupMigration
        )
    }
)

val Context.weekPatternStorage by dataStore(
    fileName = "week_pattern_group_res_containers",
    serializer = WeekPatternGroupResContainersSerializer(),
)