package com.example.englishpatterns.presentation.webConten

import androidx.lifecycle.SavedStateHandle
import com.example.englishpatterns.presentation.common.MviViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class WebContentViewModel(
    savedStateHandle: SavedStateHandle,
) : MviViewModel<WebContentState, WebContentAction, Unit>(
    savedStateHandle = savedStateHandle
) {

    override val uiState = MutableStateFlow(WebContentState(url = ""))

    override val eventState = MutableSharedFlow<Unit>()

    override fun sendAction(action: WebContentAction) {
        when (action) {

            else -> {}
        }
    }
}