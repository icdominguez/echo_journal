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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.presentation.designsystem.theme.BackgroundMoodItemSelected
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography
import com.icdominguez.echo_journal.presentation.designsystem.theme.Primary30
import com.icdominguez.echo_journal.presentation.designsystem.theme.SecondaryContainer
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Mood
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Moods

@Composable
fun MoodFilterChip(
    columnSize: Size = Size.Zero,
    selectedMoodList: List<Mood> = listOf(),
    onCloseButtonClicked: () -> Unit = {},
    onMoodItemClicked: (mood: Mood) -> Unit = {}
) {

    val maxSize = 2

    CustomFilterChip(
        columnSize = columnSize,
        selectedMoodList = selectedMoodList,
        filterChipLabel = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectedMoodList.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy((-4).dp)
                    ) {
                        selectedMoodList.forEach { selection ->
                            Image(
                                modifier = Modifier
                                    .size(22.dp),
                                painter = painterResource(selection.selectedDrawable),
                                contentDescription = null
                            )
                        }
                    }
                }

                var text = stringResource(id = R.string.all_moods)

                if (selectedMoodList.isNotEmpty()) {
                    text = if (selectedMoodList.size > maxSize) {
                        "${selectedMoodList[0].name}," +
                                " ${selectedMoodList[1].name} " +
                                "+ ${selectedMoodList.size - maxSize}"
                    } else {
                        if (selectedMoodList.size == 1) {
                            selectedMoodList[0].name
                        } else {
                            "${selectedMoodList[0].name}, ${selectedMoodList[1].name}"
                        }
                    }
                }

                Text(
                    text = text,
                    style = LocalEchoJournalTypography.current.labelLarge,
                    color = Color.Black
                )

                if (selectedMoodList.isNotEmpty()) {
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
            Moods.allMods.reversed().forEach { mood ->
                DropdownMenuItem(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .padding(horizontal = 4.dp)
                        .background(
                            color = if (selectedMoodList.contains(mood)) BackgroundMoodItemSelected.copy(
                                alpha = 0.05f
                            ) else Color.Transparent,
                            shape = RoundedCornerShape(size = 8.dp)
                        ),
                    text = {
                        Text(
                            text = mood.name,
                            style = LocalEchoJournalTypography.current.button
                        )
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(mood.selectedDrawable),
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        if (selectedMoodList.contains(mood)) {
                            Image(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Primary30)
                            )
                        }
                    },
                    onClick = {
                        onMoodItemClicked(mood)
                    }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MoodFilterChipPreview() {
    MoodFilterChip()
}