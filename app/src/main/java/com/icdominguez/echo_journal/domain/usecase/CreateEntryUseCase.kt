package com.icdominguez.echo_journal.domain.usecase

import com.icdominguez.echo_journal.data.model.EntryEntity
import com.icdominguez.echo_journal.domain.repository.LocalEchoJournalRepository
import com.icdominguez.echo_journal.presentation.model.Entry
import java.time.LocalDateTime
import javax.inject.Inject

class CreateEntryUseCase @Inject constructor(
    private val localEchoJournalRepository: LocalEchoJournalRepository,
) {
    suspend operator fun invoke(entry: Entry) {
        localEchoJournalRepository.insert(
            EntryEntity(
                mood = entry.mood.name,
                title = entry.title,
                description = entry.description,
                filePath = entry.filePath,
                audioDuration = entry.audioDuration,
                relatedTopics = entry.topics.joinToString(","),
                date = LocalDateTime.now()
            )
        )
    }
}