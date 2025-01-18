package com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.presentation.designsystem.composables.TopicRowItem
import com.icdominguez.echo_journal.presentation.designsystem.theme.BackgroundMoodItemSelected
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography
import com.icdominguez.echo_journal.presentation.designsystem.theme.SecondaryContainer
import com.icdominguez.echo_journal.presentation.model.Topic

private const val MAX_VISIBLE_MOODS = 2

@Composable
fun TopicFilterChip(
    topics: List<Topic> = emptyList(),
    selectedTopicsList: List<Topic> = emptyList(),
    onCloseButtonClicked: () -> Unit = {},
    onTopicItemClicked: (topic: Topic) -> Unit = {}
) {
    CustomFilterChip(
        selectedList = selectedTopicsList,
        filterChipLabel = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {

                var text = stringResource(id = R.string.all_topics)

                if (selectedTopicsList.isNotEmpty()) {
                    text = if (selectedTopicsList.size > MAX_VISIBLE_MOODS) {
                        "${selectedTopicsList[0].name}," +
                                " ${selectedTopicsList[1].name} " +
                                "+ ${selectedTopicsList.size - MAX_VISIBLE_MOODS}"
                    } else {
                        if (selectedTopicsList.size == 1) {
                            selectedTopicsList[0].name
                        } else {
                            "${selectedTopicsList[0].name}, ${selectedTopicsList[1].name}"
                        }
                    }
                }

                Text(
                    text = text,
                    style = LocalEchoJournalTypography.current.labelLarge,
                    color = Color.Black
                )

                if (selectedTopicsList.isNotEmpty()) {
                    Image(
                        modifier = Modifier
                            .clickable { onCloseButtonClicked() }
                            .size(18.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(SecondaryContainer)
                    )
                }
            }
        },
        dropdownMenuContent = {
            topics.forEach { topic ->
                val isSelected = selectedTopicsList.contains(topic)
                TopicRowItem(
                    modifier = Modifier
                        .padding(
                            vertical = 2.dp, horizontal = 4.dp
                        )
                        .background(
                            color = if (isSelected) BackgroundMoodItemSelected.copy(
                                alpha = 0.05f
                            ) else Color.Transparent,
                            shape = RoundedCornerShape(size = 8.dp)
                        ),
                    topic = topic.name,
                    onClick = { onTopicItemClicked(topic) },
                    isSelected = isSelected
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopicFilterChipPreview() {
    TopicFilterChip()
}