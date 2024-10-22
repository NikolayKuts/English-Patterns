package com.example.englishpatterns.presentation.patternPractisingScreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.englishpatterns.ui.theme.EnglishPatternsTheme
import com.lib.lokdroid.core.logE

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
                    groupItems(
                        patternGroupHolders = state.patternGroupHolders,
                        sendAction = sendAction
                    )
                }
            }

            val position = (state.currentPattern?.position ?: -1) + 1
            val positionText = if (position == 0) "" else position.toString()
            val size = state.currentPattern?.groupSize ?: -1
            val sizeText = if (size == -1) "" else " / $size"
            val progress = "$positionText$sizeText"

            Column {
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
                        onClick = { sendAction(PatternPracticingAction.SelectAllPatternGroups) }
                    ) {
                        Text(text = "Select all")
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCBAAA8)),
                        onClick = { sendAction(PatternPracticingAction.SelectNextPatternGroup) }
                    ) {
                        Text(text = "Select next")
                    }
                }
            }
        }

        PatternContent(state = state)

        BottomContent(sendAction = sendAction)
    }
}

private fun LazyListScope.groupItems(
    patternGroupHolders: List<PatternGroupHolder>,
    sendAction: (PatternPracticingAction) -> Unit
) {
    logE("WEAK -> $patternGroupHolders")

    itemsIndexed(items = patternGroupHolders) { index, patternGroupHolder ->
        val itemBackground: Color = if (patternGroupHolder.isWeaklyMemorized) {
            Color(0xBFC96D65)
        } else {
            Color.Transparent
        }

        Box(
            modifier = Modifier
                .clickable {
                    sendAction(
                        PatternPracticingAction.ChangePatternGroupHolderChoosingState(position = index)
                    )
                }
                .defaultMinSize(minHeight = 30.dp, minWidth = 20.dp)
                .border(
                    width = 2.dp,
                    color = if (patternGroupHolder.isChosen)
                        Color(0xFF8DA96D) else
                        Color(0xFF535650),
                    shape = RoundedCornerShape(6.dp)
                )
                .clip(RoundedCornerShape(6.dp))
                .background(itemBackground)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = (index + 1).toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }
        Spacer(modifier = Modifier.width(6.dp))
    }
}

@Composable
private fun BoxScope.PatternContent(state: PatternPracticingState) {
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
        animationSpec = tween(durationMillis = 500, easing = LinearEasing), label = "rotation",
    )

    Column(
        modifier = Modifier
            .align(Alignment.Center)
            .graphicsLayer { rotationY = rotation },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = state.currentPattern?.pattern?.native ?: "select groups",
            color = Color(0xFFC5CC85),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = state.currentPattern?.pattern?.translation ?: "",
            color = Color(0xFF7ABCC5),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BoxScope.BottomContent(sendAction: (PatternPracticingAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { sendAction(PatternPracticingAction.ShufflePatternPairs) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDFB4B1)),
            ) {
                Text(text = "Shuffle")
            }

            Button(
                onClick = { sendAction(PatternPracticingAction.AddPatternAsWeaklyMemorized) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDB8B86)),
            ) {
                Text(text = "week")
            }
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


@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun PatternPracticingScreenPreview() {
    EnglishPatternsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PatternPracticingScreen(
                state = PatternPracticingState(
                    patternGroupHolders = listOf(
                        PatternGroupHolder(
                            patterns = listOf(Pattern(native = "native", translation = "translation")),
                            isChosen = true
                        ),
                        PatternGroupHolder(
                            patterns = listOf(Pattern(native = "native", translation = "translation")),
                            isChosen = false
                        ),
                        PatternGroupHolder(
                            patterns = listOf(Pattern(native = "native", translation = "translation")),
                            isChosen = false
                        ),
                        PatternGroupHolder(
                            patterns = listOf(Pattern(native = "native", translation = "translation")),
                            isChosen = true
                        ),

                        PatternGroupHolder(
                            listOf(Pattern(native = "native", translation = "translation"))
                        ),
                        PatternGroupHolder(
                            listOf(Pattern(native = "native", translation = "translation"))
                        ),
                        PatternGroupHolder(
                            listOf(Pattern(native = "native", translation = "translation"))
                        ),
                        PatternGroupHolder(
                            listOf(Pattern(native = "native", translation = "translation"))
                        ),
                        PatternGroupHolder(
                            listOf(Pattern(native = "native", translation = "translation"))
                        ),
                        PatternGroupHolder(
                            listOf(Pattern(native = "native", translation = "translation"))
                        ),
                        PatternGroupHolder(
                            listOf(Pattern(native = "native", translation = "translation"))
                        ),
                        PatternGroupHolder(
                            listOf(Pattern(native = "native", translation = "translation"))
                        ),
                    ),
                ),
                sendAction = {}
            )
        }
    }
}