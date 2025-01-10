package com.icdominguez.echo_journal.presentation.screens.createrecord

import com.icdominguez.echo_journal.presentation.MviViewModel
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Mood
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Moods
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateRecordScreenViewModel @Inject constructor():
    MviViewModel<CreateRecordScreenViewModel.State, CreateRecordScreenViewModel.Event>() {

    data class State(
        val entryText: String = "",
        val moodList: List<Mood> = Moods.allMods,
        val selectedMood: Mood? = null,
        val moodSelectorModalBottomSheetSelected: Mood? = null,
        val showMoodSelectorModalBottomSheet: Boolean = false,
    )

    override var currentState: State = State()

    sealed class Event {
        data class OnEntryTextChanged(val entryText: String): Event()
        data object OnAddMoodButtonClicked: Event()
        data class OnMoodClicked(val mood: Mood): Event()
        data object OnMoodSelectorModalBottomSheetDismissed: Event()
        data object OnMoodSelectorModalBottomSheetConfirmed: Event()
    }

    override fun uiEvent(event: Event) {
        when(event) {
            is Event.OnEntryTextChanged -> onEntryTextChanged(event.entryText)
            is Event.OnAddMoodButtonClicked -> onAddMoodButtonClicked()
            is Event.OnMoodClicked -> onMoodClicked(event.mood)
            is Event.OnMoodSelectorModalBottomSheetDismissed -> onMoodSelectorModalBottomSheetDismissed()
            is Event.OnMoodSelectorModalBottomSheetConfirmed -> onMoodSelectorModalBottomSheetConfirmed()
        }
    }

    private fun onEntryTextChanged(entryText: String) {
        updateState {
            copy(entryText = entryText)
        }
    }

    private fun onAddMoodButtonClicked() {
        updateState {
            copy(
                showMoodSelectorModalBottomSheet = true,
                moodSelectorModalBottomSheetSelected = state.value.selectedMood
            )
        }
    }

    private fun onMoodClicked(mood: Mood) {
        updateState {
            copy(
                moodSelectorModalBottomSheetSelected = if(mood.name == moodSelectorModalBottomSheetSelected?.name) null else mood
            )
        }
    }

    private fun onMoodSelectorModalBottomSheetDismissed() {
        updateState {
            copy(
                showMoodSelectorModalBottomSheet = false,
                moodSelectorModalBottomSheetSelected = null
            )
        }
    }

    private fun onMoodSelectorModalBottomSheetConfirmed() {
        updateState {
            copy(
                showMoodSelectorModalBottomSheet = false,
                selectedMood = state.value.moodSelectorModalBottomSheetSelected
            )
        }
    }
}