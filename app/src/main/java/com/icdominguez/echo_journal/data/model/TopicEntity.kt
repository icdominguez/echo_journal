package com.icdominguez.echo_journal.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.icdominguez.echo_journal.presentation.model.Topic

@Entity(tableName = "topics")
data class TopicEntity(
    @PrimaryKey
    val name: String,
    val isDefault: Boolean = false,
)

fun TopicEntity.toTopic() = Topic(
    name = this.name,
    isDefault = this.isDefault,
)