package com.icdominguez.echo_journal.presentation.screens.createrecord

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.icdominguez.echo_journal.data.audio.AndroidAudioPlayer
import com.icdominguez.echo_journal.domain.usecase.CreateTopicUseCase
import com.icdominguez.echo_journal.domain.usecase.DeleteFileUseCase
import com.icdominguez.echo_journal.domain.usecase.GetAllTopicsUseCase
import com.icdominguez.echo_journal.presentation.MviViewModel
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
): MviViewModel<CreateRecordScreenViewModel.State, CreateRecordScreenViewModel.Event>() {

    private var fileRecordedPath: String? = null

    data class State(
        val entryText: String = "",
        val topicText: String = "",
        val moodList: List<Mood> = Moods.allMods,
        val selectedMood: Mood? = null,
        val moodSelectorModalBottomSheetSelected: Mood? = null,
        val showMoodSelectorModalBottomSheet: Boolean = false,
        val audioRecordedDuration: Int = 0,
        val topicList: List<Topic> = emptyList(),
        val filteredTopicList: List<Topic> = emptyList(),
        val selectedTopics: List<String> = emptyList(),
    )

    override var currentState: State = State()

    sealed class Event {
        data class OnEntryTextChanged(val entryText: String): Event()
        data object OnAddMoodButtonClicked: Event()
        data class OnMoodClicked(val mood: Mood): Event()
        data object OnMoodSelectorModalBottomSheetDismissed: Event()
        data object OnMoodSelectorModalBottomSheetConfirmed: Event()
        data object OnPlayClicked: Event()
        data object OnPauseClicked: Event()
        data class OnSliderValueChanged(val position: Int): Event()
        data object OnBackClicked: Event()
        data class OnTopicTextChanged(val topicText: String): Event()
        data class OnAddTopicClicked(val topic: String): Event()
        data class OnTopicClicked(val topic: String): Event()
        data class OnDeleteTopicButtonClicked(val topic: String): Event()
    }

    init {
        fileRecordedPath = savedStateHandle.get<String>(NavArg.FileRecordedPath.key)
        fileRecordedPath?.let {
            val audioDuration = audioPlayer.init(audioName = it)
            updateState { copy(audioRecordedDuration = audioDuration) }
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
            is Event.OnMoodSelectorModalBottomSheetDismissed -> onMoodSelectorModalBottomSheetDismissed()
            is Event.OnMoodSelectorModalBottomSheetConfirmed -> onMoodSelectorModalBottomSheetConfirmed()
            is Event.OnSliderValueChanged -> onSliderValueChanged(position = event.position)
            is Event.OnPlayClicked -> onPlayClicked()
            is Event.OnPauseClicked -> onPauseClicked()
            is Event.OnBackClicked -> onBackClicked()
            is Event.OnTopicTextChanged -> onTopicTextChanged(topicText = event.topicText)
            is Event.OnAddTopicClicked -> onAddTopicClicked(topic = event.topic)
            is Event.OnTopicClicked -> onTopicClicked(event.topic)
            is Event.OnDeleteTopicButtonClicked -> onDeleteTopicButtonClicked(topic = event.topic)
        }
    }

    private fun onTopicClicked(topic: String) {
        val newList = state.value.selectedTopics.toMutableList().apply { add(topic) }
        updateState {
            copy(
                selectedTopics = newList,
                topicText = ""
            )
        }
    }

    private fun onDeleteTopicButtonClicked(topic: String) {
        val newList = state.value.selectedTopics.toMutableList().apply { remove(topic) }
        updateState { copy(selectedTopics = newList) }
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

    private fun onAddTopicClicked(topic: String) {
        viewModelScope.launch {
            createTopicUseCase(topic)
            updateState {
                copy(
                    topicText = "",
                    selectedTopics = state.value.selectedTopics.toMutableList().apply { add(topic) }
                )
            }
        }
    }

    private fun onPlayClicked() {
        audioPlayer.play()
    }

    private fun onPauseClicked() {
        audioPlayer.pause()
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
            copy(moodSelectorModalBottomSheetSelected = if(mood.name == moodSelectorModalBottomSheetSelected?.name) null else mood)
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

    private fun onMoodSelectorModalBottomSheetConfirmed() {
        updateState {
            copy(
                showMoodSelectorModalBottomSheet = false,
                selectedMood = state.value.moodSelectorModalBottomSheetSelected
            )
        }
    }

    private fun onSliderValueChanged(position: Int) {
        audioPlayer.playFrom(millis = position)
    }

    private fun onBackClicked() {
        fileRecordedPath?.let {
            audioPlayer.reset()
            deleteFileUseCase(it)
        }
    }
}