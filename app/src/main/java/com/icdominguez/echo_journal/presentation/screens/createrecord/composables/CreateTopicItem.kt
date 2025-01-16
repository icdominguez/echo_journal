package com.icdominguez.echo_journal.presentation.screens.createrecord.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Composable
fun CreateTopicItem(
    modifier: Modifier = Modifier,
    value: String,
    onClick: (String) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick(value)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 8.dp,
                    end = 4.dp,
                ),
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            modifier = Modifier
                .padding(start = 4.dp),
            text = "Create '${value}'",
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
@Preview(showBackground = true)
@Composable
fun CreateTopicItemPreview() {
    CreateTopicItem(value = "Topic")
}