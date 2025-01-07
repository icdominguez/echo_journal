package com.icdominguez.echo_journal.domain.repository

import com.icdominguez.echo_journal.data.model.EntryEntity
import kotlinx.coroutines.flow.Flow

interface LocalEchoJournalRepository {
    fun getAll(): Flow<List<EntryEntity>>
    suspend fun insert(entryEntity: EntryEntity)
}