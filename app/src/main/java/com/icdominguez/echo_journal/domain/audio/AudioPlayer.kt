package com.icdominguez.echo_journal.domain.audio

interface AudioPlayer {
    fun init(audioName: String): Int
    fun play()
    fun playFrom(millis: Int)
    fun pause()
    fun reset()
}