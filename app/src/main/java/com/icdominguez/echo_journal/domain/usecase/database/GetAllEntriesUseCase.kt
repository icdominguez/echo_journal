package com.icdominguez.echo_journal.domain.usecase.database

import com.icdominguez.echo_journal.data.model.toEntry
import com.icdominguez.echo_journal.domain.repository.LocalEchoJournalRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllEntriesUseCase @Inject constructor(
    private val localEchoJournalRepository: LocalEchoJournalRepository
) {
    operator fun invoke() =
        localEchoJournalRepository.getAllEntries().map { it.map { entryEntity -> entryEntity.toEntry() } }
}