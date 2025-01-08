package com.icdominguez.echo_journal.data.audio

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import com.icdominguez.echo_journal.domain.audio.AudioRecorder
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class AndroidAudioRecorder @Inject constructor(
    @ApplicationContext private val context: Context,
) : AudioRecorder {

    companion object {
        private const val SAMPLING_RATE = 44100
        private const val BIT_RATE = 320000
    }

    private var recorder: MediaRecorder? = null
    var isPaused: Boolean = false

    private fun createRecorder(): MediaRecorder {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder()
    }

    override fun start(path: String) {
        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(File(path)).fd)
            setAudioSamplingRate(SAMPLING_RATE)
            setAudioEncodingBitRate(BIT_RATE)

            prepare()
            start()

            recorder = this
        }
    }

    override fun pause() {
        recorder?.pause()
        isPaused = true
    }

    override fun resume() {
        recorder?.resume()
    }

    override fun stop() {
        recorder?.stop()
        recorder?.reset()
    }
}