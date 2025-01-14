package com.icdominguez.echo_journal.presentation.screens.yourechojournal

import com.icdominguez.echo_journal.data.model.TopicEntity
import com.icdominguez.echo_journal.domain.audio.AudioRecorder
import com.icdominguez.echo_journal.domain.usecase.CreateFileUseCase
import com.icdominguez.echo_journal.domain.usecase.DeleteFileUseCase
import com.icdominguez.echo_journal.presentation.MviViewModel
import com.icdominguez.echo_journal.presentation.model.Entry
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
        val entryList: List<Entry> = FakeData.timelineEntries,
        val filteredEntryList: List<Entry> = FakeData.timelineEntries,
        val selectedMoodList: List<Mood> = listOf(),
        val selectedTopicList: List<TopicEntity> = listOf(),
        val topicsList: List<TopicEntity> = FakeData.topicsEntries,
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
        //region: topics filter
        data object OnTopicsChipCloseButtonClicked: Event()
        data class OnTopicItemClicked(val topic: TopicEntity): Event()
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
            is Event.OnTopicsChipCloseButtonClicked -> onTopicsChipResetButtonClicked()
            is Event.OnTopicItemClicked -> onTopicItemClicked(topic = event.topic)
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
        val newFilteredEntryList = if (state.value.selectedTopicList.isEmpty()) {
            state.value.entryList
        } else {
            filterTopicsList()
        }

        updateState {
            copy(
                selectedMoodList = listOf(),
                filteredEntryList = newFilteredEntryList
            )
        }
    }

    private fun onMoodItemClicked(mood: Mood) {
        val newSelectedMoodList: List<Mood> = if (state.value.selectedMoodList.contains(mood)) {
            state.value.selectedMoodList - mood
        } else {
            state.value.selectedMoodList + mood
        }

        val newFilteredEntryList = if (newSelectedMoodList.isNotEmpty()) {
            if (state.value.selectedTopicList.isEmpty()) {
                filterMoodsList(
                    selectedMoodList = newSelectedMoodList
                )
            } else {
                filterMoodsList(
                    selectedMoodList = newSelectedMoodList,
                    timelineEntriesList = state.value.filteredEntryList
                )
            }
        } else {
            state.value.entryList
        }

        updateState {
            copy(
                selectedMoodList =  newSelectedMoodList,
                filteredEntryList = newFilteredEntryList
            )
        }
    }

    private fun onTopicsChipResetButtonClicked() {
        val newFilteredEntryList = if (state.value.selectedMoodList.isEmpty()){
            state.value.entryList
        } else {
            filterMoodsList()
        }

        updateState {
            copy(
                selectedTopicList = listOf(),
                filteredEntryList = newFilteredEntryList
            )
        }
    }

    private fun onTopicItemClicked(topic: TopicEntity) {
        val newSelectedTopicList: List<TopicEntity> = if (state.value.selectedTopicList.contains(topic)) {
            state.value.selectedTopicList - topic
        } else {
            state.value.selectedTopicList + topic
        }

        val newFilteredEntryList = if (newSelectedTopicList.isNotEmpty()) {
            if (state.value.selectedMoodList.isEmpty()) {
                filterTopicsList(
                    selectedTopicList = newSelectedTopicList
                )
            } else {
                filterTopicsList(
                    selectedTopicList = newSelectedTopicList,
                    timelineEntriesList = state.value.filteredEntryList
                )
            }
        } else {
            state.value.entryList
        }

        updateState {
            copy(
                selectedTopicList = newSelectedTopicList,
                filteredEntryList = newFilteredEntryList
            )
        }
    }

    private fun filterMoodsList(
        selectedMoodList: List<Mood> = state.value.selectedMoodList,
        timelineEntriesList: List<Entry> = state.value.entryList
    ): List<Entry> {
        return timelineEntriesList.filter {
            it.mood in selectedMoodList.map { mood -> mood.name }
        }
    }

    private fun filterTopicsList(
        selectedTopicList: List<TopicEntity> = state.value.selectedTopicList,
        timelineEntriesList: List<Entry> = state.value.entryList
    ): List<Entry> {
        return timelineEntriesList.filter { entry ->
            entry.topics.any { topic ->
                topic in selectedTopicList.map { it.name }
            }
        }
    }

}