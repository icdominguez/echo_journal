package com.icdominguez.echo_journal.data.audio

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.icdominguez.echo_journal.domain.audio.AudioPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class AndroidAudioPlayer @Inject constructor(
    @ApplicationContext private val context: Context,
): AudioPlayer {
    private var player: MediaPlayer? = null

    override fun init(audioName: String): Int {
        try {
            MediaPlayer.create(
                context,
                File(Uri.parse(audioName).path!!).toUri()
            ).apply {
                player = this
                return player?.duration ?: 0
            }
        } catch (e: Exception) {
            Log.i("icd", "Couldn't create an instance of audio player")
            return -1
        }
    }

    override fun play() {
        player?.start()
    }

    override fun playFrom(millis: Int) {
        player?.let {
            it.apply {
                seekTo(millis.toLong(), MediaPlayer.SEEK_CLOSEST_SYNC)
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

    override fun reset() {
        player?.stop()
        player?.release()
        player = null
    }

}