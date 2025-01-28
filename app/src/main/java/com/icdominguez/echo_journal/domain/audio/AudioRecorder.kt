package com.icdominguez.echo_journal.domain.audio

import kotlinx.coroutines.flow.Flow

interface AudioRecorder {
    fun init(path: String)
    fun start(): Flow<Float>
    fun pause()
    fun resume()
    fun stop()
}