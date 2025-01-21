package com.icdominguez.echo_journal.domain.repository

import com.icdominguez.echo_journal.data.model.EntryEntity
import com.icdominguez.echo_journal.data.model.TopicEntity
import kotlinx.coroutines.flow.Flow

interface LocalEchoJournalRepository {
    fun getAllEntries(): Flow<List<EntryEntity>>
    fun getAllTopics(): Flow<List<TopicEntity>>
    suspend fun insert(entryEntity: EntryEntity)
    suspend fun insertTopic(topicEntity: TopicEntity)
    suspend fun updateTopic(topicEntity: TopicEntity)
}