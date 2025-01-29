package com.icdominguez.echo_journal.domain.usecase.database

import com.icdominguez.echo_journal.data.model.toTopic
import com.icdominguez.echo_journal.domain.repository.LocalEchoJournalRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllTopicsUseCase @Inject constructor(
    private val localEchoJournalRepository: LocalEchoJournalRepository
) {
    operator fun invoke() =
        localEchoJournalRepository.getAllTopics().map { topicList ->
            topicList.map { topic ->
                topic.toTopic()
            }
        }
}