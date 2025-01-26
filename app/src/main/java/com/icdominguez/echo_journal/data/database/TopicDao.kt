package com.icdominguez.echo_journal.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.icdominguez.echo_journal.data.model.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Query("SELECT * FROM topics")
    fun getAll(): Flow<List<TopicEntity>>

    @Insert
    suspend fun insertTopic(vararg topicEntity: TopicEntity)

    @Query("UPDATE topics SET isDefault = :isDefault WHERE name = :name")
    suspend fun updateTopic(name: String, isDefault: Boolean)
}