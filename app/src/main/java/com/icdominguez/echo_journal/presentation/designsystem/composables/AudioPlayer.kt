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
import androidx.compose.runtime.derivedStateOf
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioPlayerComponent(
    modifier: Modifier = Modifier,
    audioDuration: Int = 0,
    onPlayClicked: () -> Unit = {},
    onPauseClicked: () -> Unit = {},
    onSliderValueChanged: (Int) -> Unit = {},
    color: Color = MaterialTheme.colorScheme.primary,
) {
    var currentSecondsPlaying by remember { mutableFloatStateOf(0f) }
    var secondsString by remember { mutableStateOf("00:00") }
    var playing by remember { mutableStateOf(false) }

    LaunchedEffect(playing) {
        if(playing) {
            while (isActive) {
                if(currentSecondsPlaying < audioDuration.toFloat()) {
                    delay(1000)
                    currentSecondsPlaying += 1000f
                    secondsString = currentSecondsPlaying.toLong().millisToMinutesSecondsFormat()
                } else {
                    playing = false
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
            .background(color = color.copy(alpha = 0.1f))
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
                        enabled = audioDuration > 0L,
                        onClick = {
                            if(playing) {
                                playing = false
                                onPauseClicked()
                            } else{
                                playing = true
                                onPlayClicked()
                            }
                        }
                    )
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center),
                    imageVector = if(playing) Icons.Default.Pause else Icons.Default.PlayArrow,
                    tint = color,
                    contentDescription = null,
                )
            }

            Slider(
                modifier = modifier
                    .weight(1f)
                    .height(32.dp)
                    .padding(horizontal = 6.dp),
                colors = SliderDefaults.colors(
                    activeTrackColor = color,
                    inactiveTickColor = color.copy(alpha = 0.8f)
                ),
                value = currentSecondsPlaying,
                valueRange = 0f..audioDuration.toFloat(),
                onValueChange = { newValue ->
                    currentSecondsPlaying = (newValue / 1000).toInt() * 1000F
                    secondsString = currentSecondsPlaying.toLong().millisToMinutesSecondsFormat()
                    onSliderValueChanged(newValue.toInt())
                },
                track = { sliderState ->
                    val fraction by remember {
                        derivedStateOf {
                            (sliderState.value - sliderState.valueRange.start) /
                                    (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, end = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth(fraction)
                                .height(4.dp)
                                .background(
                                    color = color,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )
                        Box(
                            Modifier
                                .fillMaxWidth(1f)
                                .height(4.dp)
                                .background(
                                    color = color.copy(alpha = 0.3f),
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
                    text = "/${audioDuration.toLong().millisToMinutesSecondsFormat()}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AudioSeekbarPreview() {
    AudioPlayerComponent(
        color = MaterialTheme.colorScheme.primary,
    )
}