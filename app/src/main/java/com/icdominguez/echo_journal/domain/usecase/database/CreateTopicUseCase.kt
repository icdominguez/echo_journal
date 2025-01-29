package com.icdominguez.echo_journal.domain.usecase.database

import com.icdominguez.echo_journal.data.model.TopicEntity
import com.icdominguez.echo_journal.domain.repository.LocalEchoJournalRepository
import javax.inject.Inject

class CreateTopicUseCase @Inject constructor(
    private val localEchoJournalRepository: LocalEchoJournalRepository
) {
    suspend operator fun invoke(
        topic: String,
        default: Boolean = false
    ) {
        val newTopicEntity = TopicEntity(
            name = topic,
            isDefault = default
        )
        localEchoJournalRepository.insertTopic(newTopicEntity)
    }
}