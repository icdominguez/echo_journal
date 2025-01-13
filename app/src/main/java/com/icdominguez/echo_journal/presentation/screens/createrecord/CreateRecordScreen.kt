package com.icdominguez.echo_journal.presentation.screens.createrecord

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.presentation.designsystem.composables.AudioPlayerComponent
import com.icdominguez.echo_journal.presentation.screens.createrecord.composables.CreateRecordScreenTopBar
import com.icdominguez.echo_journal.presentation.screens.createrecord.composables.EntryTextField
import com.icdominguez.echo_journal.presentation.screens.createrecord.composables.moodselector.MoodSelectorModalBottomSheet

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
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
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

                Spacer(modifier = Modifier
                    .padding(top = 16.dp))

                AudioPlayerComponent(
                    audioDuration = state.audioRecordedDuration,
                    color = state.selectedMood?.color ?: MaterialTheme.colorScheme.primary,
                    onPlayClicked = { uiEvent(CreateRecordScreenViewModel.Event.OnPlayClicked) },
                    onPauseClicked = { uiEvent(CreateRecordScreenViewModel.Event.OnPauseClicked) },
                    onSliderValueChanged = { uiEvent(CreateRecordScreenViewModel.Event.OnSliderValueChanged(it)) }
                )
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