package com.example.englishpatterns.presentation.common

import android.content.Context
import androidx.datastore.dataStore
import com.example.englishpatterns.data.PatternHoldersSerializer

val Context.patternStore by dataStore(
    fileName = "pattern_holders",
    serializer = PatternHoldersSerializer(),
)