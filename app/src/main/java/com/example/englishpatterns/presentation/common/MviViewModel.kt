package com.example.englishpatterns.presentation.common

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class MviViewModel<State, Action, Event>(
    protected val savedStateHandle: SavedStateHandle
) : ViewModel() {

    abstract val uiState: StateFlow<State>
    abstract val eventState: SharedFlow<Event>

    abstract fun sendAction(action: Action)

    protected fun MutableSharedFlow<Event>.launchEmit(
        context: CoroutineContext = Dispatchers.IO,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend () -> Event
    ) {
        viewModelScope.launch(context = context, start = start) {
            emit(block())
        }
    }

    protected fun <T> Flow<T>.launchCollect(
        context: CoroutineContext = Dispatchers.IO,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        collector: FlowCollector<T>,
    ): Job = viewModelScope.launch(context = context, start = start) {
        this@launchCollect.collect(collector)
    }
}