package com.example.englishpatterns.presentation.patternPractisingScreen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PatternPracticingScreen(
    state: PatternPracticingState,
    sendAction: (PatternPracticingAction) -> Unit,
) {
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
                    itemsIndexed(items = state.patternGroupHolders) { index, pairGroup ->
                        Card(
                            modifier = Modifier
                                .defaultMinSize(minHeight = 30.dp, minWidth = 20.dp)
                                .padding()
                                .clickable {
                                    sendAction(
                                        PatternPracticingAction.ChangePairGroupChoosingState(
                                            position = index
                                        )
                                    )
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = if (pairGroup.isChosen) {
                                    Color(0xFF9EBE79)
                                } else {
                                    Color(0x228BC34A)
                                },
                            ),
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

            val position = (state.currentPattern?.position ?: -1) + 1
            val positionText = if (position == 0) "" else position.toString()
            val size = state.currentPattern?.groupSize ?: -1
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
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB38481)),
                    onClick = { sendAction(PatternPracticingAction.SelectAllPairs) }
                ) {
                    Text(text = "Select all")
                }
            }
        }

        val position = state.currentPattern?.position ?: -1
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
                text = state.currentPattern?.pair?.native ?: "select groups",
                color = Color(0xFFC5CC85),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = state.currentPattern?.pair?.translation ?: "",
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
                onClick = { sendAction(PatternPracticingAction.ShufflePatternPairs) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB38481)),
            ) {
                Text(text = "Shuffle")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF91AA74)),
                onClick = { sendAction(PatternPracticingAction.NextPatterPair) },
            ) {
                Text(text = "Next")
            }
        }
    }
}