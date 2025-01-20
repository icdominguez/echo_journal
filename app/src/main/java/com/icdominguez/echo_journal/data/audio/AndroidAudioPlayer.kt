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
            return -1
        }
    }

    override fun play(path: String) {
        Log.i("icd", "android Audio Player play")
        player?.reset()
        player?.setDataSource(path)
        player?.prepare()
        player?.start()
    }

    override fun playFrom(path: String, millis: Int) {
        Log.i("icd", "Android Audio Player playFrom")
        player?.reset()
        player?.setDataSource(path)
        player?.prepare()
        player?.start()
        player?.seekTo(millis.toLong(), MediaPlayer.SEEK_CLOSEST_SYNC)
    }

    override fun pause(): Int {
        Log.i("icd", "android Audio Player pause")
        var currentPosition: Int? = null
        player?.let {
            if(it.isPlaying) {
                it.pause()
            }
            currentPosition = player?.currentPosition
        }
        return currentPosition ?: 0
    }

    override fun stop() {
        Log.i("icd", "android Audio Player stop")
        player?.stop()
    }

    override fun reset() {
        Log.i("icd", "android Audio Player reset")
        player?.release()
        player = null
    }
}