package com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.common.millisToFormattedString
import com.icdominguez.echo_journal.data.timer.AndroidTimer
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordAudioModalBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    navigateToNewEntry: () -> Unit = {},
    onRecordAudioStopped: () -> Unit = {},
    onRecordAudioPaused: () -> Unit = {},
    onRecordAudioResumed: () -> Unit = {},
) {
    val localDensity = LocalDensity.current
    val timer = AndroidTimer()
    timer.start()
    var isPaused by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var maxHeightForAudioControls by remember { mutableStateOf(0.dp) }

    val heightModifier = if(maxHeightForAudioControls == 0.dp) {
        Modifier
    } else {
        Modifier.heightIn(min = maxHeightForAudioControls, max = maxHeightForAudioControls)
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissRequest() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.recording_your_memories),
                style = LocalEchoJournalTypography.current.headlineMedium
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = timer.millis.longValue.millisToFormattedString(),
                style = LocalEchoJournalTypography.current.bodySmall
            )
        }

        Row(
            modifier = heightModifier
                .onGloballyPositioned { layoutCoordinates ->
                    if(maxHeightForAudioControls == 0.dp) {
                        maxHeightForAudioControls = with(localDensity) { layoutCoordinates.size.height.toDp() }
                    }
                }
                .fillMaxWidth()
                .padding(
                    top = 24.dp,
                    bottom = 14.dp,
                    start = 50.dp,
                    end = 50.dp,
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .clickable {
                        onDismissRequest()
                    },
                painter = painterResource(R.drawable.cancel_record_button),
                contentDescription = null,
            )

            Image(
                modifier = modifier
                    .clip(shape = CircleShape)
                    .clickable {
                        if(isPaused) {
                            isPaused = false
                            onRecordAudioResumed()
                            timer.start()
                        } else {
                            onRecordAudioStopped()
                            navigateToNewEntry()
                            timer.reset()
                        }
                    },
                painter = painterResource(if(isPaused)R.drawable.record else R.drawable.save_record_button),
                contentDescription = null,
            )

            Image(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .clickable {
                        if(isPaused) {
                            isPaused = false
                            onRecordAudioPaused()
                            navigateToNewEntry()
                            timer.reset()
                        } else {
                            isPaused = true
                            timer.pause()
                        }
                    },
                painter = painterResource(if(isPaused) R.drawable.save_record_button_small else R.drawable.pause_record_button),
                contentDescription = null,
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun RecordAudioModalBottomSheetPreview() {
    RecordAudioModalBottomSheet()
}