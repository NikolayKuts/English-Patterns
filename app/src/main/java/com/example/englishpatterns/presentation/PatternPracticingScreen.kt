package com.example.englishpatterns.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
    val currentPatternGroupState by state.currentPattern.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Groups ->")
                Spacer(modifier = Modifier.width(16.dp))
                LazyRow {
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
                                text = (index + 1).toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.wrapContentHeight()
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }
            val position = (currentPatternGroupState?.position ?: -1) + 1
            val positionText = if (position == 0) "" else position.toString()
            val size = currentPatternGroupState?.groupSize ?: -1
            val sizeText = if (size == -1) "" else " / $size"
            val progress = "$positionText$sizeText"

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 28.dp),
                    text = progress
                )
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFB38481)),
                    onClick = { viewModel.sendEvent(event = Event.SelectAllPairs) }
                ) {
                    Text(text = "Select all")
                }
            }
        }

        val position = currentPatternGroupState?.position ?: -1
        var rotationState by remember {
            mutableStateOf(true)
        }
        LaunchedEffect(key1 = position) {
            if (position == 0) {
                rotationState = !rotationState
            }
        }

        val rotation by animateFloatAsState(
            targetValue = if (rotationState) 0F else 360F,
            animationSpec = tween(durationMillis = 500, easing = LinearEasing),
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer { rotationY = rotation },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentPatternGroupState?.pair?.native ?: "select groups",
                color = Color(0xFFC5CC85),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = currentPatternGroupState?.pair?.translation ?: "",
                color = Color(0xFF7ABCC5),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {
            Button(
                onClick = { viewModel.sendEvent(event = Event.ShufflePatternPairs) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFB38481)),
            ) {
                Text(text = "Shuffle")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF91AA74)),
                onClick = { viewModel.sendEvent(event = Event.NextPatterPair) },
            ) {
                Text(text = "Next")
            }
        }
    }
}