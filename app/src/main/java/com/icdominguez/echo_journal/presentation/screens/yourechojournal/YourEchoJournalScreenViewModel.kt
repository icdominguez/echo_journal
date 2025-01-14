package com.icdominguez.echo_journal.presentation.screens.yourechojournal

import com.icdominguez.echo_journal.data.model.EntryEntity
import com.icdominguez.echo_journal.domain.audio.AudioRecorder
import com.icdominguez.echo_journal.domain.usecase.CreateFileUseCase
import com.icdominguez.echo_journal.domain.usecase.DeleteFileUseCase
import com.icdominguez.echo_journal.presentation.MviViewModel
import com.icdominguez.echo_journal.presentation.screens.FakeData
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Mood
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YourEchoJournalScreenViewModel @Inject constructor(
    private val audioRecorder: AudioRecorder,
    private val createFileUseCase: CreateFileUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
): MviViewModel<YourEchoJournalScreenViewModel.State, YourEchoJournalScreenViewModel.Event>() {

    data class State(
        val showRecordModalBottomSheet: Boolean = false,
        val visiblePermissionDialogQueue: List<String> = emptyList(),
        val timelineEntriesList: List<EntryEntity> = FakeData.timelineEntries,
        val filteredTimelineEntriesList: List<EntryEntity> = FakeData.timelineEntries,
        val selectedMoodList: List<Mood> = listOf(),
        val filePath: String = "",
    )

    override var currentState: State = State()

    sealed class Event {
        // region: permissions
        data class OnPermissionResult(val permission: String): Event()
        data object OnPermissionDialogDismissed: Event()
        //region: audio events
        data object OnRecordAudioPaused: Event()
        data object OnRecordAudioResumed: Event()
        data object OnRecordAudioConfirmed: Event()
        // region: others
        data object OnCreateEntryFloatingActionButtonClicked: Event()
        data object OnRecordAudioModalSheetDismissed: Event()
        //region: moods filter
        data object OnMoodsChipCloseButtonClicked: Event()
        data class OnMoodItemClicked(val mood: Mood): Event()
    }

    override fun uiEvent(event: Event) {
        when(event) {
            is Event.OnPermissionResult -> onPermissionResult(event.permission)
            is Event.OnPermissionDialogDismissed -> onPermissionDialogDismissed()
            is Event.OnRecordAudioPaused -> onRecordAudioPaused()
            is Event.OnRecordAudioResumed -> onRecordAudioResumed()
            is Event.OnRecordAudioConfirmed -> onRecordAudioConfirmed()
            is Event.OnCreateEntryFloatingActionButtonClicked -> onCreateEntryFloatingActionButtonClicked()
            is Event.OnRecordAudioModalSheetDismissed -> onRecordAudioModalSheetDismissed()
            is Event.OnMoodsChipCloseButtonClicked -> onMoodsChipResetButtonClicked()
            is Event.OnMoodItemClicked -> onMoodItemClicked(mood = event.mood)
        }
    }

    private fun onPermissionResult(permission: String) {
        updateState {
            copy(
                visiblePermissionDialogQueue = visiblePermissionDialogQueue
                    .toMutableList()
                    .apply {
                        add(permission)
                    }
            )
        }
    }

    private fun onPermissionDialogDismissed() {
        updateState {
            copy(
                visiblePermissionDialogQueue = visiblePermissionDialogQueue
                    .toMutableList()
                    .apply {
                        remove(visiblePermissionDialogQueue.first())
                    }
            )
        }
    }

    private fun onRecordAudioPaused() =
        audioRecorder.resume()

    private fun onRecordAudioResumed() =
        audioRecorder.pause()

    private fun onRecordAudioConfirmed() {
        updateState { copy(showRecordModalBottomSheet = false) }
        audioRecorder.stop()
    }

    private fun onCreateEntryFloatingActionButtonClicked() {
        val filePath = createFileUseCase()
        updateState { copy(showRecordModalBottomSheet = true, filePath = filePath) }
        audioRecorder.init(filePath)
        audioRecorder.start()
    }

    private fun onRecordAudioModalSheetDismissed() {
        updateState { copy(showRecordModalBottomSheet = false) }
        audioRecorder.stop()
        deleteFileUseCase(currentState.filePath)
    }

    private fun onMoodsChipResetButtonClicked() {
        updateState {
            copy(
                selectedMoodList = listOf(),
                filteredTimelineEntriesList = timelineEntriesList
            )
        }
    }

    private fun onMoodItemClicked(mood: Mood) {
        val newSelectedMoodList: List<Mood> = if (state.value.selectedMoodList.contains(mood)) {
            state.value.selectedMoodList - mood
        } else {
            state.value.selectedMoodList + mood
        }

        updateState {
            copy(
                selectedMoodList =  newSelectedMoodList,
                filteredTimelineEntriesList = if (newSelectedMoodList.isNotEmpty()) {
                    timelineEntriesList.filter {
                        it.mood in newSelectedMoodList.map { mood -> mood.name }
                    }
                } else {
                    timelineEntriesList
                }
            )
        }
    }

}