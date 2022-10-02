package com.example.englishpatterns.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.StateFlow

const val PatternListDestination = "pattern_list_screen"

@Composable
fun PatternListScreen(
    patternHolderSource: StateFlow<List<PatternHolder>>,
    onItemClick: (position: Int, patternHolder: PatternHolder) -> Unit,
    onStartButtonClick: () -> Unit,
) {
    val patternHolders by patternHolderSource.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.align(alignment = Alignment.Center)
        ) {

            itemsIndexed(items = patternHolders) { index, holder ->
                Text(
                    text = holder.pattern.name,
                    modifier = Modifier
                        .background(
                            when (holder.isChosen) {
                                true -> Color(0xFF97A785)
                                else -> Color(0x2597A785)
                            }
                        )
                        .clickable { onItemClick(index, holder) }
                )
            }
        }
        Button(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = onStartButtonClick
        ) {
            Text(text = "start")
        }
    }
}