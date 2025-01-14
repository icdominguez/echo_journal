package com.icdominguez.echo_journal.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.icdominguez.echo_journal.data.model.EntryEntity
import com.icdominguez.echo_journal.data.model.TopicEntity

@Database(entities = [EntryEntity::class, TopicEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class EchoJournalDatabase: RoomDatabase() {
    abstract fun entryDao(): EntryDao
    abstract fun topicDao(): TopicDao
}