package com.example.englishpatterns.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PatternListScreen(patternHolders: StateFlow<List<PatternHolder>>) {
    val holders by patternHolders.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.align(alignment = Alignment.Center)
        ) {

            items(items = holders) { holder ->
                Text(text = holder.pattern.name)
            }
        }
    }

}