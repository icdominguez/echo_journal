package com.icdominguez.echo_journal.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class MviViewModel<S, E> : ViewModel() {

    protected abstract var currentState: S

    private val _state by lazy { MutableStateFlow(currentState) }
    val state: StateFlow<S>
        get() = _state

    private fun updateState() {
        _state.tryEmit(currentState)
    }

    private fun updateState(state: S) {
        currentState = state
        updateState()
    }

    protected fun updateState(currentStateTransformation: S.() -> S) {
        updateState(currentState.currentStateTransformation())
    }

    open fun uiEvent(event: E) {}
}
