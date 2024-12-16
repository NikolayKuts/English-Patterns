package com.example.englishpatterns.presentation.patternPractisingScreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.DrawableRes
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getSelectedText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.englishpatterns.R
import com.example.englishpatterns.data.common.LoadingState
import com.example.englishpatterns.domain.PatternGroupUnitState
import com.example.englishpatterns.presentation.common.shimmerEffect
import com.example.englishpatterns.ui.theme.EnglishPatternsTheme
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun PatternPracticingScreen(
    modifier: Modifier = Modifier,
    state: PatternPracticingState,
    sendAction: (PatternPracticingAction) -> Unit,
) {
    Box(
        modifier
            .fillMaxSize()
            .padding(top = 32.dp)
            .padding(horizontal = 16.dp)
    ) {
        val scope = rememberCoroutineScope()
        val patternContentContainerColor = remember { Animatable(Color.Transparent) }

        val animatableGroupPointerColor = {
            val duration = 700

            scope.launch {
                patternContentContainerColor.animateTo(
                    targetValue = Color.Transparent,
                    animationSpec = keyframes {
                        durationMillis = duration

                        Color(0x89D07B75) at (duration / 2)
                        Color.Transparent at duration
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Groups: ")

                Spacer(modifier = Modifier.width(16.dp))

                val listState = rememberLazyListState()
                val lastItemIndex = state.patternGroupHolders.indexOfLast { it.isChosen }
                val scrollPosition = if (lastItemIndex < 0) 0 else lastItemIndex

                LaunchedEffect(key1 = state) {
                    listState.animateScrollToItem(scrollPosition)
                }

                LazyRow(
                    state = listState
                ) {
                    groupItems(
                        patternGroupHolders = state.patternGroupHolders,
                        sendAction = sendAction
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val position = (state.currentPattern?.position ?: -1) + 1
            val positionText = if (position == 0) "" else position.toString()
            val size = state.currentPattern?.groupSize ?: -1
            val sizeText = if (size == -1) "" else " / $size"
            val progress = "$positionText$sizeText"

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = progress
                )

                val shuffleButtonTint = if (state.isPatternGroupHolderSateShuffled) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    Color(0xFFA5A5A5)
                }

                RoundedButton(
                    activated = state.isPatternGroupHolderSateShuffled,
                    activatedBackground = Color(0xFF4B7485),
                    onClink = { sendAction(PatternPracticingAction.ShufflePatternPairs) }
                ) {
                    Icon(
                        tint = shuffleButtonTint,
                        painter = painterResource(id = R.drawable.ic_shuffle),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                val isAllButtonActivated = state.patternGroupHolders.all { it.isChosen }
                val allButtonTextColor = if (isAllButtonActivated) {
                    Color.Unspecified
                } else {
                    Color(0xFFA5A5A5)
                }

                RoundedButton(
                    activated = isAllButtonActivated,
                    activatedBackground = Color(0xFFA1BB84),
                    onClink = {
                        sendAction(PatternPracticingAction.ChangeAllPatternGroupHoldersSelectionState)
                    }
                ) {
                    Text(text = "all", color = allButtonTextColor)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Row(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color(0xFF565656),
                            shape = RoundedCornerShape(50.dp)
                        )
                        .padding(6.dp),
                ) {
                    PatterGroupNavigationButton(iconId = R.drawable.ic_arrow_back) {
                        sendAction(PatternPracticingAction.SelectPreviousPatternGroup)
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    PatterGroupNavigationButton(iconId = R.drawable.ic_arrow_forward) {
                        sendAction(PatternPracticingAction.SelectNextPatternGroup)
                    }
                }
            }
        }

        AnimatedContent(
            modifier = Modifier.align(Alignment.Center),
            targetState = state.currentPattern,
            label = "AnimatedContent"
        ) { patternGroupUnitState ->
            PatternContent(
                patternGroupUnitState = patternGroupUnitState,
                isTranslationHidden = state.isTranslationHidden,
                selectedTextInfo = state.selectedTextInfo,
                pronunciationLoadingState = state.pronunciationLoadingState,
                sendAction = sendAction
            )
        }

        BottomContent(
            sendAction = sendAction,
            onWeakButtonClick = { animatableGroupPointerColor() }
        )
    }

    var shouldInterceptBack by remember { mutableStateOf(true) }
    var showExitDialog by remember { mutableStateOf(false) }

    if (showExitDialog) {
        ExitDialog(
            onDismiss = { showExitDialog = false },
            onConfirm = { shouldInterceptBack = false }
        )
    }

    BackHandler(enabled = shouldInterceptBack) {
        showExitDialog = showExitDialog.not()
    }
}

private fun LazyListScope.groupItems(
    patternGroupHolders: List<PatternGroupHolder>,
    sendAction: (PatternPracticingAction) -> Unit
) {
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
private fun PatterGroupNavigationButton(
    @DrawableRes iconId: Int,
    contentDescription: String? = null,
    onClink: () -> Unit
) {
    Icon(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(Color(0x57FF9800))
            .clickable { onClink() }
            .padding(4.dp),
        painter = painterResource(id = iconId),
        contentDescription = contentDescription
    )
}

@Composable
private fun BoxScope.PatternContent(
    patternGroupUnitState: PatternGroupUnitState?,
    isTranslationHidden: Boolean,
    selectedTextInfo: LoadingState<SelectedTextInfo>,
    pronunciationLoadingState: LoadingState<Unit>,
    sendAction: (PatternPracticingAction) -> Unit,
) {
    val position = patternGroupUnitState?.position ?: -1
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
            modifier = Modifier.clickable {
                sendAction(PatternPracticingAction.ChangeTranslationVisibilityState)
            },
            text = patternGroupUnitState?.pattern?.native ?: "",
            color = Color(0xFFC5CC85),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        PatternTranslationContent(
            patternGroupUnitState = patternGroupUnitState,
            selectedTextInfo = selectedTextInfo,
            isTranslationHidden = isTranslationHidden,
            pronunciationLoadingState = pronunciationLoadingState,
            sendAction = sendAction
        )
    }
}

@Composable
private fun PatternTranslationContent(
    patternGroupUnitState: PatternGroupUnitState?,
    selectedTextInfo: LoadingState<SelectedTextInfo>,
    isTranslationHidden: Boolean,
    sendAction: (PatternPracticingAction) -> Unit,
    pronunciationLoadingState: LoadingState<Unit>,
) {
    CompositionLocalProvider(
        LocalTextToolbar provides object : TextToolbar {
            override val status: TextToolbarStatus
                get() = TextToolbarStatus.Hidden

            override fun showMenu(
                rect: Rect,
                onCopyRequested: (() -> Unit)?,
                onPasteRequested: (() -> Unit)?,
                onCutRequested: (() -> Unit)?,
                onSelectAllRequested: (() -> Unit)?
            ) {
                // No implementation to prevent menu from showing
            }

            override fun hide() {}
        }
    ) {
        SelectionContainer {
            var selectedText by remember { mutableStateOf("") }
            var showDropdown by remember(patternGroupUnitState) { mutableStateOf(false) }
            var translationTextContainerSize by remember { mutableStateOf(IntSize.Zero) }
            val (translationContainerColor, translationTextColor) = if (isTranslationHidden) {
                Color(0xFF292929) to Color.Transparent
            } else {
                Color.Transparent to Color(0xFF7ABCC5)
            }

            SelectableText(
                text = patternGroupUnitState?.pattern?.translation ?: "",
                containerColor = translationContainerColor,
                textColor = translationTextColor,
                isTranslationHidden = isTranslationHidden,
                onGloballyPositioned = { translationTextContainerSize = it },
                onTextSelected = { newSelectedText ->
                    showDropdown = newSelectedText.isNotBlank() == true
                    selectedText = newSelectedText
                    sendAction(
                        PatternPracticingAction.TextSelected(
                            newSelectedText
                        )
                    )
                },
            )

            if (showDropdown && isTranslationHidden.not()) {
                SelectedTextMenu(
                    selectedText = selectedText,
                    selectedTextInfoLoadingState = selectedTextInfo,
                    translationTextContainerSize = translationTextContainerSize,
                    pronunciationLoadingState = pronunciationLoadingState,
                    sendAction = sendAction
                )
            }
        }
    }
}

@Composable
fun SelectableText(
    text: String,
    onTextSelected: (String) -> Unit,
    containerColor: Color,
    textColor: Color,
    isTranslationHidden: Boolean,
    onGloballyPositioned: (IntSize) -> Unit,
) {
    var textInput by remember(text) { mutableStateOf(TextFieldValue(text)) }
    val (handleColor, backgroundSelectionColor) = if (isTranslationHidden) {
        Color.Transparent to Color.Transparent
    } else {
        Color(0xFF484848) to Color(0x42B3B3B3)
    }

    OutlinedTextField(
        modifier = Modifier
            .widthIn(min = 3.dp)
            .onGloballyPositioned { onGloballyPositioned(it.size) },
        value = textInput,
        onValueChange = { newValue: TextFieldValue ->
            textInput = newValue
            val selectedTextValue = textInput.getSelectedText().text

            onTextSelected(selectedTextValue)
        },
        readOnly = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            errorContainerColor = containerColor,
            disabledContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            selectionColors = TextSelectionColors(
                handleColor = handleColor,
                backgroundColor = backgroundSelectionColor
            ),
        ),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            color = textColor
        ),
    )
}

@Composable
private fun BoxScope.BottomContent(
    sendAction: (PatternPracticingAction) -> Unit,
    onWeakButtonClick: () -> Unit = {},
) {
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
                onClick = {
                    sendAction(PatternPracticingAction.AddPatternAsWeaklyMemorized)
                    onWeakButtonClick()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDB8B86)),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_thumb_down),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "weak")
            }
        }

        Row {
            Button(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90C0C7)),
                onClick = { sendAction(PatternPracticingAction.PreviousPatter) },
            ) {
                Text(text = "Previous")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF91AA74)),
                onClick = { sendAction(PatternPracticingAction.NextPatter) },
            ) {
                Text(text = "Next")
            }
        }
    }
}

