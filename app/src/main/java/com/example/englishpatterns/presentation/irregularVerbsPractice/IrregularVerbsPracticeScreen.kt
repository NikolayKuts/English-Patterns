package com.example.englishpatterns.presentation.irregularVerbsPractice

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.englishpatterns.R
import com.example.englishpatterns.domain.irregularVerbs.HidingMode
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbsStorage
import com.example.englishpatterns.domain.irregularVerbs.Verb
import com.example.englishpatterns.domain.irregularVerbs.VerbDetails
import com.example.englishpatterns.presentation.patternPractisingScreen.RoundedButton
import com.example.englishpatterns.ui.theme.EnglishPatternsTheme

@Composable
fun IrregularVerbsPracticeScreen(
    modifier: Modifier = Modifier,
    state: IrregularVerbsPracticeState,
    sendAction: (action: IrregularVerbsPracticeAction) -> Unit,
) {
    val verb = state.currentVerbDetails

    Column(modifier = modifier) {
        VerbsGroups(verbsGroupViewHolders = state.verbsGroupViewHolders, sendAction = sendAction)

        Spacer(modifier = Modifier.height(8.dp))

        ModePanel(
            isShufflingModeOn = state.isShufflingModeOn,
            verbHighlightModeOn = state.verbHighlightModeOn,
            sendAction = sendAction
        )

        Spacer(modifier = Modifier.height(16.dp))

        VerbDetailsContent(
            verb = verb,
            highlightModeOn = state.verbHighlightModeOn,
            hidingMode = state.hidingMode,
            sendAction = sendAction
        )

        TextToSpeechButton { sendAction(IrregularVerbsPracticeAction.TextToSpeech) }

        VerbDescriptionManagementPanel(
            onPreviousClick = { sendAction(IrregularVerbsPracticeAction.PreviousVerb) },
            onNextClick = { sendAction(IrregularVerbsPracticeAction.NextVerb) }
        )
    }
}

@Composable
private fun VerbsGroups(
    verbsGroupViewHolders: List<IrregularVerbsGroupViewHolder>,
    sendAction: (action: IrregularVerbsPracticeAction) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        verbsGroupViewHolders.forEach {
            when (it) {
                is IrregularVerbsGroupViewHolder.Common -> {
                    VerbGroupButton(
                        title = it.type.pattern,
                        checked = it.isSelected
                    ) {
                        sendAction(IrregularVerbsPracticeAction.ChangeVerbGroup(it.type))
                    }
                }

                is IrregularVerbsGroupViewHolder.FullyChanging -> {
                    VerbGroupDropdownMenuButton(
                        holder = it,
                        onClick = {
                            sendAction(IrregularVerbsPracticeAction.ChangeVerbGroup(it.type))
                        },
                        onItemClick = { subGroupViewHolder ->
                            sendAction(
                                IrregularVerbsPracticeAction.SetSubGroup(subGroupViewHolder)
                            )
                        }

                    )
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.ModePanel(
    isShufflingModeOn: Boolean,
    verbHighlightModeOn: Boolean,
    sendAction: (action: IrregularVerbsPracticeAction) -> Unit,
) {
    Row(modifier = Modifier.align(Alignment.End)) {
        Spacer(modifier = Modifier.width(16.dp))

        ShuffleButton(isShufflingModeOn = isShufflingModeOn) {
            sendAction(IrregularVerbsPracticeAction.ChangeShufflingMode)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Switch(
            checked = verbHighlightModeOn,
            onCheckedChange = { sendAction(IrregularVerbsPracticeAction.ChangeVerbHighlightMode) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF91AA74),
                uncheckedThumbColor = Color(0x7003A9F4),
                checkedTrackColor = Color(0x70858585)
            )
        )
    }
}

@Composable
private fun ShuffleButton(
    isShufflingModeOn: Boolean,
    onClick: () -> Unit,
) {
    val shuffleButtonTint = if (isShufflingModeOn) {
        MaterialTheme.colorScheme.onSurface
    } else {
        Color(0xFFA5A5A5)
    }
    RoundedButton(
        activated = isShufflingModeOn,
        activatedBackground = Color(0xFF4B7485),
        onClick = onClick
    ) {
        Icon(
            tint = shuffleButtonTint,
            painter = painterResource(id = R.drawable.ic_shuffle),
            contentDescription = null
        )
    }
}

@Composable
private fun ColumnScope.VerbDetailsContent(
    verb: VerbDetails?,
    highlightModeOn: Boolean,
    hidingMode: HidingMode,
    sendAction: (action: IrregularVerbsPracticeAction) -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = verb?.word ?: "")

        Spacer(modifier = Modifier.height(24.dp))

        val (v1Color, v2Color, v3Color) = getVerbColorsByGroupType(
            type = verb?.type,
            highlightModeOn = highlightModeOn
        )

        val v2Hidden = hidingMode == HidingMode.Second || hidingMode == HidingMode.SecondAndThird
        val v3Hidden = hidingMode == HidingMode.Third || hidingMode == HidingMode.SecondAndThird

        verb?.let {
            Row {
                VerbItem(verb = it.v1, verbColor = v1Color, hidden = false)
                Spacer(modifier = Modifier.width(12.dp))
                VerbItem(verb = it.v2, verbColor = v2Color, hidden = v2Hidden) {
                    sendAction(IrregularVerbsPracticeAction.ChangeHidingMode(HidingMode.Second))
                }
                Spacer(modifier = Modifier.width(12.dp))
                VerbItem(verb = it.v3, verbColor = v3Color, v3Hidden) {
                    sendAction(IrregularVerbsPracticeAction.ChangeHidingMode(HidingMode.Third))
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.TextToSpeechButton(onClick: () -> Unit) {
    Button(
        modifier = Modifier.align(Alignment.End),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE3CF93)),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_volume_up),
            contentDescription = null,
        )
    }
}

@Composable
private fun VerbDescriptionManagementPanel(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    Row {
        Button(
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90C0C7)),
            onClick = onPreviousClick,
        ) {
            Text(text = "Previous")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF91AA74)),
            onClick = onNextClick,
        ) {
            Text(text = "Next")
        }
    }
}

