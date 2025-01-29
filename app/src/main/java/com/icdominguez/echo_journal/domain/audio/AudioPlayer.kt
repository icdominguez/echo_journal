package com.icdominguez.echo_journal.domain.audio

import kotlinx.coroutines.flow.Flow

interface AudioPlayer {
    fun init(audioName: String): Int
    fun play(path: String)
    fun playFrom(path: String, millis: Int)
    fun pause(): Int
    fun stop()
    fun reset()
    fun listener(): Flow<Long>
}