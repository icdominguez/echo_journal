package com.icdominguez.echo_journal.data.repository

import com.icdominguez.echo_journal.data.database.EntryDao
import com.icdominguez.echo_journal.data.database.TopicDao
import com.icdominguez.echo_journal.data.model.EntryEntity
import com.icdominguez.echo_journal.data.model.TopicEntity
import com.icdominguez.echo_journal.domain.repository.LocalEchoJournalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalEchoJournalRepositoryImpl @Inject constructor(
    private val entryDao: EntryDao,
    private val topicDao: TopicDao,
): LocalEchoJournalRepository {
    override fun getAllEntries() = flow {
        entryDao.getAll().collect { entry ->
            emit(entry)
        }
    }

    override fun getAllTopics(): Flow<List<TopicEntity>> = flow {
        topicDao.getAll().collect { topic ->
            emit(topic)
        }
    }

    override suspend fun insert(entryEntity: EntryEntity) {
        return withContext(Dispatchers.IO) {
            entryDao.insertEntry(entryEntity)
        }
    }

    override suspend fun insertTopic(topicEntity: TopicEntity) {
        return withContext(Dispatchers.IO) {
            topicDao.insertTopic(topicEntity)
        }
    }
}