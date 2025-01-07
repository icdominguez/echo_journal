package com.icdominguez.echo_journal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "entries")
data class EntryEntity(
    @PrimaryKey(autoGenerate = true)
    val entryId: Int = 0,
    val mood: String,
    val title: String,
    val description: String,
    val filePath: String,
    val date: LocalDateTime = LocalDateTime.now(),
)