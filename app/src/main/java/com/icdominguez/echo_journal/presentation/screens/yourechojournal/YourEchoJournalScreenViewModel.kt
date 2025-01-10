package com.icdominguez.echo_journal.presentation.screens.yourechojournal

import com.icdominguez.echo_journal.data.audio.AndroidAudioRecorder
import com.icdominguez.echo_journal.domain.repository.FileManagerRepository
import com.icdominguez.echo_journal.presentation.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YourEchoJournalScreenViewModel @Inject constructor(
    private val androidAudioRecorder: AndroidAudioRecorder,
    private val fileManagerRepository: FileManagerRepository,
): MviViewModel<YourEchoJournalScreenViewModel.State, YourEchoJournalScreenViewModel.Event>() {

    data class State(
        val showRecordModalBottomSheet: Boolean = false,
        val visiblePermissionDialogQueue: List<String> = emptyList(),
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
        androidAudioRecorder.resume()

    private fun onRecordAudioResumed() =
        androidAudioRecorder.pause()

    private fun onRecordAudioConfirmed() {
        updateState { copy(showRecordModalBottomSheet = false) }
        androidAudioRecorder.stop()
    }

    private fun onCreateEntryFloatingActionButtonClicked() {
        updateState { copy(showRecordModalBottomSheet = true) }
        val filePath = fileManagerRepository.createFile()
        androidAudioRecorder.start(filePath)
    }

    private fun onRecordAudioModalSheetDismissed() {
        updateState { copy(showRecordModalBottomSheet = false) }
        androidAudioRecorder.stop()
    }
}