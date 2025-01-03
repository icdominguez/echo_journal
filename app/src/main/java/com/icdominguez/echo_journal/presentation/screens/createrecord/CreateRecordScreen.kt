package com.icdominguez.echo_journal.presentation.screens.createrecord

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.icdominguez.echo_journal.presentation.screens.createrecord.composables.CreateRecordScreenTopBar

@Composable
fun CreateRecordScreen(
    state: CreateRecordScreenViewModel.State = CreateRecordScreenViewModel.State(),
    uiEvent: (CreateRecordScreenViewModel.Event) -> Unit = {},
    navigateBack: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CreateRecordScreenTopBar(
                onNavigationIconClicked = { navigateBack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) { }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateRecordScreenPreview() {
    CreateRecordScreen()
}