package com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.icdominguez.echo_journal.R

@Composable
fun CreateEntryFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Image(
        modifier = modifier
            .clickable { onClick() },
        painter = painterResource(R.drawable.fab_icon),
        contentDescription = null
    )
}

@Preview(showBackground = true)
@Composable
fun CreateEntryFloatingActionButtonPreview() {
    CreateEntryFloatingActionButton()
}