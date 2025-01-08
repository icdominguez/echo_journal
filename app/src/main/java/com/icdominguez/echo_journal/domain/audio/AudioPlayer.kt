package com.icdominguez.echo_journal.domain.audio

import java.io.File

interface AudioPlayer {
    fun init(file: File): Int
    fun play()
    fun playFrom(millis: Long)
    fun pause()
    fun stop()
}