package com.icdominguez.echo_journal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.icdominguez.echo_journal.presentation.model.Entry
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Moods
import java.time.LocalDateTime

@Entity(tableName = "entries")
data class EntryEntity(
    @PrimaryKey(autoGenerate = true)
    val entryId: Int = 0,
    val mood: String,
    val title: String,
    val description: String,
    val filePath: String,
    val relatedTopics: String,
    val audioDuration: Int,
    val date: LocalDateTime = LocalDateTime.now(),
    val amplitudes: String,
)

fun EntryEntity.toEntry() = Entry(
    entryId = entryId,
    mood = Moods.allMods.find { it.name == mood } ?: Moods.NEUTRAL,
    title = title,
    description = description,
    filePath = filePath,
    date = date,
    topics = relatedTopics.takeIf { it.isNotEmpty() }?.split(",") ?: emptyList(),
    audioDuration = audioDuration,
    amplitudes = amplitudes.split(",").map { it.toFloat() },
)