package com.icdominguez.echo_journal.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.icdominguez.echo_journal.data.model.EntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Query("SELECT * FROM entries")
    fun getAll(): Flow<List<EntryEntity>>

    @Insert
    suspend fun insertEntry(vararg entryEntity: EntryEntity)
}