package com.icdominguez.echo_journal.presentation.designsystem.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography

@Composable
fun TopicRowItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    topic: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    bottom = 8.dp,
                    start = 8.dp,
                    end = 4.dp,
                ),
            text = "#",
            style = LocalEchoJournalTypography.current.button.copy(color = MaterialTheme.colorScheme.primary),
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = topic,
            style = LocalEchoJournalTypography.current.button
                .copy(color = Color(0xFF3B4663))
        )
    }
}
@Preview(showBackground = true)
@Composable
fun TopicRowItemPreview() {
    TopicRowItem(topic = "Topic")
}