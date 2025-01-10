package com.icdominguez.echo_journal.data.audio

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import com.icdominguez.echo_journal.domain.audio.AudioPlayer
import java.io.File

class AndroidAudioPlayer(
    private val context: Context,
): AudioPlayer {
    private var player: MediaPlayer? = null

    override fun init(file: File): Int {
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            return player?.duration ?: 0
        }
    }

    override fun play() {
        player?.start()
    }

    override fun playFrom(millis: Long) {
        player?.let {
            it.apply {
                seekTo(millis, MediaPlayer.SEEK_CLOSEST_SYNC)
            }
        }
    }

    override fun pause() {
        player?.let {
            if(it.isPlaying) {
                it.pause()
            }
        }
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }

}