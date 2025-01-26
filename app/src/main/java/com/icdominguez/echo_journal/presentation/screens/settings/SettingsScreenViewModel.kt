package com.icdominguez.echo_journal.presentation.screens.settings

import androidx.lifecycle.viewModelScope
import com.icdominguez.echo_journal.domain.usecase.CreateTopicUseCase
import com.icdominguez.echo_journal.domain.usecase.GetAllTopicsUseCase
import com.icdominguez.echo_journal.domain.usecase.GetMoodFromSharedPreferencesUseCase
import com.icdominguez.echo_journal.domain.usecase.SetMoodInSharedPreferencesUseCase
import com.icdominguez.echo_journal.domain.usecase.UpdateTopicUseCase
import com.icdominguez.echo_journal.presentation.MviViewModel
import com.icdominguez.echo_journal.presentation.model.Topic
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Mood
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Moods
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor (
    private val setMoodInSharedPreferencesUseCase: SetMoodInSharedPreferencesUseCase,
    private val getMoodFromSharedPreferencesUseCase: GetMoodFromSharedPreferencesUseCase,
    private val getAllTopicsUseCase: GetAllTopicsUseCase,
    private val createTopicUseCase: CreateTopicUseCase,
    private val updateTopicUseCase: UpdateTopicUseCase,
): MviViewModel<SettingsScreenViewModel.State, SettingsScreenViewModel.Event>() {

    data class State(
        val moodList: List<Mood> = Moods.allMods,
        val selectedMood: Mood? = null,
        val topicText: String = "",
        val showTopicTextField: Boolean = false,
        val topicList: List<Topic> = emptyList(),
        val filteredTopicList: List<Topic> = emptyList(),
    )

    override var currentState: State = State()

    sealed class Event {
        data class OnMoodClicked(val mood: Mood): Event()
        data class OnTopicTextChanged(val topicText: String): Event()
        data object OnAddTopicButtonClicked: Event()
        data class OnSaveTopicButtonClicked(val topic: String): Event()
        data class OnSetTopicAsDefaultClicked(val topic: Topic) : Event()
        data class OnDeleteTopicButtonClicked(val topicName: String) : Event()
    }

    override fun uiEvent(event:  Event) {
        when(event) {
            is Event.OnMoodClicked -> onMoodClicked(mood = event.mood)
            is Event.OnTopicTextChanged -> onTopicTextChanged(topicText = event.topicText)
            is Event.OnAddTopicButtonClicked -> onAddTopicButtonClicked()
            is Event.OnSaveTopicButtonClicked -> onSaveTopicClicked(topic = event.topic)
            is Event.OnSetTopicAsDefaultClicked -> onSetTopicAsDefaultClicked(topic = event.topic)
            is Event.OnDeleteTopicButtonClicked -> onDeleteTopicButtonClicked(topicName = event.topicName)
        }
    }

    init {
        val storedMood = getMoodFromSharedPreferencesUseCase()
        storedMood?.let {
            updateState { copy(selectedMood = Moods.allMods.find { mood -> mood.name == storedMood }) }
        }
        viewModelScope.launch {
            getAllTopicsUseCase().collect {
                updateState {
                    copy(
                        topicList = it,
                        filteredTopicList = it.filter { it.isDefault }
                    )
                }
            }
        }
    }

    private fun onSetTopicAsDefaultClicked(topic: Topic) {
        viewModelScope.launch {
            updateTopicUseCase(topic.copy(isDefault = true))
        }
        updateState { copy(topicText = "") }
    }

    private fun onSaveTopicClicked(topic: String) {
        viewModelScope.launch {
            createTopicUseCase(topic = topic, default = true)
        }
        updateState { copy(topicText = "") }
    }

    private fun onMoodClicked(mood: Mood) {
        setMoodInSharedPreferencesUseCase(mood)
        updateState { copy(selectedMood = mood) }
    }

    private fun onTopicTextChanged(topicText: String) {
        val filteredList = state.value.topicList.filter { it.name.lowercase().contains(topicText.lowercase()) }
        updateState {
            copy(
                topicText = topicText,
                topicList = filteredList
            )
        }
    }

    private fun onAddTopicButtonClicked() {
        updateState { copy(showTopicTextField = true) }
    }

    private fun onDeleteTopicButtonClicked(topicName: String) {
        val topic = state.value.topicList.find { it.name == topicName }
        topic?.let {
            viewModelScope.launch {
                updateTopicUseCase(it.copy(isDefault = false))
            }
        }
    }
}