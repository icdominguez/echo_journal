package com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Mood
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Moods

@Composable
fun MoodItem(
    modifier: Modifier = Modifier,
    mood: Mood = Moods.allMods[0],
    selectedMood: Mood? = null,
    onMoodClicked: (Mood) -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val drawable = if(mood.name == selectedMood?.name) mood.selectedDrawable else mood.unselectedDrawable
        val textStyle =
            if(mood.name == selectedMood?.name)
                LocalEchoJournalTypography.current.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
            else {
                LocalEchoJournalTypography.current.bodySmall.copy(color = MaterialTheme.colorScheme.outline)
            }
        Image(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onMoodClicked(mood) }
                )
                .size(40.dp),
            painter = painterResource(drawable),
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = mood.name,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MoodItemPreview() {
    MoodItem()
}