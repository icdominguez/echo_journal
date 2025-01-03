package com.icdominguez.echo_journal.data.repository

import com.icdominguez.echo_journal.data.model.EntryEntity
import com.icdominguez.echo_journal.domain.repository.LocalEchoJournalRepository
import kotlinx.coroutines.flow.Flow

class LocalEchoJournalRepositoryImpl : LocalEchoJournalRepository {
    override fun getAll(): Flow<List<EntryEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(entryEntity: EntryEntity) {
        TODO("Not yet implemented")
    }
}