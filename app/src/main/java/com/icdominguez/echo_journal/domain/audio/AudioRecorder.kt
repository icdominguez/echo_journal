package com.icdominguez.echo_journal.domain.audio

interface AudioRecorder {
    fun start(path: String)
    fun pause()
    fun resume()
    fun stop()
}