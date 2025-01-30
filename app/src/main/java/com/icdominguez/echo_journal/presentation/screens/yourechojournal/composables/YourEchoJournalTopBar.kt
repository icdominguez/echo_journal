package com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.icdominguez.echo_journal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourEchoJournalTopBar(
    modifier: Modifier = Modifier,
    onSettingsIconClicked: () -> Unit = {},
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {
            Text(text = stringResource(R.string.your_echo_journal_top_bar_title))
        },
        actions = {
            IconButton(
                onClick = { onSettingsIconClicked() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun YourEchoJournalTopBarPreview() {
    YourEchoJournalTopBar()
}