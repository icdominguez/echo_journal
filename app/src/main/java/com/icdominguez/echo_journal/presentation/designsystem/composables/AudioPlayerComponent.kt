package com.icdominguez.echo_journal.presentation.designsystem.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.common.millisToMinutesSecondsFormat
import com.icdominguez.echo_journal.presentation.model.Entry
import com.icdominguez.echo_journal.presentation.screens.FakeData
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Moods
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioPlayerComponent(
    modifier: Modifier = Modifier,
    entry: Entry,
    onPlayClicked: (Entry) -> Unit = {},
    onPauseClicked: (Entry) -> Unit = {},
    onSliderValueChanged: (Entry) -> Unit = {},
    onAudioPlayerEnd: (Entry) -> Unit = {},
) {
    var currentSecondsPlaying by remember { mutableFloatStateOf(0f) }
    var secondsString by remember { mutableStateOf("00:00") }

    LaunchedEffect(entry.isPlaying) {
        if(entry.isPlaying) {
            while (isActive) {
                if(currentSecondsPlaying < entry.audioDuration.toFloat()) {
                    delay(1000)
                    currentSecondsPlaying += 1000f
                    secondsString = currentSecondsPlaying.toLong().millisToMinutesSecondsFormat()
                } else {
                    onAudioPlayerEnd(entry)
                    currentSecondsPlaying = 0f
                    secondsString = "00:00"
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(50.dp))
            .background(
                color = entry.mood?.color?.copy(alpha = 0.1f) ?: Moods.SAD.color.copy(alpha = 0.1f)
            )
            .padding(
                top = 4.dp,
                bottom = 4.dp,
                start = 4.dp,
                end = 10.dp,
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(32.dp)
                    .background(Color.White)
                    .clickable(
                        enabled = entry.audioDuration > 0L,
                        onClick = {
                            if(entry.isPlaying) {
                                onPauseClicked(entry)
                            } else{
                                onPlayClicked(entry)
                            }
                        }
                    )
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center),
                    imageVector = if(entry.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    tint = entry.mood?.color ?: Moods.SAD.color,
                    contentDescription = null,
                )
            }

            Slider(
                modifier = modifier
                    .weight(1f)
                    .height(32.dp)
                    .padding(horizontal = 6.dp),
                colors = SliderDefaults.colors(
                    activeTrackColor = entry.mood?.color ?: Moods.SAD.color,
                    inactiveTickColor = entry.mood?.color?.copy(alpha = 0.8f) ?: Moods.SAD.color.copy(alpha = 0.8f)
                ),
                value = currentSecondsPlaying,
                valueRange = 0f..entry.audioDuration.toFloat(),
                onValueChange = { newValue ->
                    currentSecondsPlaying = (newValue / 1000).toInt() * 1000F
                    secondsString = currentSecondsPlaying.toLong().millisToMinutesSecondsFormat()
                    onSliderValueChanged(entry.copy(audioProgress = newValue.toInt()))
                },
                track = { sliderState ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, end = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth(
                                    (sliderState.value - sliderState.valueRange.start) /
                                            (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                                )
                                .height(4.dp)
                                .background(
                                    color = entry.mood?.color ?: MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )
                        Box(
                            Modifier
                                .fillMaxWidth(1f)
                                .height(4.dp)
                                .background(
                                    color = entry.mood?.color?.copy(0.3f) ?: MaterialTheme.colorScheme.primary.copy(0.3f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )
                    }
                },
                thumb = {}
            )

            Row {
                Text(
                    text = secondsString,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = "/${entry.audioDuration.toLong().millisToMinutesSecondsFormat()}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AudioPlayerComponentPreview() {
    AudioPlayerComponent(
        entry = FakeData.timelineEntries[0],
    )
}