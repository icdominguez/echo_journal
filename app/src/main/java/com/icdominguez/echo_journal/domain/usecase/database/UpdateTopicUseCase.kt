package com.icdominguez.echo_journal.domain.usecase.database

import com.icdominguez.echo_journal.data.model.TopicEntity
import com.icdominguez.echo_journal.domain.repository.LocalEchoJournalRepository
import com.icdominguez.echo_journal.presentation.model.Topic
import javax.inject.Inject

class UpdateTopicUseCase @Inject constructor(
    private val localEchoJournalRepository: LocalEchoJournalRepository,
) {
    suspend operator fun invoke(topic: Topic) {
        localEchoJournalRepository.updateTopic(
            topicEntity = TopicEntity(
                name = topic.name,
                isDefault = topic.isDefault,
            )
        )
    }
}