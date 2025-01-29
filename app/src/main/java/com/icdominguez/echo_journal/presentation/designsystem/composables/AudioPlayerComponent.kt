package com.icdominguez.echo_journal.presentation.designsystem.composables

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.common.millisToMinutesSecondsFormat
import com.icdominguez.echo_journal.presentation.model.Entry
import com.icdominguez.echo_journal.presentation.screens.FakeData
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Moods
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.floor

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
                top = 6.dp,
                bottom = 6.dp,
                start = 4.dp,
                end = 10.dp,
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 4.dp,
                        shape = CircleShape
                    )
                    .clip(shape = CircleShape)
                    .size(32.dp)
                    .background(Color.White)
                    .clickable(
                        enabled = entry.audioDuration > 0L,
                        onClick = {
                            if (entry.isPlaying) {
                                onPauseClicked(entry)
                            } else {
                                onPlayClicked(entry)
                            }
                        }
                    )
            ) {
                AnimatedContent(
                    modifier = Modifier.align(Alignment.Center),
                    targetState = entry.isPlaying,
                    label = ""
                ) { isPlaying ->
                    if (isPlaying) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.Center),
                            imageVector = Icons.Default.Pause ,
                            tint = entry.mood?.color ?: Moods.SAD.color,
                            contentDescription = null,
                        )
                    } else {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.Center),
                            imageVector = Icons.Default.PlayArrow,
                            tint = entry.mood?.color ?: Moods.SAD.color,
                            contentDescription = null,
                        )
                    }
                }
            }

            AudioWaveform(
                modifier = Modifier
                    .weight(1f)
                    .height(32.dp)
                    .padding(horizontal = 6.dp),
                progress = toAudioWaveFormValue(audioProgress = entry.audioProgress.toFloat(), audioDuration = entry.audioDuration.toFloat()),
                spikeWidth = 6.dp,
                spikePadding = 2.dp,
                spikeRadius = 10.dp,
                waveformBrush = SolidColor(entry.mood?.color?.copy(alpha = 0.2f) ?: Moods.SAD.color.copy(alpha = 0.2f)),
                progressBrush = SolidColor(entry.mood?.color ?: Moods.SAD.color),
                amplitudes = entry.amplitudes.map { it.toInt() },
                onProgressChange = { newValue ->
                    currentSecondsPlaying = floor(((newValue / 1f) * entry.audioDuration).toInt() + 1000F)
                    secondsString = currentSecondsPlaying.toLong().millisToMinutesSecondsFormat()
                    onSliderValueChanged(entry.copy(audioProgress = ((newValue / 1f) * entry.audioDuration).toInt()))
                },
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

private fun toAudioWaveFormValue(audioProgress: Float, audioDuration: Float): Float {
    val value = (audioProgress / audioDuration).coerceIn(0f, 1f)
    return value
}

@Preview(showBackground = true)
@Composable
private fun AudioPlayerComponentPreview() {
    AudioPlayerComponent(
        entry = FakeData.timelineEntries[0],
    )
}