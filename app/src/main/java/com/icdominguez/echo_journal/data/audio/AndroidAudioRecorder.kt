package com.icdominguez.echo_journal.data.audio

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import com.icdominguez.echo_journal.domain.audio.AudioRecorder
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class AndroidAudioRecorder @Inject constructor(
    @ApplicationContext private val context: Context,
) : AudioRecorder {

    companion object {
        private const val SAMPLING_RATE = 44100
        private const val BIT_RATE = 128000
        private const val SMOOTHING_FACTOR = 0.5f
        private const val HISTORY_SIZE = 5
    }

    private var recorder: MediaRecorder? = null
    private var isPaused: Boolean = false
    private var amplitudeJob: Job? = null

    private val amplitudeFlow = MutableStateFlow(0f)
    private var smoothedAmplitude = 0f

    private val amplitudeHistory = mutableListOf<Float>()
    private var dynamicMaxAmplitude = Short.MAX_VALUE.toFloat()

    override fun init(path: String) {
        val newRecorder = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder()

        newRecorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(File(path)).fd)
            setAudioSamplingRate(SAMPLING_RATE)
            setAudioEncodingBitRate(BIT_RATE)

            prepare()
            recorder = this
        }
    }

    override fun start(): StateFlow<Float> {
        recorder?.start()

        amplitudeJob?.cancel()
        amplitudeJob = CoroutineScope(Dispatchers.IO).launch {
            while (isActive && !isPaused) {
                val amplitude = recorder?.maxAmplitude?.toFloat() ?: 0f
                val amplitudeSmoothed = smoothAmplitude(amplitude)
                val scaled = scaleAmplitude(amplitudeSmoothed)

                if (scaled > 0) {
                    amplitudeFlow.value = scaled
                }
                delay(100)
            }
        }

        return amplitudeFlow
    }

    override fun pause() {
        recorder?.pause()
        isPaused = true
    }

    override fun resume() {
        recorder?.resume()
        isPaused = false
    }

    override fun stop() {
        amplitudeJob?.cancel()
        recorder?.stop()
        recorder?.reset()
    }

    private fun smoothAmplitude(newAmplitude: Float): Float {
        smoothedAmplitude = (SMOOTHING_FACTOR * newAmplitude) + ((1 - SMOOTHING_FACTOR) * smoothedAmplitude)
        return smoothedAmplitude
    }

    private fun scaleAmplitude(newAmplitude: Float): Float {
        amplitudeHistory.add(newAmplitude)
        if(amplitudeHistory.size > HISTORY_SIZE) {
            amplitudeHistory.removeAt(0)
        }

        val newMax = amplitudeHistory.maxOrNull() ?: 1f
        dynamicMaxAmplitude = maxOf(dynamicMaxAmplitude * 0.95f, newMax)

        return ((newAmplitude / dynamicMaxAmplitude) * 100f).coerceAtLeast(5f)
    }
}