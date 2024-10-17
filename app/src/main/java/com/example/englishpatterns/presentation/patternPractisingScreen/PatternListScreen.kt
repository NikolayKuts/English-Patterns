package com.example.englishpatterns.presentation.patternPractisingScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.englishpatterns.presentation.common.MainAction
import com.example.englishpatterns.presentation.common.MainState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PatternListScreen(
    state: MainState,
    sendAction: (action: MainAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.95F)
                .padding(start = 6.dp, end = 6.dp)
        ) {
            stickyHeader {
                val count = state.rowPatternGroupHolders.content.count { patternHolder -> patternHolder.isChosen }
                Text(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Color(0xFFA38B67))
                        .padding(top = 4.dp, bottom = 4.dp)
                        .fillMaxWidth()
                        .align(CenterHorizontally),
                    text = count.toString(),
                    textAlign = TextAlign.Center
                )
            }
            itemsIndexed(items = state.rowPatternGroupHolders.content) { index, holder ->
                Text(
                    text = holder.rawPatternGroup.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            sendAction(MainAction.ChangePatterHolderChoosingState(index, holder))
                        }
                        .clip(shape = RoundedCornerShape(4.dp))
                        .background(
                            when (holder.isChosen) {
                                true -> Color(0x9A97A785)
                                else -> Color(0x2597A785)
                            }
                        )
                        .padding(4.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                sendAction(MainAction.NavigateToPatternPracticing)
            }
        ) {
            Text(text = "start")
        }
    }
}