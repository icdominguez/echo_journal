package com.icdominguez.echo_journal.domain.audio

interface AudioRecorder {
    fun init(path: String)
    fun start()
    fun pause()
    fun resume()
    fun stop()
}