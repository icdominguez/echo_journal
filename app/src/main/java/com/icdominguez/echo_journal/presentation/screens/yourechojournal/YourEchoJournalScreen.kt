package com.icdominguez.echo_journal.presentation.screens.yourechojournal

import androidx.compose.foundation.layout.Arrangement
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.presentation.screens.FakeData
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.icdominguez.echo_journal.presentation.designsystem.composables.PermissionDialog
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.CreateEntryFloatingActionButton
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.entrytimeline.EntryTimeLineItem
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.RecordAudioModalBottomSheet
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.RecordAudioTextProvider
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.YourEchoJournalTopBar

@Composable
fun YourEchoJournalScreen(
    state: YourEchoJournalScreenViewModel.State = YourEchoJournalScreenViewModel.State(),
    uiEvent: (YourEchoJournalScreenViewModel.Event) -> Unit = {},
    navigateToCreateRecordScreen: () -> Unit = {},
) {
    val context = LocalContext.current

    val recordAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if(!isGranted) {
                uiEvent(YourEchoJournalScreenViewModel.Event.OnPermissionResult(permission = Manifest.permission.RECORD_AUDIO))
            } else {
                uiEvent(YourEchoJournalScreenViewModel.Event.OnCreateEntryFloatingActionButtonClicked)
            }
        }
    )

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
                 onClick = {
                     recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
                 }
             )
         }

         if(state.showRecordModalBottomSheet) {
             RecordAudioModalBottomSheet(
                 onDismissRequest = { uiEvent(YourEchoJournalScreenViewModel.Event.OnRecordAudioModalSheetDismissed) },
                 navigateToNewEntry = {
                     navigateToCreateRecordScreen()
                     uiEvent(YourEchoJournalScreenViewModel.Event.OnRecordAudioConfirmed)
                 },
                 onRecordAudioPaused = { uiEvent(YourEchoJournalScreenViewModel.Event.OnRecordAudioPaused) },
                 onRecordAudioResumed = { uiEvent(YourEchoJournalScreenViewModel.Event.OnRecordAudioResumed) },
             )
         }

         state.visiblePermissionDialogQueue
             .forEach { permission ->
                 PermissionDialog(
                     permissionTextProvider = when (permission) {
                         Manifest.permission.RECORD_AUDIO -> {
                             RecordAudioTextProvider(LocalContext.current)
                         }
                         else -> return@forEach
                     },
                     isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                         LocalContext.current as androidx.activity.ComponentActivity,
                         permission
                     ),
                     onDismissClicked = { uiEvent(YourEchoJournalScreenViewModel.Event.OnPermissionDialogDismissed) },
                     onOkClicked = {
                         uiEvent(YourEchoJournalScreenViewModel.Event.OnPermissionDialogDismissed)
                         recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
                     },
                     onGoToAppSettingsClick = {
                         val detailsSettingsIntent = Intent(
                             Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                             Uri.fromParts("package", context.packageName, null)
                         )
                         context.startActivity(detailsSettingsIntent)
                     }
                 )
             }
     }
}

@Preview(showBackground = true)
@Composable
fun YourEchoJournalScreenPreview() {
    YourEchoJournalScreen()
}