package com.example.englishpatterns.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.englishpatterns.domain.PatternHolder

const val PatternListDestination = "pattern_list_screen"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PatternListScreen(
    viewModel: MainViewModel,
    onItemClick: (position: Int, patternHolder: PatternHolder) -> Unit,
    onStartButtonClick: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value as? State.InitialState ?: return
    val patternHolders = state.patternHolderSource.collectAsState().value ?: return

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
                val count = patternHolders.holders.count { patternHolder -> patternHolder.isChosen }
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
            itemsIndexed(items = patternHolders.holders) { index, holder ->
                Text(
                    text = holder.pattern.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(index, holder) }
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
            onClick = onStartButtonClick
        ) {
            Text(text = "start")
        }
    }
}