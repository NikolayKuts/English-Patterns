package com.example.englishpatterns.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.englishpatterns.domain.RawPatternGroupHolder
import com.example.englishpatterns.presentation.UiMarkColor
import com.example.englishpatterns.ui.theme.EnglishPatternsTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: MainState,
    sendAction: (action: MainAction) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        val markColorChooserEnable = remember { mutableStateOf(value = false) }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 6.dp, end = 6.dp),
        ) {
            header(state = state)

            itemsIndexed(items = state.rowPatternGroupHolders.content) { index, holder ->
                Item(
                    holder = holder,
                    index = index,
                    markColorChooserEnable = markColorChooserEnable,
                    sendAction = sendAction
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Column {
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8FAE6B)),
                onClick = { sendAction(MainAction.NavigateToPatternPracticing) }
            ) {
                Text(text = "start")
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4DA4EA)),
                onClick = { sendAction(MainAction.NavigateToIrregularVerbsPracticeScreen) }
            ) {
                Text(text = "Verbs")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Item(
    holder: RawPatternGroupHolder,
    index: Int,
    markColorChooserEnable: MutableState<Boolean>,
    sendAction: (action: MainAction) -> Unit
) {
    val markColor = holder.rawPatternGroup.markColor

    val itemBackground = when (holder.isChosen) {
        true -> Color(0x9A6F7C60)
        else -> Color(markColor.toUiMarkColor().value)
    }

    var popupEnabled by remember { mutableStateOf(value = false) }

    LaunchedEffect(markColorChooserEnable.value) {
        if (!markColorChooserEnable.value) {
            popupEnabled = false
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(itemBackground)
            .combinedClickable(
                onClick = {
                    sendAction(
                        MainAction.ChangePatterHolderChoosingState(position = index, holder)
                    )
                    markColorChooserEnable.value = false
                },
                onLongClick = {
                    markColorChooserEnable.value = true
                    popupEnabled = true
                },
            )
            .padding(4.dp)

    ) {
        Text(
            text = (index + 1).toString(),
            modifier = Modifier.padding(end = 4.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = holder.rawPatternGroup.name,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(4.dp))
                .padding(4.dp)
        )

        if (markColorChooserEnable.value && popupEnabled) {
            MarkColorPopup(
                onItemClick = { colorValue ->
                    sendAction(
                        MainAction.SetMarkColor(
                            patternIndex = index,
                            markColor = colorValue.toMarkColor()
                        )
                    )
                    markColorChooserEnable.value = false
                }
            )
        }
        Color(0x59EA86FF)
    }
}

@Composable
private fun MarkColorPopup(
    onItemClick: (markColor: UiMarkColor) -> Unit,
) {
    Popup(offset = IntOffset(x = 0, y = 0)) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF1A1A1A))
                .padding(8.dp)
        ) {
            UiMarkColor.entries.forEachIndexed { index, markColor ->
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(markColor.value))
                        .clickable { onItemClick(markColor) }
                )

                if (index != UiMarkColor.entries.toTypedArray().lastIndex) {
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.header(state: MainState) {
    stickyHeader {
        val count = state.rowPatternGroupHolders.content.count { patternHolder ->
            patternHolder.isChosen
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Text(text = "Chosen patterns count: ")
            Spacer(modifier = Modifier.width(50.dp))
            Text(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(Color(0x8B51514E))
                    .padding(top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                text = count.toString(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun MainScreenPreview() {
    EnglishPatternsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainScreen(
                state = MainState(),
                sendAction = {}
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun MarkColorPopupPreview() {
    MarkColorPopup { }
}