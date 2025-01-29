package com.icdominguez.echo_journal.presentation.model

import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Mood
import java.time.LocalDateTime

data class Entry(
    val entryId: Int = 0,
    val mood: Mood ?= null,
    val title: String = "",
    val description: String = "",
    val filePath: String = "",
    val date: LocalDateTime = LocalDateTime.now(),
    val topics: List<String> = emptyList(),
    val audioDuration: Int = 0,
    val audioProgress: Int = 0,
    val isPlaying: Boolean = false,
    val amplitudes: List<Float> = emptyList(),
)
