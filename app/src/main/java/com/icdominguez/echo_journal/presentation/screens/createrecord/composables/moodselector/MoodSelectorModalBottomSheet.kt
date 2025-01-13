package com.icdominguez.echo_journal.presentation.screens.createrecord.composables.moodselector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Mood
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.MoodItem
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.RecordAudioModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodSelectorModalBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    moodList: List<Mood> = emptyList(),
    selectedMood: Mood? = null,
    onMoodClicked: (Mood) -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissRequest() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.how_are_you_doing),
                style = LocalEchoJournalTypography.current.headlineMedium,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            ) {
                moodList.map { mood ->
                    MoodItem(
                        modifier = Modifier
                            .weight(1f),
                        mood = mood,
                        selectedMood = selectedMood,
                        onMoodClicked = { onMoodClicked(it) }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 24.dp,
                        bottom = 18.dp,
                    )
            ) {
                CancelButton(
                    modifier = Modifier.weight(1f),
                    onCancelButtonClicked = { onDismissRequest() },
                )
                Spacer(modifier = Modifier.width(16.dp))
                ConfirmButton(
                    modifier = Modifier.weight(2f),
                    onConfirmButtonClicked = { onConfirm() },
                    enabled = selectedMood != null,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MoodSelectorModalBottomSheetPreview() {
    RecordAudioModalBottomSheet()
}