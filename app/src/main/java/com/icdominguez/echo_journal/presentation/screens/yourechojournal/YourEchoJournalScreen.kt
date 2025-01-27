package com.icdominguez.echo_journal.presentation.screens.yourechojournal

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.common.toTodayYesterdayOrDate
import com.icdominguez.echo_journal.presentation.designsystem.composables.PermissionDialog
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography
import com.icdominguez.echo_journal.presentation.designsystem.theme.ScreenBg1
import com.icdominguez.echo_journal.presentation.designsystem.theme.ScreenBg2
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.CreateEntryFloatingActionButton
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.MoodFilterChip
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.RecordAudioModalBottomSheet
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.RecordAudioTextProvider
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.TopicFilterChip
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.YourEchoJournalTopBar
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.entrytimeline.EntryTimeLineItem
import java.time.LocalDate

@Composable
fun YourEchoJournalScreen(
    state: YourEchoJournalScreenViewModel.State = YourEchoJournalScreenViewModel.State(),
    uiEvent: (YourEchoJournalScreenViewModel.Event) -> Unit = {},
    navigateToCreateRecordScreen: (String) -> Unit = {},
    navigateToSettingsScreenScreen: () -> Unit = {},
    isLaunchedFromWidget: Boolean = false,
) {
    val hasCheckedLaunchedFromWidget = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val recordAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                uiEvent(YourEchoJournalScreenViewModel.Event.OnPermissionResult(permission = Manifest.permission.RECORD_AUDIO))
            } else {
                uiEvent(YourEchoJournalScreenViewModel.Event.OnCreateEntryFloatingActionButtonClicked)
            }
        }
    )

    Scaffold(
        topBar = {
            YourEchoJournalTopBar(
                modifier = Modifier.background(Color.Transparent),
                onSettingsIconClicked = { navigateToSettingsScreenScreen() }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush
                        .linearGradient(colors = listOf(ScreenBg1, ScreenBg2))
                )
                .padding(innerPadding),
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                LazyRow (
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if(state.entryList.isNotEmpty()) {
                        item {
                            MoodFilterChip(
                                selectedMoodList = state.selectedMoodList,
                                onCloseButtonClicked = { uiEvent(YourEchoJournalScreenViewModel.Event.OnMoodsChipCloseButtonClicked) },
                                onMoodItemClicked = { mood -> uiEvent(YourEchoJournalScreenViewModel.Event.OnMoodItemClicked(mood = mood)) }
                            )
                        }
                    }

                    if(state.topicsList.isNotEmpty() && state.entryList.isNotEmpty()) {
                        item {
                            TopicFilterChip(
                                topics = state.topicsList,
                                selectedTopicsList = state.selectedTopicList.sortedBy { it.name },
                                onCloseButtonClicked = { uiEvent(YourEchoJournalScreenViewModel.Event.OnTopicsChipCloseButtonClicked) },
                                onTopicItemClicked = { topic -> uiEvent(YourEchoJournalScreenViewModel.Event.OnTopicItemClicked(topic = topic)) }
                            )
                        }
                    }
                }


                if (state.filteredEntryList.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                    ) {
                        val mappedList = state.filteredEntryList.sortedByDescending { it.date }.groupBy { it.date.toLocalDate() }

                        mappedList.map { map ->
                            Text(
                                modifier = Modifier
                                    .padding(bottom = 16.dp),
                                text = (map.key as LocalDate).toTodayYesterdayOrDate().uppercase(),
                                style = LocalEchoJournalTypography.current.labelMedium
                            )

                            map.value.map { entry ->
                                EntryTimeLineItem(
                                    entry = entry,
                                    index = map.value.indexOf(entry),
                                    lastIndex = map.value.lastIndex,
                                    onAudioPlayerStarted = { uiEvent(YourEchoJournalScreenViewModel.Event.OnAudioPlayerStarted(it)) },
                                    onAudioPlayerPaused = { uiEvent(YourEchoJournalScreenViewModel.Event.OnAudioPlayerPaused(it)) },
                                    onSliderValueChanged = { uiEvent(YourEchoJournalScreenViewModel.Event.OnSliderValueChanged(it)) },
                                    onAudioPlayerEnded = { uiEvent(YourEchoJournalScreenViewModel.Event.OnAudioPlayerEnded(it)) },
                                )
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.no_entries),
                            contentDescription = null
                        )

                        Text(
                            text = stringResource(R.string.no_entries),
                            style = LocalEchoJournalTypography.current.headlineMedium
                        )

                        Text(
                            text = stringResource(R.string.start_recording),
                            style = LocalEchoJournalTypography.current.bodyMedium
                        )
                    }
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
                     uiEvent(YourEchoJournalScreenViewModel.Event.OnRecordAudioConfirmed)
                     navigateToCreateRecordScreen(state.filePath)
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

        if(!hasCheckedLaunchedFromWidget.value) {
            hasCheckedLaunchedFromWidget.value = true
            if(isLaunchedFromWidget) {
                recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun YourEchoJournalScreenPreview() {
    YourEchoJournalScreen()
}