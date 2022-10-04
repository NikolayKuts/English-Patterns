package com.example.englishpatterns.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

const val PatternPracticingDestination = "pattern_practicing_screen"

@Composable
fun PatternPracticingScreen(
    viewModel: MainViewModel,
) {
    DisposableEffect(key1 = null) {
        onDispose { viewModel.sendEvent(event = Event.DisplayMainScreen) }
    }

    val state = viewModel.state.collectAsState().value as? State.PatternPracticingState ?: return
    val groups by state.patternPairGroups.collectAsState()
    val currentPattern by state.currentPattern.collectAsState()

    Box(
        Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        ) {
            Text(text = "Groups ->")
            LazyRow(
//                modifier = Modifier.weight(0.7F)
            ) {
                itemsIndexed(items = groups) { index, pairGroup ->
                    Card(
                        modifier = Modifier
                            .defaultMinSize(minHeight = 30.dp, minWidth = 20.dp)
                            .padding()
                            .clickable {
                                viewModel.sendEvent(
                                    event = Event.ChangePairGroupChoosingState(position = index)
                                )
                            },
                        backgroundColor = if (pairGroup.isChosen) Color(0xFF9EBE79)
                        else Color(0x228BC34A),
                        shape = RoundedCornerShape(size = 5.dp),
                        border = BorderStroke(width = 1.dp, color = Color(0xF0CFCFCF)),
                    ) {
                        Text(
                            text = index.toString(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.wrapContentHeight()
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
        }

        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = currentPattern?.native ?: "select groups",
            )
            Text(
                text = currentPattern?.translation ?: ""
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClick = { viewModel.sendEvent(event = Event.NextPatterPair) }) {
            Text(text = "Next")
        }
    }
}