package com.icdominguez.echo_journal.presentation.screens.yourechojournal

import android.Manifest
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.net.Uri
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.common.toTodayYesterdayOrDate
import com.icdominguez.echo_journal.presentation.designsystem.composables.PermissionDialog
import com.icdominguez.echo_journal.presentation.designsystem.theme.FloatingActionButtonFirstColorShadowOne
import com.icdominguez.echo_journal.presentation.designsystem.theme.FloatingActionButtonSecondColorShadowOne
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography
import com.icdominguez.echo_journal.presentation.designsystem.theme.ScreenBg1
import com.icdominguez.echo_journal.presentation.designsystem.theme.ScreenBg2
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.MoodFilterChip
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.RecordAudioModalBottomSheet
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.RecordAudioTextProvider
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.TopicFilterChip
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.YourEchoJournalTopBar
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.entrytimeline.EntryTimeLineItem
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun YourEchoJournalScreen(
    state: YourEchoJournalScreenViewModel.State = YourEchoJournalScreenViewModel.State(),
    uiEvent: (YourEchoJournalScreenViewModel.Event) -> Unit = {},
    navigateToCreateRecordScreen: (String) -> Unit = {},
    navigateToSettingsScreenScreen: () -> Unit = {},
    isLaunchedFromWidget: Boolean = false,
) {
    // region: system variables
    val hasCheckedLaunchedFromWidget = rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val localDensity = LocalDensity.current
    val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java) as Vibrator

    // region: long pressed variables
    var isPressed by remember { mutableStateOf(false) }
    var isInTarget by remember { mutableStateOf(false) }
    val cancelButtonBaseSize = 48.dp
    val cancelButtonScale = if (isInTarget) 1.5f else 1f

    val dragAndDropTarget = remember {
        object : DragAndDropTarget {
            override fun onDrop(event: DragAndDropEvent): Boolean {
                isPressed = false
                if (vibrator.hasVibrator()) {
                    vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 200), -1))
                }
                uiEvent(YourEchoJournalScreenViewModel.Event.OnRecordAudioButtonStopped)
                return false
            }

            override fun onEntered(event: DragAndDropEvent) {
                super.onEntered(event)
                isInTarget = true
            }

            override fun onEnded(event: DragAndDropEvent) {
                super.onEnded(event)
                isPressed = false
                uiEvent(YourEchoJournalScreenViewModel.Event.OnRecordAudioButtonStopped)
                navigateToCreateRecordScreen(state.filePath)
            }

            override fun onExited(event: DragAndDropEvent) {
                super.onExited(event)
                isInTarget = false
            }
        }
    }

    // region: modal bottom sheet audio controllers
    var maxHeightForAudioControls by remember { mutableStateOf(0.dp) }
    var maxWidthForAudioControls by remember { mutableStateOf(0.dp) }

    val heightModifier = Modifier
        .heightIn(min = maxHeightForAudioControls, max = maxHeightForAudioControls)
        .widthIn(min = maxWidthForAudioControls, max = maxWidthForAudioControls)

    // region: permissions
    var hasPermission by remember { mutableStateOf(false) }

    val recordAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                uiEvent(YourEchoJournalScreenViewModel.Event.OnPermissionResult(permission = Manifest.permission.RECORD_AUDIO))
            } else {
                hasPermission = true
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

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (6).dp)
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    if(isPressed) {
                        Image(
                            modifier = Modifier
                                .size(cancelButtonBaseSize * cancelButtonScale)
                                .dragAndDropTarget(
                                    shouldStartDragAndDrop = { event ->
                                        event
                                            .mimeTypes()
                                            .contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                                    },
                                    target = dragAndDropTarget
                                ),
                            painter = painterResource(R.drawable.cancel_record_button),
                            contentDescription = null,
                        )
                    }

                    Box(
                        modifier = heightModifier
                            .onGloballyPositioned { layoutCoordinates ->
                                if(maxHeightForAudioControls == 0.dp) {
                                    maxHeightForAudioControls = with(localDensity) {
                                        layoutCoordinates.size.height.toDp() + 128.dp
                                    }
                                    maxWidthForAudioControls = with(localDensity) {
                                        layoutCoordinates.size.width.toDp() + 128.dp
                                    }
                                }
                            }
                    ) {
                        if (isPressed) {
                            Canvas(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(128.dp)
                            ) {
                                drawCircle(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            FloatingActionButtonFirstColorShadowOne.copy(alpha = 0.1f),
                                            FloatingActionButtonSecondColorShadowOne.copy(alpha = 0.1f)
                                        ),
                                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                                        end = androidx.compose.ui.geometry.Offset(300f, 300f)
                                    ),
                                )
                            }

                            Canvas(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(108.dp)
                            ) {
                                drawCircle(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            FloatingActionButtonFirstColorShadowOne.copy(alpha = 0.2f),
                                            FloatingActionButtonSecondColorShadowOne.copy(alpha = 0.2f)
                                        ),
                                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                                        end = androidx.compose.ui.geometry.Offset(300f, 300f)
                                    ),
                                )
                            }
                        }

                        Image(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .clip(shape = CircleShape)
                                .size(64.dp)
                                .dragAndDropSource(
                                    drawDragDecoration = {}
                                ) {
                                    detectTapGestures(
                                        onTap = {
                                            if(hasPermission) {
                                                uiEvent(YourEchoJournalScreenViewModel.Event.OnCreateEntryFloatingActionButtonClicked)
                                            } else {
                                                recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                            }
                                        },
                                        onLongPress = {
                                            if (hasPermission) {
                                                uiEvent(YourEchoJournalScreenViewModel.Event.OnRecordAudioButtonPressed)
                                                isPressed = true
                                                isInTarget = false

                                                startTransfer(
                                                    DragAndDropTransferData(
                                                        ClipData.newPlainText(
                                                            "delete",
                                                            "remove the current audio"
                                                        )
                                                    )
                                                )
                                            } else {
                                                recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                            }
                                        },
                                    )
                                },
                            painter = painterResource(R.drawable.record),
                            contentDescription = null,
                        )
                    }
                }
            }
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
                        LocalContext.current as ComponentActivity,
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
                if(hasPermission) {
                    recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
                } else {
                    uiEvent(YourEchoJournalScreenViewModel.Event.OnCreateEntryFloatingActionButtonClicked)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun YourEchoJournalScreenPreview() {
    YourEchoJournalScreen()
}