package com.example.englishpatterns.presentation.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<State, Action, Event> : ViewModel() {

    abstract val state: StateFlow<State>
    abstract val eventState: SharedFlow<Event>

    abstract fun sendAction(action: Action)
}