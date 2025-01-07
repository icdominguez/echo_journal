package com.icdominguez.echo_journal.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.icdominguez.echo_journal.data.model.EntryEntity

@Database(entities = [EntryEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class EchoJournalDatabase: RoomDatabase() {
    abstract fun entryDao(): EntryDao
}