package com.example.englishpatterns.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.englishpatterns.domain.Pattern
import kotlinx.coroutines.flow.StateFlow

const val PatternPracticingDestination = "pattern_practicing_screen"

@Composable
fun PatternPracticingScreen(patternsSource: StateFlow<List<Pattern>>) {
    val patterns by patternsSource.collectAsState()

    Box(
        Modifier.fillMaxSize()
    ) {
        val patternPairs = patterns.map {
            stringResource(id = it.resId).split("@")
                .map { rowPair ->
                    rowPair.substringBefore("==") to rowPair.substringAfter("==")
                }
        }
            .flatten()

        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = patternPairs.first().first,
            )
            Text(
                text = patternPairs.first().second,
            )
        }
    }
}