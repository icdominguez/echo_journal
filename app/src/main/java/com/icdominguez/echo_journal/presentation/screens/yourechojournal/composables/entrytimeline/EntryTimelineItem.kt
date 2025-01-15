package com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.entrytimeline

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.common.toHoursAndMinutes
import com.icdominguez.echo_journal.presentation.designsystem.composables.AudioPlayerComponent
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography
import com.icdominguez.echo_journal.presentation.designsystem.theme.TopicBackground
import com.icdominguez.echo_journal.presentation.model.Entry
import com.icdominguez.echo_journal.presentation.screens.FakeData

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EntryTimeLineItem(
    entry: Entry,
    index: Int,
    lastIndex: Int,
    onAudioPlayerStart: () -> Unit = {},
    onAudioPlayerPause: () -> Unit = {},
) {
    val moodIcon: Int = when(entry.mood) {
        "Sad" -> R.drawable.sad_mood_on
        "Stressed" -> R.drawable.stressed_mood_on
        "Neutral" -> R.drawable.neutral_mood_on
        "Peaceful" -> R.drawable.peaceful_mood_on
        "Excited" -> R.drawable.excited_mood_on
        else -> R.drawable.neutral_mood_on
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.Center)
            .height(intrinsicSize = IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(space = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(top = if (index == 0) 8.dp else 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(index != 0) {
                VerticalDivider(modifier = Modifier.height(8.dp))
            }

            Image(
                painter = painterResource(id = moodIcon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(size = 32.dp)
            )

            if (index != lastIndex) {
                VerticalDivider(modifier = Modifier
                    .fillMaxHeight()
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 16.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(shape = RoundedCornerShape(10.dp))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .padding(bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(space = 6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(weight = 1f),
                        text = entry.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = LocalEchoJournalTypography.current.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    Text(
                        text = entry.date.toHoursAndMinutes(),
                        style = LocalEchoJournalTypography.current.bodySmall,
                    )
                }

                //Audio seekbar
                AudioPlayerComponent(
                    onPlayClicked = { onAudioPlayerStart() }
                )

                //Description
                if (entry.description.isNotEmpty()) {
                    DescriptionText(
                        text = entry.description
                    )
                }

                if (entry.topics.isNotEmpty()) {
                    FlowRow (
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val topics: List<String> = entry.topics
                        topics.forEach { name ->
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = TopicBackground,
                                        shape = CircleShape
                                    )
                                    .padding(vertical = 2.dp, horizontal = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier.size(14.dp),
                                    imageVector = Icons.Outlined.Tag,
                                    contentDescription = null
                                )

                                Text(
                                    text = name,
                                    style = LocalEchoJournalTypography.current.headlineXSmall,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }


                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EntryTimeLineItemComponent() {
    EntryTimeLineItem(
        entry = FakeData.timelineEntries[0],
        index = 0,
        lastIndex = 0
    )
}