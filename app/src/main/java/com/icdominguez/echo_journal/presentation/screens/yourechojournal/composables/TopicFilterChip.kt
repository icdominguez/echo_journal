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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.data.model.TopicEntity
import com.icdominguez.echo_journal.presentation.designsystem.theme.BackgroundMoodItemSelected
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography
import com.icdominguez.echo_journal.presentation.designsystem.theme.Primary30
import com.icdominguez.echo_journal.presentation.designsystem.theme.SecondaryContainer
import com.icdominguez.echo_journal.presentation.screens.FakeData

private const val maxSize = 2

@Composable
fun TopicFilterChip(
    selectedTopicsList: List<TopicEntity> = listOf(),
    onCloseButtonClicked: () -> Unit = {},
    onTopicItemClicked: (topic: TopicEntity) -> Unit = {}
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
                    text = if (selectedTopicsList.size > maxSize) {
                        "${selectedTopicsList[0].name}," +
                                " ${selectedTopicsList[1].name} " +
                                "+ ${selectedTopicsList.size - maxSize}"
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
                            .clickable {
                                onCloseButtonClicked()
                            }
                            .size(18.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(SecondaryContainer)
                    )
                }
            }
        },
        dropdownMenuContent = {
            FakeData.topicsEntries.forEach { topic ->
                DropdownMenuItem(
                    modifier = Modifier
                        .padding(vertical = 2.dp, horizontal = 4.dp)
                        .background(
                            color = if (selectedTopicsList.contains(topic)) BackgroundMoodItemSelected.copy(
                                alpha = 0.05f
                            ) else Color.Transparent,
                            shape = RoundedCornerShape(size = 8.dp)
                        ),
                    text = {
                        Text(
                            text = topic.name,
                            style = LocalEchoJournalTypography.current.button
                        )
                    },
                    leadingIcon = {
                        Image(
                            imageVector = Icons.Outlined.Tag,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        if (selectedTopicsList.contains(topic)) {
                            Image(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Primary30)
                            )
                        }
                    },
                    onClick = {
                        onTopicItemClicked(topic)
                    }
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