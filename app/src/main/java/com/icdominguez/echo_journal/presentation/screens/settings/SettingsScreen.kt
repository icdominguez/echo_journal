package com.icdominguez.echo_journal.presentation.screens.settings

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.presentation.designsystem.composables.AssociatedTopicItem
import com.icdominguez.echo_journal.presentation.designsystem.composables.TopicRowItem
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography
import com.icdominguez.echo_journal.presentation.designsystem.theme.ScreenBg1
import com.icdominguez.echo_journal.presentation.designsystem.theme.ScreenBg2
import com.icdominguez.echo_journal.presentation.designsystem.theme.TopicBackground
import com.icdominguez.echo_journal.presentation.screens.createrecord.composables.CreateTopicItem
import com.icdominguez.echo_journal.presentation.screens.createrecord.composables.TopicTextField
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.MoodItem
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.filters.CustomDropdownMenu

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(
    state: SettingsScreenViewModel.State = SettingsScreenViewModel.State(),
    uiEvent: (SettingsScreenViewModel.Event) -> Unit = {},
    navigateBack: () -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SettingsScreenTopBar(
                modifier = Modifier
                    .background(Color.Transparent),
                onNavigationIconClicked = { navigateBack() }
            )
        }
    ) { innerPadding ->

        val topicsHeight = with(LocalDensity.current) { 81.toDp() }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush
                        .linearGradient(colors = listOf(ScreenBg1, ScreenBg2)),
                )
                .padding(innerPadding)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 8.dp,
                            start = 16.dp,
                            end = 16.dp,
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(14.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.my_mood),
                            style = LocalEchoJournalTypography.current.headlineSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = 2.dp),
                            text = stringResource(R.string.select_your_mood),
                            style = LocalEchoJournalTypography.current.bodyMedium,
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 14.dp),
                            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                        ) {
                            state.moodList.map { mood ->
                                MoodItem(
                                    modifier = Modifier
                                        .weight(1f),
                                    mood = mood,
                                    selectedMood = state.selectedMood,
                                    onMoodClicked = { uiEvent(SettingsScreenViewModel.Event.OnMoodClicked(it)) }
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp,
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        ),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(14.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.my_topics),
                            style = LocalEchoJournalTypography.current.headlineSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = 2.dp),
                            text = stringResource(R.string.select_your_topics),
                            style = LocalEchoJournalTypography.current.bodyMedium,
                        )

                        FlowRow(
                            modifier = Modifier
                                .padding(top = 14.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            state.filteredTopicList.map { topic ->
                                AssociatedTopicItem(
                                    topic = topic.name,
                                    clickable = true,
                                    onDeleteTopicClicked = { uiEvent(SettingsScreenViewModel.Event.OnDeleteTopicButtonClicked(it)) }
                                )
                            }

                            if(!state.showTopicTextField) {
                                Box(
                                    modifier = Modifier
                                        .size(topicsHeight)
                                        .clip(shape = CircleShape)
                                        .background(TopicBackground)
                                        .clickable {
                                            uiEvent(SettingsScreenViewModel.Event.OnAddTopicButtonClicked)
                                        },
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = null,
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .height(topicsHeight),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                if(state.showTopicTextField) {
                                    TopicTextField(
                                        modifier = Modifier
                                            .focusRequester(focusRequester),
                                        text = state.topicText,
                                        onTopicTextChange = { uiEvent(SettingsScreenViewModel.Event.OnTopicTextChanged(it)) },
                                        showHintText = false,
                                    )
                                }

                                if(state.topicText != "") {
                                    CustomDropdownMenu(
                                        expanded = state.topicText.isNotEmpty(),
                                        focusable = false,
                                        onDismissRequest = {},
                                        dropdownMenuContent = {
                                            state.topicList.map { topic ->
                                                if(!state.filteredTopicList.contains(topic))
                                                TopicRowItem(
                                                    topic = topic.name,
                                                    onClick = { uiEvent(SettingsScreenViewModel.Event.OnSetTopicAsDefaultClicked(topic)) }
                                                )
                                            }
                                            if(state.topicText.isNotEmpty() && state.topicList.none { it.name.lowercase() == state.topicText.lowercase() } && state.filteredTopicList.none { it.name.lowercase() == state.topicText.lowercase() }) {
                                                CreateTopicItem(
                                                    value = state.topicText,
                                                    onClick = { uiEvent(SettingsScreenViewModel.Event.OnSaveTopicButtonClicked(it)) },
                                                )

                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(state.showTopicTextField) {
        if(state.showTopicTextField) {
            focusRequester.requestFocus()
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen()
}