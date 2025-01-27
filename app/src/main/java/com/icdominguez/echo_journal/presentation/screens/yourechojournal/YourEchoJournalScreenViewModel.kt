package com.icdominguez.echo_journal.presentation.screens.yourechojournal

import androidx.lifecycle.viewModelScope
import com.icdominguez.echo_journal.domain.audio.AudioPlayer
import com.icdominguez.echo_journal.domain.audio.AudioRecorder
import com.icdominguez.echo_journal.domain.usecase.CreateFileUseCase
import com.icdominguez.echo_journal.domain.usecase.DeleteFileUseCase
import com.icdominguez.echo_journal.domain.usecase.GetAllEntriesUseCase
import com.icdominguez.echo_journal.domain.usecase.GetAllTopicsUseCase
import com.icdominguez.echo_journal.presentation.MviViewModel
import com.icdominguez.echo_journal.presentation.model.Entry
import com.icdominguez.echo_journal.presentation.model.Topic
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Mood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YourEchoJournalScreenViewModel @Inject constructor(
    private val audioRecorder: AudioRecorder,
    private val audioPlayer: AudioPlayer,
    private val createFileUseCase: CreateFileUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
    private val getAllEntriesUseCase: GetAllEntriesUseCase,
    private val getAllTopicsUseCase: GetAllTopicsUseCase,
): MviViewModel<YourEchoJournalScreenViewModel.State, YourEchoJournalScreenViewModel.Event>() {

    data class State(
        val showRecordModalBottomSheet: Boolean = false,
        val visiblePermissionDialogQueue: List<String> = emptyList(),
        val entryList: List<Entry> = emptyList(),
        val filteredEntryList: List<Entry> = emptyList(),
        val selectedMoodList: List<Mood> = emptyList(),
        val selectedTopicList: List<Topic> = emptyList(),
        val topicsList: List<Topic> = emptyList(),
        val filePath: String = "",
    )

    override var currentState: State = State()

    sealed class Event {
        // region: permissions
        data class OnPermissionResult(val permission: String): Event()
        data object OnPermissionDialogDismissed: Event()
        // region: audio recorder events
        data object OnRecordAudioPaused: Event()
        data object OnRecordAudioResumed: Event()
        data object OnRecordAudioConfirmed: Event()
        // region: audio player events
        data class OnAudioPlayerStarted(val entry: Entry): Event()
        data class OnAudioPlayerPaused(val entry: Entry): Event()
        data class OnSliderValueChanged(val entry: Entry): Event()
        data class OnAudioPlayerEnded(val entry: Entry): Event()
        // region: others
        data object OnCreateEntryFloatingActionButtonClicked: Event()
        data object OnRecordAudioModalSheetDismissed: Event()
        //region: moods filter
        data object OnMoodsChipCloseButtonClicked: Event()
        data class OnMoodItemClicked(val mood: Mood): Event()
        //region: topics filter
        data object OnTopicsChipCloseButtonClicked: Event()
        data class OnTopicItemClicked(val topic: Topic): Event()
    }

    override fun uiEvent(event: Event) {
        when(event) {
            is Event.OnPermissionResult -> onPermissionResult(permission = event.permission)
            is Event.OnPermissionDialogDismissed -> onPermissionDialogDismissed()
            is Event.OnRecordAudioPaused -> onRecordAudioPaused()
            is Event.OnRecordAudioResumed -> onRecordAudioResumed()
            is Event.OnRecordAudioConfirmed -> onRecordAudioConfirmed()
            is Event.OnAudioPlayerStarted -> onAudioPlayerStarted(entry = event.entry)
            is Event.OnAudioPlayerPaused -> onAudioPlayerPaused(entry = event.entry)
            is Event.OnSliderValueChanged -> onSliderValueChanged(entry = event.entry)
            is Event.OnAudioPlayerEnded -> onAudioPlayerEnded(entry = event.entry)
            is Event.OnCreateEntryFloatingActionButtonClicked -> onCreateEntryFloatingActionButtonClicked()
            is Event.OnRecordAudioModalSheetDismissed -> onRecordAudioModalSheetDismissed()
            is Event.OnMoodsChipCloseButtonClicked -> onMoodsChipResetButtonClicked()
            is Event.OnMoodItemClicked -> onMoodItemClicked(mood = event.mood)
            is Event.OnTopicsChipCloseButtonClicked -> onTopicsChipResetButtonClicked()
            is Event.OnTopicItemClicked -> onTopicItemClicked(topic = event.topic)
        }
    }

    init {
        getAllEntries()
        getAllTopics()
    }

    // region: events
    private fun onPermissionResult(permission: String) {
        updateState {
            copy(
                visiblePermissionDialogQueue = visiblePermissionDialogQueue
                    .toMutableList()
                    .apply {
                        add(permission)
                    },
                showRecordModalBottomSheet = false
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

    private fun onAudioPlayerStarted(entry: Entry) {
        val newList = state.value.filteredEntryList.toMutableList().map {
            if(it.isPlaying) {
                it.copy(
                    isPlaying = false,
                    audioProgress = audioPlayer.pause()
                )
            } else if(it.filePath == entry.filePath) {
                it.copy(isPlaying = true)
            } else {
                it
            }
        }

        if (entry.audioProgress > 0) {
            audioPlayer.playFrom(entry.filePath, entry.audioProgress)
        } else {
            audioPlayer.play(entry.filePath)
        }

        updateState { copy(filteredEntryList = newList) }
    }

    private fun onAudioPlayerPaused(entry: Entry) {
        val newList = state.value.filteredEntryList.toMutableList().map {
            if(it.filePath == entry.filePath) {
                it.copy(
                    isPlaying = false,
                    audioProgress = audioPlayer.pause()
                )
            } else {
                it.copy(isPlaying = false)
            }
        }

        updateState { copy(filteredEntryList = newList) }
    }

    private fun onAudioPlayerEnded(entry: Entry) {
        val newList = state.value.filteredEntryList.toMutableList().map {
            if(it.filePath == entry.filePath) {
                it.copy(isPlaying = false, audioProgress = 0)
            } else {
                it
            }
        }

        audioPlayer.stop()
        updateState { copy(filteredEntryList = newList) }
    }

    private fun onSliderValueChanged(entry: Entry) {
        val newList = state.value.filteredEntryList.toMutableList().map {
            if (it.filePath == entry.filePath) {
                it.copy(audioProgress = entry.audioProgress)
            } else {
                it
            }
        }
        if(entry.isPlaying) {
            audioPlayer.playFrom(entry.filePath, entry.audioProgress)
        }
        updateState { copy(filteredEntryList = newList) }
    }

    private fun onCreateEntryFloatingActionButtonClicked() {
        val filePath = createFileUseCase()
        val audioPlaying = state.value.filteredEntryList.firstOrNull { it.isPlaying }
        audioPlaying?.let { onAudioPlayerPaused(audioPlaying) }
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
            filterEntryListByTopic()
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
                filterEntryListByMood(selectedMoodList = newSelectedMoodList)
            } else {
                filterEntryListByMood(selectedMoodList = newSelectedMoodList,)
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
        val newFilteredEntryList = if (state.value.selectedMoodList.isEmpty()) {
            state.value.entryList
        } else {
            filterEntryListByMood()
        }

        updateState {
            copy(
                selectedTopicList = listOf(),
                filteredEntryList = newFilteredEntryList
            )
        }
    }

    private fun onTopicItemClicked(topic: Topic) {
        val newSelectedTopicList: List<Topic> = if (state.value.selectedTopicList.contains(topic)) {
            state.value.selectedTopicList - topic
        } else {
            state.value.selectedTopicList + topic
        }

        val newFilteredEntryList = if (newSelectedTopicList.isNotEmpty()) {
            if (state.value.selectedMoodList.isEmpty()) {
                filterEntryListByTopic(selectedTopicList = newSelectedTopicList)
            } else {
                filterEntryListByTopic(selectedTopicList = newSelectedTopicList)
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
    // end region

    // region: others
    private fun getAllEntries() {
        viewModelScope.launch {
            getAllEntriesUseCase().collect { entryList ->
                updateState { copy(entryList = entryList, filteredEntryList = entryList) }
                if(entryList.isNotEmpty()) {
                    audioPlayer.init(entryList[0].filePath)
                }
            }
        }
    }

    private fun getAllTopics() {
        viewModelScope.launch {
            getAllTopicsUseCase().collect { topicList ->
                updateState { copy(topicsList = topicList) }
            }
        }
    }

    private fun filterEntryListByMood(
        selectedMoodList: List<Mood> = state.value.selectedMoodList,
    ): List<Entry> {
        return state.value.entryList.filter {
            it.mood?.name in selectedMoodList.map { mood -> mood.name }
        }
    }

    private fun filterEntryListByTopic(
        selectedTopicList: List<Topic> = state.value.selectedTopicList,
    ): List<Entry> {
        return state.value.entryList.filter { entry ->
            entry.topics.any { topic ->
                topic in selectedTopicList.map { it.name }
            }
        }
    }
    // endregion
}