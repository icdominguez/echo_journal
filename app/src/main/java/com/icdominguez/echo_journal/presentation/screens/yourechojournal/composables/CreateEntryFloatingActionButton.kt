package com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CreateEntryFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .padding(
                bottom = 32.dp,
                end = 16.dp,
            )
            .size(64.dp)
            .shadow(
                elevation = 4.dp,
                shape = CircleShape,
                clip = true,
                spotColor = Color(android.graphics.Color.parseColor("#1B6EF3"))
            )
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable { onClick() }
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .size(32.dp),
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = Color.White,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateEntryFloatingActionButtonPreview() {
    CreateEntryFloatingActionButton()
}