package com.icdominguez.echo_journal.presentation.screens.yourechojournal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.presentation.screens.FakeData
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.CreateEntryFloatingActionButton
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.entrytimeline.EntryTimeLineItem
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

             LazyColumn(
                 verticalArrangement = Arrangement.spacedBy(0.dp)
             ) {
                 itemsIndexed(FakeData.timelineEntries) { index, entry ->
                     EntryTimeLineItem(
                         entry = entry,
                         index = index,
                         lastIndex = FakeData.timelineEntries.lastIndex
                     )
                 }
             }
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