@Composable
private fun ExitDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    EnglishPatternsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val onBackPressedDispatcher =
                LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
            val coroutineScope = rememberCoroutineScope()

            AlertDialog(
                onDismissRequest = onDismiss,
                confirmButton = {
                    AlertDialogButton(
                        iconId = R.drawable.ic_exit,
                        clipShape = RoundedCornerShape(16.dp),
                        tint = Color(0xFFE87A7A),
                        onClink = {
                            coroutineScope.launch {
                                onConfirm()
                                awaitFrame()
                                onBackPressedDispatcher?.onBackPressed()
                            }
                        },
                    )
                },
                title = {
                    Text(text = "Are you sure you want to exit?")
                },
                dismissButton = {
                    AlertDialogButton(
                        iconId = R.drawable.ic_cancel,
                        tint = Color(0xFF858282),
                        onClink = { onDismiss() },
                    )
                },
            )
        }
    }
}

@Composable
private fun AlertDialogButton(
    @DrawableRes iconId: Int,
    onClink: () -> Unit,
    tint: Color,
    clipShape: RoundedCornerShape = RoundedCornerShape(24.dp),
    contentDescription: String? = null
) {
    Icon(
        modifier = Modifier
            .size(48.dp)
            .clip(clipShape)
            .clickable { onClink() },
        painter = painterResource(id = iconId),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
private fun SelectedTextMenu(
    selectedText: String,
    selectedTextInfoLoadingState: LoadingState<SelectedTextInfo>,
    translationTextContainerSize: IntSize,
    sendAction: (PatternPracticingAction) -> Unit,
    pronunciationLoadingState: LoadingState<Unit>,
) {
    Popup(
        offset = IntOffset(x = 0, y = translationTextContainerSize.height + 20)
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF292929))
                .padding(10.dp)
                .height(IntrinsicSize.Max)
        ) {
            Column(
                modifier = Modifier
                    .widthIn(min = 50.dp)
                    .width(IntrinsicSize.Max)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    val transcriptionText =
                        if (selectedTextInfoLoadingState is LoadingState.Success) {
                            selectedTextInfoLoadingState.data.transcription
                        } else {
                            ""
                        }

                    val (iconColor: Color, clickable: Boolean) = when (pronunciationLoadingState) {
                        is LoadingState.Success -> Color(0xFF8DAC6A) to true
                        else -> LocalContentColor.current.copy(alpha = 0.3f) to false
                    }

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerEffect(enabled = selectedTextInfoLoadingState is LoadingState.Loading),
                        text = transcriptionText,
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Icon(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .shimmerEffect(enabled = pronunciationLoadingState is LoadingState.Loading)
                            .clickable(enabled = clickable) {
                                sendAction(
                                    PatternPracticingAction.TextPronunciationRequired(
                                        selectedText
                                    )
                                )
                            },
                        painter = painterResource(id = R.drawable.ic_volume_up),
                        tint = iconColor,
                        contentDescription = null
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            sendAction(
                                PatternPracticingAction.SelectedTextSearchRequired(text = selectedText)
                            )
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(text = "WordHunt")

                    Spacer(modifier = Modifier.width(12.dp))

                    Icon(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_word_hunt),
                        contentDescription = null
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            sendAction(
                                PatternPracticingAction.RedirectionToKlafAppRequired(text = selectedText)
                            )
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(text = "Klaf")

                    Spacer(modifier = Modifier.width(12.dp))

                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_klaf_app_labale),
                        contentDescription = null,
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            sendAction(
                                PatternPracticingAction.RedirectionToChatGptAppRequired(text = selectedText)
                            )
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(text = "ChatGPT")

                    Spacer(modifier = Modifier.width(12.dp))

                    Icon(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_chatgpt),
                        contentDescription = null
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            sendAction(
                                PatternPracticingAction.RedirectionToYouGlishPageRequired(text = selectedText)
                            )
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(text = "YouGlish")

                    Spacer(modifier = Modifier.width(12.dp))

                    Icon(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .size(height = 24.dp, width = 40.dp),
                        painter = painterResource(id = R.drawable.ic_youglish),
                        contentDescription = null
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            sendAction(
                                PatternPracticingAction.RedirectionToGoogleImagesPageRequired(text = selectedText)
                            )
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(text = "Google")

                    Spacer(modifier = Modifier.width(12.dp))

                    Icon(
                        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                        painter = painterResource(id = R.drawable.ic_image_search),
                        contentDescription = null
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            sendAction(
                                PatternPracticingAction.RedirectionToWordTemplateSearchPageRequired(
                                    text = selectedText
                                )
                            )
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(text = "Template")

                    Spacer(modifier = Modifier.width(12.dp))

                    Icon(
                        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                }
            }

            VerticalDivider(
                modifier = Modifier
                    .padding(6.dp)
                    .padding(top = 22.dp)
            )

            if (selectedTextInfoLoadingState is LoadingState.Success) {
                Column(
                    modifier = Modifier
                        .padding(2.dp)
                        .width(IntrinsicSize.Max)
                ) {
                    val translation = selectedTextInfoLoadingState.data.translations
                    translation.forEachIndexed { index, translaton ->
                        Text(
                            text = translaton,
                            style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic)
                        )

                        if (index != translation.lastIndex) {
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RoundedButton(
    activated: Boolean,
    activatedBackground: Color,
    onClink: () -> Unit,
    content: @Composable() (RowScope.() -> Unit)
) {
    val (
        buttonColor,
        borderColor
    ) = if (activated) {
        activatedBackground to Color.Transparent
    } else {
        Color(0x19A2A2A2) to Color(0xFF686868)
    }

    Button(
        modifier = Modifier.defaultMinSize(minWidth = 41.dp, minHeight = 41.dp),
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(width = 1.dp, color = borderColor),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        onClick = onClink
    ) {
        content()
    }
}

/** ---------------------------------- Previews ---------------------------------- **/

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
                    isTranslationHidden = true,
                    patternGroupHolders = listOf(
                        PatternGroupHolder(
                            patterns = listOf(
                                Pattern(
                                    native = "native",
                                    translation = "translation"
                                )
                            ),
                            isChosen = true
                        ),
                        PatternGroupHolder(
                            patterns = listOf(
                                Pattern(
                                    native = "native",
                                    translation = "translation"
                                )
                            ),
                            isChosen = false
                        ),
                        PatternGroupHolder(
                            patterns = listOf(
                                Pattern(
                                    native = "native",
                                    translation = "translation"
                                )
                            ),
                            isChosen = false
                        ),
                        PatternGroupHolder(
                            patterns = listOf(
                                Pattern(
                                    native = "native",
                                    translation = "translation"
                                )
                            ),
                            isChosen = true
                        ),

//                        PatternGroupHolder(
//                            listOf(Pattern(native = "native", translation = "translation"))
//                        ),
//                        PatternGroupHolder(
//                            listOf(Pattern(native = "native", translation = "translation"))
//                        ),
//                        PatternGroupHolder(
//                            listOf(Pattern(native = "native", translation = "translation"))
//                        ),
//                        PatternGroupHolder(
//                            listOf(Pattern(native = "native", translation = "translation"))
//                        ),
//                        PatternGroupHolder(
//                            listOf(Pattern(native = "native", translation = "translation"))
//                        ),
//                        PatternGroupHolder(
//                            listOf(Pattern(native = "native", translation = "translation"))
//                        ),
//                        PatternGroupHolder(
//                            listOf(Pattern(native = "native", translation = "translation"))
//                        ),
//                        PatternGroupHolder(
//                            listOf(Pattern(native = "native", translation = "translation"))
//                        ),
                    ),
                    currentPattern = PatternGroupUnitState(
                        pattern = Pattern(
                            native = "some native text",
                            translation = "some translation text"
                        ),
                        position = 1,
                        groupSize = 12
                    )
                ),
                sendAction = {}
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
private fun ExitDialogPreview() {
    ExitDialog(
        onDismiss = {},
        onConfirm = {}
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun SelectedTextMenuPreview() {
    EnglishPatternsTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            SelectedTextMenu(
                selectedText = "selected text",
                selectedTextInfoLoadingState = LoadingState.Success(
                    data = SelectedTextInfo(
                        transcription = "translation",
                        translations = listOf("translation1", "translation2")
                    )
                ),
                translationTextContainerSize = IntSize.Zero,
                pronunciationLoadingState = LoadingState.Success(Unit),
                sendAction = {},
            )
        }
    }
}