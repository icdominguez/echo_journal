package com.icdominguez.echo_journal.presentation.screens.yourechojournal

import com.icdominguez.echo_journal.presentation.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YourEchoJournalScreenViewModel @Inject constructor():
    MviViewModel<YourEchoJournalScreenViewModel.State, YourEchoJournalScreenViewModel.Event>() {

    data class State(
        val loading: Boolean = false
    )

    override var currentState: State = State()

    sealed class Event {
        data object OnScreenLoaded: Event()
    }

    override fun uiEvent(event: Event) {
        when(event) {
            is Event.OnScreenLoaded -> {}
        }
    }
}