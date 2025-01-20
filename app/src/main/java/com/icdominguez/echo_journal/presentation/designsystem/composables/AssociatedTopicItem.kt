package com.icdominguez.echo_journal.presentation.designsystem.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.presentation.designsystem.theme.CloseButton
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography
import com.icdominguez.echo_journal.presentation.designsystem.theme.TopicBackground

@Composable
fun AssociatedTopicItem(
    modifier: Modifier = Modifier,
    topic: String = "",
    clickable: Boolean = false,
    textStyle: TextStyle = LocalEchoJournalTypography.current.headlineXSmall,
    onDeleteTopicClicked: (String) -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(
                color = TopicBackground,
                shape = CircleShape,
            )
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 10.dp),
                text = "#",
                style = LocalEchoJournalTypography.current.button.copy(color = MaterialTheme.colorScheme.outlineVariant)
            )
            Text(
                modifier = Modifier
                    .padding(
                        vertical = 6.dp,
                        horizontal = 4.dp
                    ),
                text = topic,
                style = textStyle
            )
            Icon(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(14.dp)
                    .clickable(
                        enabled = clickable,
                        onClick = {
                            onDeleteTopicClicked(topic)
                        }
                    ),
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = CloseButton,
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun TopicItemPreview() {
    AssociatedTopicItem(topic = "Topic")
}