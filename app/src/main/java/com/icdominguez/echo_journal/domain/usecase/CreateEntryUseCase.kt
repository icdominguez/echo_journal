package com.icdominguez.echo_journal.domain.usecase

import com.icdominguez.echo_journal.data.model.EntryEntity
import com.icdominguez.echo_journal.domain.repository.FileManagerRepository
import com.icdominguez.echo_journal.domain.repository.LocalEchoJournalRepository
import com.icdominguez.echo_journal.presentation.model.Entry
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Moods
import java.time.LocalDateTime
import javax.inject.Inject

class CreateEntryUseCase @Inject constructor(
    private val localEchoJournalRepository: LocalEchoJournalRepository,
    private val fileManagerRepository: FileManagerRepository,
) {
    suspend operator fun invoke(entry: Entry) {
        val internalStoragePath = fileManagerRepository.moveFileFromCacheToInternalStorage(path = entry.filePath)
        internalStoragePath?.let {
            localEchoJournalRepository.insert(
                EntryEntity(
                    mood = entry.mood?.name ?: Moods.SAD.name,
                    title = entry.title,
                    description = entry.description,
                    filePath = it,
                    audioDuration = entry.audioDuration,
                    relatedTopics = entry.topics.joinToString(","),
                    date = LocalDateTime.now()
                )
            )
        }
    }
}