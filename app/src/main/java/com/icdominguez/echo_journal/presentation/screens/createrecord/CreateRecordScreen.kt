package com.icdominguez.echo_journal.presentation.screens.createrecord

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.presentation.designsystem.composables.AssociatedTopicItem
import com.icdominguez.echo_journal.presentation.designsystem.composables.AudioPlayerComponent
import com.icdominguez.echo_journal.presentation.designsystem.composables.TopicRowItem
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography
import com.icdominguez.echo_journal.presentation.screens.createrecord.composables.CreateRecordScreenTopBar
import com.icdominguez.echo_journal.presentation.screens.createrecord.composables.CreateTopicItem
import com.icdominguez.echo_journal.presentation.screens.createrecord.composables.EntryTextField
import com.icdominguez.echo_journal.presentation.screens.createrecord.composables.TopicTextField
import com.icdominguez.echo_journal.presentation.screens.createrecord.composables.moodselector.MoodSelectorModalBottomSheet
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.filters.CustomDropdownMenu

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateRecordScreen(
    state: CreateRecordScreenViewModel.State = CreateRecordScreenViewModel.State(),
    uiEvent: (CreateRecordScreenViewModel.Event) -> Unit = {},
    navigateBack: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CreateRecordScreenTopBar(
                onNavigationIconClicked = {
                    uiEvent(CreateRecordScreenViewModel.Event.OnBackClicked)
                    navigateBack()
                }
            )
        }
    ) { innerPadding ->

        val topicsHeight = with(LocalDensity.current) { 81.toDp() }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .clip(shape = CircleShape)
                                .clickable {
                                    uiEvent(CreateRecordScreenViewModel.Event.OnAddMoodButtonClicked)
                                }
                                .size(32.dp),
                            painter = painterResource(if(state.selectedMood != null) state.selectedMood.selectedDrawable else R.drawable.add_mood_button),
                            contentDescription = null
                        )

                        EntryTextField(
                            entryText = state.entryText,
                            onEntryTextChange = { uiEvent(CreateRecordScreenViewModel.Event.OnEntryTextChanged(it)) },
                        )
                    }

                    AudioPlayerComponent(
                        audioDuration = state.audioRecordedDuration,
                        color = state.selectedMood?.color ?: MaterialTheme.colorScheme.primary,
                        onPlayClicked = { uiEvent(CreateRecordScreenViewModel.Event.OnPlayClicked) },
                        onPauseClicked = { uiEvent(CreateRecordScreenViewModel.Event.OnPauseClicked) },
                        onSliderValueChanged = { uiEvent(CreateRecordScreenViewModel.Event.OnSliderValueChanged(it)) }
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .height(topicsHeight),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = "#",
                                style = LocalEchoJournalTypography.current.bodyMedium.copy(color = MaterialTheme.colorScheme.outlineVariant)
                            )
                        }

                        state.selectedTopics.map { topic ->
                            AssociatedTopicItem(
                                topic = topic,
                                clickable = true,
                                onDeleteTopicClicked = { uiEvent(CreateRecordScreenViewModel.Event.OnDeleteTopicButtonClicked(it)) },
                            )
                        }

                        Column(
                            modifier = Modifier
                                .height(topicsHeight),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            TopicTextField(
                                text = state.topicText,
                                onTopicTextChange = { uiEvent(CreateRecordScreenViewModel.Event.OnTopicTextChanged(it)) },
                            )

                            CustomDropdownMenu(
                                modifier = Modifier
                                    .padding(top = 12.dp),
                                expanded = state.topicText.isNotEmpty(),
                                focusable = false,
                                onDismissRequest = {},
                                dropdownMenuContent = {
                                    state.filteredTopicList.map { topic ->
                                        if(!state.selectedTopics.contains(topic.name)) {
                                            TopicRowItem(
                                                topic = topic.name,
                                                onClick = { uiEvent(CreateRecordScreenViewModel.Event.OnTopicClicked(topic.name)) }
                                            )
                                        }
                                    }
                                    if(state.topicText.isNotEmpty() && !state.selectedTopics.contains(state.topicText) && !state.topicList.any { it.name == state.topicText }) {
                                        CreateTopicItem(
                                            value = state.topicText,
                                            onClick = { uiEvent(CreateRecordScreenViewModel.Event.OnAddTopicClicked(it)) },
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }

            if(state.showMoodSelectorModalBottomSheet) {
                MoodSelectorModalBottomSheet(
                    onDismissRequest = { uiEvent(CreateRecordScreenViewModel.Event.OnMoodSelectorModalBottomSheetDismissed) },
                    moodList = state.moodList,
                    selectedMood = state.moodSelectorModalBottomSheetSelected,
                    onMoodClicked = { uiEvent(CreateRecordScreenViewModel.Event.OnMoodClicked(it))},
                    onConfirm = { uiEvent(CreateRecordScreenViewModel.Event.OnMoodSelectorModalBottomSheetConfirmed) }
                )
            }
        }

        BackHandler {
            uiEvent(CreateRecordScreenViewModel.Event.OnBackClicked)
            navigateBack()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateRecordScreenPreview() {
    CreateRecordScreen()
}