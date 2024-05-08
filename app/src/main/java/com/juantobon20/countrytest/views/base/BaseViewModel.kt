package com.juantobon20.countrytest.views.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<State, Event>(
   private val initialState: State
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<State> = MutableStateFlow(initialState)
    val stateFlow: StateFlow<State> = _stateFlow.asStateFlow()

    private val _events: MutableSharedFlow<Event> = MutableSharedFlow(replay = 0)
    val eventFlow: SharedFlow<Event> = _events.asSharedFlow()

    fun update(newState: State) {
        _stateFlow.update { newState }
    }

    fun emitEvent(event: Event) {
        _events.tryEmit(event)
    }

    fun currentState() : State = _stateFlow.value ?: initialState
}