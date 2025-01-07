package com.icdominguez.echo_journal.presentation.screens.yourechojournal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.CreateEntryFloatingActionButton
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.YourEchoJournalTopBar

@Composable
fun YourEchoJournalScreen(
    state: YourEchoJournalScreenViewModel.State = YourEchoJournalScreenViewModel.State(),
    uiEvent: (YourEchoJournalScreenViewModel.Event) -> Unit = {},
    navigateToCreateRecordScreen: () -> Unit = {},
) {
     Scaffold(
         topBar = { YourEchoJournalTopBar() }
     ) { innerPadding ->
         Box(
             modifier = Modifier
                 .fillMaxSize()
                 .padding(innerPadding),
         ) {
             CreateEntryFloatingActionButton(
                 modifier = Modifier
                    .align(Alignment.BottomEnd),
                 onClick = { navigateToCreateRecordScreen() }
             )
         }
     }
}

@Preview(showBackground = true)
@Composable
fun YourEchoJournalScreenPreview() {
    YourEchoJournalScreen()
}