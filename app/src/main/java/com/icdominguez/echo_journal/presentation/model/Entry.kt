package com.icdominguez.echo_journal.presentation.model

import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Mood
import java.time.LocalDateTime

data class Entry(
    val entryId: Int,
    val mood: Mood,
    val title: String,
    val description: String,
    val filePath: String,
    val date: LocalDateTime = LocalDateTime.now(),
    val topics: List<String>
)