private fun getVerbColorsByGroupType(
    type: IrregularVerbGroupType?,
    highlightModeOn: Boolean
): Triple<Color, Color, Color> {
    val v1HighLightColor = Color(0xFFEADD6D)
    val v2HighLightColor = Color(0xFF94DEE8)
    val v3HighLightColor = Color(0xFFE8978F)

    return if (highlightModeOn) {
        when (type) {
            IrregularVerbGroupType.Unchanging -> {
                Triple(v1HighLightColor, v1HighLightColor, v1HighLightColor)
            }

            IrregularVerbGroupType.PartiallyChanging -> {
                Triple(v1HighLightColor, v2HighLightColor, v1HighLightColor)
            }

            IrregularVerbGroupType.FullyChanging -> {
                Triple(v1HighLightColor, v2HighLightColor, v3HighLightColor)
            }

            IrregularVerbGroupType.PartiallyConsistent -> {
                Triple(v1HighLightColor, v2HighLightColor, v2HighLightColor)
            }

            null -> Triple(Color.Unspecified, Color.Unspecified, Color.Unspecified)
        }
    } else {
        Triple(Color.Unspecified, Color.Unspecified, Color.Unspecified)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun VerbGroupButton(
    title: String,
    checked: Boolean,
    onLongClick: () -> Unit = {},
    onClick: () -> Unit,
) {
    val backgroundColor = if (checked) Color(0x8DA0D762) else Color(0xFF575757)

    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .background(backgroundColor)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
            .padding(8.dp),
        text = title,
        fontWeight = FontWeight.Bold

    )
}

@Composable
private fun VerbGroupDropdownMenuButton(
    holder: IrregularVerbsGroupViewHolder.FullyChanging,
    onClick: () -> Unit,
    onItemClick: (FullyChangingSubGroupViewHolder) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        VerbGroupButton(
            title = holder.type.pattern,
            checked = holder.isSelected,
            onClick = onClick,
            onLongClick = { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            holder.subGroups.forEach { subGroupViewHolder ->
                val backgroundColor = if (subGroupViewHolder.isSelected) {
                    Color(0x768BC34A)
                } else {
                    Color.Transparent
                }

                DropdownMenuItem(
                    modifier = Modifier.background(backgroundColor),
                    text = {
                        Text(text = subGroupViewHolder.subGroupName)
                    },
                    onClick = { onItemClick(subGroupViewHolder) }
                )
            }
        }
    }
}

@Composable
private fun VerbItem(
    verb: Verb,
    verbColor: Color,
    hidden: Boolean,
    onClick: (() -> Unit)? = null
) {
    val background = if (hidden) Color(0x62656564) else Color.Unspecified
    val finalVerbColor = if (hidden) Color.Transparent else verbColor
    val ipaColor = if (hidden) Color.Transparent else Color(0x49FDFDFD)

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(background)
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = verb.word, color = finalVerbColor)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = verb.ipa, color = ipaColor)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES, device = "spec:parent=pixel_5"
)

@Composable
private fun IrregularVerbsPracticeScreenPreview() {
    val verbsDetails = IrregularVerbsStorage.unchanging.details.take(6).first()
    val state = IrregularVerbsPracticeState(
        currentVerbDetails = verbsDetails,
        verbsGroupViewHolders = IrregularVerbGroupType.entries.map {
            IrregularVerbsGroupViewHolder.Common(type = it, isSelected = false)
        },
        hidingMode = HidingMode.Third
    )

    EnglishPatternsTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold { paddingValues ->
                IrregularVerbsPracticeScreen(
                    modifier = Modifier.padding(paddingValues),
                    state = state,
                    sendAction = {}
                )
            }
        }
    }
}