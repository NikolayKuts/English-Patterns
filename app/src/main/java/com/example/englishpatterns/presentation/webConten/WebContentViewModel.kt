package com.example.englishpatterns.presentation.webConten

import com.example.englishpatterns.presentation.common.MviViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class WebContentViewModel : MviViewModel<WebContentState, WebContentAction, Unit>()  {

    override val state = MutableStateFlow(WebContentState(url = ""))

    override val eventState = MutableSharedFlow<Unit>()

    override fun sendAction(action: WebContentAction) {
        when (action) {

            else -> {}
        }
    }
}