package com.example.englishpatterns.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp

const val PatternListDestination = "pattern_list_screen"

@Composable
fun PatternListScreen(
    viewModel: MainViewModel,
    onItemClick: (position: Int, patternHolder: PatternHolder) -> Unit,
    onStartButtonClick: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value as? State.InitialState ?: return
    val patternHolders by state.patternHolderSource.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.95F)

//                .weight(1F, false)
//                .fillMaxHeight(0.9F)
        ) {
            itemsIndexed(items = patternHolders) { index, holder ->
                Text(
                    text = holder.pattern.name,
                    modifier = Modifier
                        .background(
                            when (holder.isChosen) {
                                true -> Color(0x9A97A785)
                                else -> Color(0x2597A785)
                            }
                        )
                        .clickable { onItemClick(index, holder) }
                )
            }
        }
        Button(
//            modifier = Modifier.align(Alignment.BottomCenter),
            modifier = Modifier.fillMaxWidth(),
            onClick = onStartButtonClick
        ) {
            Text(text = "start")
        }
    }
}