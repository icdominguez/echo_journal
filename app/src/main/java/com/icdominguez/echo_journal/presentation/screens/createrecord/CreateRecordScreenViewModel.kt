package com.icdominguez.echo_journal.presentation.screens.createrecord

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.icdominguez.echo_journal.data.audio.AndroidAudioPlayer
import com.icdominguez.echo_journal.domain.usecase.CreateEntryUseCase
import com.icdominguez.echo_journal.domain.usecase.CreateTopicUseCase
import com.icdominguez.echo_journal.domain.usecase.DeleteFileUseCase
import com.icdominguez.echo_journal.domain.usecase.GetAllTopicsUseCase
import com.icdominguez.echo_journal.presentation.MviViewModel
import com.icdominguez.echo_journal.presentation.model.Entry
import com.icdominguez.echo_journal.presentation.model.Topic
import com.icdominguez.echo_journal.presentation.navigation.NavArg
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Mood
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Moods
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRecordScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val audioPlayer: AndroidAudioPlayer,
    private val deleteFileUseCase: DeleteFileUseCase,
    private val getAllTopicsUseCase: GetAllTopicsUseCase,
    private val createTopicUseCase: CreateTopicUseCase,
    private val createEntryUseCase: CreateEntryUseCase,
): MviViewModel<CreateRecordScreenViewModel.State, CreateRecordScreenViewModel.Event>() {

    private var fileRecordedPath: String? = null

    data class State(
        val newEntry: Entry = Entry(),
        val moodSelectorModalBottomSheetSelected: Mood? = null,
        val showMoodSelectorModalBottomSheet: Boolean = false,
        val moodList: List<Mood> = Moods.allMods,
        val topicText: String = "",
        val topicList: List<Topic> = emptyList(),
        val filteredTopicList: List<Topic> = emptyList(),
    ) {
        val isSaveButtonEnabled: Boolean = newEntry.title.isNotEmpty() && newEntry.mood != null
    }

    override var currentState: State = State()

    sealed class Event {
        // region: entry
        data class OnEntryTextChanged(val entryText: String): Event()
        // region: mood modal bottom sheet
        data object OnAddMoodButtonClicked: Event()
        data class OnMoodClicked(val mood: Mood): Event()
        data object OnMoodSelectorModalBottomSheetConfirmed: Event()
        data object OnMoodSelectorModalBottomSheetDismissed: Event()
        // region: player
        data class OnAudioPlayerStarted(val entry: Entry): Event()
        data class OnAudioPlayerPaused(val entry: Entry): Event()
        data class OnSliderValueChanged(val entry: Entry): Event()
        data class OnAudioPlayerEnded(val entry: Entry): Event()
        // region: topics
        data class OnTopicTextChanged(val topicText: String): Event()
        data class OnTopicClicked(val topic: String): Event()
        data class OnDeleteTopicButtonClicked(val topic: String): Event()
        data class OnAddTopicClicked(val topic: String): Event()
        // region: description
        data class OnDescriptionTextChanged(val descriptionText: String): Event()
        // region: buttons
        data object OnSaveButtonClicked: Event()
        // region: others
        data object OnBackClicked: Event()
    }

    init {
        fileRecordedPath = savedStateHandle.get<String>(NavArg.FileRecordedPath.key)
        fileRecordedPath?.let {
            val audioDuration = audioPlayer.init(it)
            updateState {
                copy(newEntry = state.value.newEntry.copy(filePath = it, audioDuration = audioDuration))
            }
        }
        viewModelScope.launch {
            getAllTopicsUseCase().collect {
                updateState { copy(topicList = it) }
            }
        }
    }

    override fun uiEvent(event: Event) {
        when(event) {
            is Event.OnEntryTextChanged -> onEntryTextChanged(entryText = event.entryText)
            is Event.OnAddMoodButtonClicked -> onAddMoodButtonClicked()
            is Event.OnMoodClicked -> onMoodClicked(mood = event.mood)
            is Event.OnMoodSelectorModalBottomSheetConfirmed -> onMoodSelectorModalBottomSheetConfirmed()
            is Event.OnMoodSelectorModalBottomSheetDismissed -> onMoodSelectorModalBottomSheetDismissed()
            is Event.OnAudioPlayerStarted -> onAudioPlayerStarted(entry = event.entry)
            is Event.OnAudioPlayerPaused -> onAudioPlayerPaused(entry = event.entry)
            is Event.OnSliderValueChanged -> onSliderValueChanged(entry = event.entry)
            is Event.OnAudioPlayerEnded -> onAudioPlayerEnded(entry = event.entry)
            is Event.OnTopicTextChanged -> onTopicTextChanged(topicText = event.topicText)
            is Event.OnTopicClicked -> onTopicClicked(event.topic)
            is Event.OnDeleteTopicButtonClicked -> onDeleteTopicButtonClicked(topic = event.topic)
            is Event.OnAddTopicClicked -> onAddTopicClicked(topic = event.topic)
            is Event.OnDescriptionTextChanged -> onDescriptionTextChanged(descriptionText = event.descriptionText)
            is Event.OnSaveButtonClicked -> onSaveButtonClicked()
            is Event.OnBackClicked -> onBackClicked()
        }
    }

    // region: events
    private fun onEntryTextChanged(entryText: String) {
        updateState { copy(newEntry = state.value.newEntry.copy(title = entryText)) }
    }

    private fun onAddMoodButtonClicked() {
        updateState {
            copy(
                showMoodSelectorModalBottomSheet = true,
                moodSelectorModalBottomSheetSelected = state.value.newEntry.mood
            )
        }
    }

    private fun onMoodClicked(mood: Mood) {
        updateState { copy(moodSelectorModalBottomSheetSelected = if(mood.name == moodSelectorModalBottomSheetSelected?.name) null else mood) }
    }

    private fun onMoodSelectorModalBottomSheetConfirmed() {
        audioPlayer.reset()
        state.value.moodSelectorModalBottomSheetSelected?.let {
            updateState {
                copy(
                    showMoodSelectorModalBottomSheet = false,
                    newEntry = state.value.newEntry.copy(mood = it)
                )
            }
        }
    }

    private fun onMoodSelectorModalBottomSheetDismissed() {
        updateState {
            copy(
                showMoodSelectorModalBottomSheet = false,
                moodSelectorModalBottomSheetSelected = null,
            )
        }
    }

    private fun onAudioPlayerStarted(entry: Entry) {
        if(state.value.newEntry.audioProgress > 0) {
            audioPlayer.playFrom(entry.filePath, entry.audioProgress)
        } else {
            audioPlayer.play(entry.filePath)
        }
        updateState { copy(newEntry = state.value.newEntry.copy(isPlaying = true)) }
    }

    private fun onAudioPlayerPaused(entry: Entry) {
        updateState {
            copy(
                newEntry = state.value.newEntry.copy(
                    isPlaying = false,
                    audioProgress = audioPlayer.pause()
                )
            )
        }
    }

    private fun onSliderValueChanged(entry: Entry) {
        if(state.value.newEntry.isPlaying) {
            audioPlayer.playFrom(entry.filePath, entry.audioProgress)
        }
        updateState { copy(newEntry = state.value.newEntry.copy(audioProgress = entry.audioProgress)) }
    }

    private fun onAudioPlayerEnded(entry: Entry) {
        audioPlayer.stop()
        updateState { copy(newEntry = state.value.newEntry.copy(isPlaying = false, audioProgress = 0)) }
    }

    private fun onTopicTextChanged(topicText: String) {
        val filteredList = state.value.topicList.filter { it.name.lowercase().contains(topicText.lowercase()) }
        updateState {
            copy(
                topicText = topicText,
                filteredTopicList = filteredList
            )
        }
    }

    private fun onTopicClicked(topic: String) {
        val newList = state.value.newEntry.topics.toMutableList().apply { add(topic) }
        updateState {
            copy(
                newEntry = state.value.newEntry.copy(topics = newList),
                topicText = ""
            )
        }
    }

    private fun onDeleteTopicButtonClicked(topic: String) {
        val newList = state.value.newEntry.topics.toMutableList().apply { remove(topic) }
        updateState { copy(newEntry = state.value.newEntry.copy(topics = newList)) }
    }

    private fun onAddTopicClicked(topic: String) {
        viewModelScope.launch {
            createTopicUseCase(topic)
            updateState {
                copy(
                    topicText = "",
                    newEntry = state.value.newEntry.copy(topics = state.value.newEntry.topics.toMutableList().apply { add(topic) })
                )
            }
        }
    }

    private fun onDescriptionTextChanged(descriptionText: String) {
        updateState { copy(newEntry = state.value.newEntry.copy(description = descriptionText)) }
    }

    private fun onSaveButtonClicked() {
        audioPlayer.reset()
        viewModelScope.launch {
            createEntryUseCase(entry = state.value.newEntry)
        }
    }

    private fun onBackClicked() {
        fileRecordedPath?.let {
            audioPlayer.reset()
            deleteFileUseCase(it)
        }
    }
    // endregion
}