package com.example.englishpatterns.presentation.webConten

import com.example.englishpatterns.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class WebContentViewModel : BaseViewModel<WebContentState, WebContentAction, Unit>()  {

    override val state = MutableStateFlow(WebContentState(url = ""))

    override val eventState = MutableSharedFlow<Unit>()

    override fun sendAction(action: WebContentAction) {
        when (action) {

            else -> {}
        }
    }
}