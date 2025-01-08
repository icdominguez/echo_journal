package com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.entrytimeline

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.data.model.EntryEntity
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography
import com.icdominguez.echo_journal.presentation.screens.FakeData
import com.icdominguez.echo_journal.presentation.toHoursAndMinutes

@Composable
fun EntryTimeLineItem(
    entry: EntryEntity,
    index: Int,
    lastIndex: Int
) {
    val moodIcon: Int = when(entry.mood) {
        "SAD" -> R.drawable.sad_mood_on
        "STRESSED" -> R.drawable.stressed_mood_on
        "NEUTRAL" -> R.drawable.neutral_mood_on
        "PEACEFUL" -> R.drawable.neutral_mood_on
        "EXCITED" -> R.drawable.excited_mood_on
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
            modifier = Modifier.padding(start = 16.dp).padding(top = if (index == 0) 8.dp else 0.dp),
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
                .clip(shape = RoundedCornerShape(6.dp))
                .background(color = Color.White)
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
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = entry.title,
                        style = LocalEchoJournalTypography.current.headlineSmall.copy(
                            fontWeight = FontWeight(weight = 500)
                        )
                    )
                    Text(entry.date.toHoursAndMinutes())
                }

                //Audio seekbar

                //Description
                DescriptionText(
                    text = entry.description
                )
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