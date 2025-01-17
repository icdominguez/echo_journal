package com.icdominguez.echo_journal.domain.usecase

import com.icdominguez.echo_journal.data.model.TopicEntity
import com.icdominguez.echo_journal.domain.repository.LocalEchoJournalRepository
import javax.inject.Inject

class CreateTopicUseCase @Inject constructor(
    private val localEchoJournalRepository: LocalEchoJournalRepository
) {
    suspend operator fun invoke(topic: String) {
        localEchoJournalRepository.insertTopic(TopicEntity(name = topic))
    }
}