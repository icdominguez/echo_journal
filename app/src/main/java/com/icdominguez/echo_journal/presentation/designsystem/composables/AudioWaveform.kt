package com.icdominguez.echo_journal.presentation.designsystem.composables

import android.view.MotionEvent
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.presentation.designsystem.chunkToSize
import com.icdominguez.echo_journal.presentation.designsystem.fillToSize
import com.icdominguez.echo_journal.presentation.designsystem.normalize

private val MinSpikeWidthDp: Dp = 1.dp
private val MaxSpikeWidthDp: Dp = 24.dp
private val MinSpikePaddingDp: Dp = 0.dp
private val MaxSpikePaddingDp: Dp = 12.dp
private val MinSpikeRadiusDp: Dp = 0.dp
private val MaxSpikeRadiusDp: Dp = 12.dp

private const val MinProgress: Float = 0F
private const val MaxProgress: Float = 1F

private const val MinSpikeHeight: Float = 1F
private const val DefaultGraphicsLayerAlpha: Float = 0.99F

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AudioWaveform(
    modifier: Modifier = Modifier,
    style: DrawStyle = Fill,
    waveformBrush: Brush = SolidColor(Color.White),
    progressBrush: Brush = SolidColor(Color.Blue),
    onProgressChangeFinished: (() -> Unit)? = null,
    spikeAnimationSpec: AnimationSpec<Float> = tween(500),
    spikeWidth: Dp = 4.dp,
    spikeRadius: Dp = 2.dp,
    spikePadding: Dp = 1.dp,
    progress: Float = 0F,
    amplitudes: List<Int>,
    onProgressChange: (Float) -> Unit
) {
    val _progress = remember(progress) { progress.coerceIn(MinProgress, MaxProgress) }
    val _spikeWidth = remember(spikeWidth) { spikeWidth.coerceIn(MinSpikeWidthDp, MaxSpikeWidthDp) }
    val _spikePadding = remember(spikePadding) { spikePadding.coerceIn(MinSpikePaddingDp, MaxSpikePaddingDp) }
    val _spikeRadius = remember(spikeRadius) { spikeRadius.coerceIn(MinSpikeRadiusDp, MaxSpikeRadiusDp) }
    val _spikeTotalWidth = remember(spikeWidth, spikePadding) { _spikeWidth + _spikePadding }
    var canvasSize by remember { mutableStateOf(Size(0f, 0f)) }
    var spikes by remember { mutableFloatStateOf(0F) }
    val spikesAmplitudes = remember(amplitudes, spikes) {
        amplitudes.toDrawableAmplitudes(
            spikes = spikes.toInt(),
            minHeight = MinSpikeHeight,
            maxHeight = canvasSize.height.coerceAtLeast(MinSpikeHeight)
        )
    }.map { animateFloatAsState(it, spikeAnimationSpec).value }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { alpha = DefaultGraphicsLayerAlpha }
            .pointerInteropFilter {
                return@pointerInteropFilter when (it.action) {
                    MotionEvent.ACTION_DOWN,
                    MotionEvent.ACTION_MOVE -> {
                        if (it.x in 0F..canvasSize.width) {
                            onProgressChange(it.x / canvasSize.width)
                            true
                        } else false
                    }
                    MotionEvent.ACTION_UP -> {
                        onProgressChangeFinished?.invoke()
                        true
                    }
                    else -> false
                }
            }
            .then(modifier)
    ) {
        canvasSize = size
        spikes = size.width / _spikeTotalWidth.toPx()

        spikesAmplitudes.forEachIndexed { index, amplitude ->
            val spikeX = index * _spikeTotalWidth.toPx()
            if (_progress > 0 && spikeX < _progress * size.width) {
                drawRoundRect(
                    brush = progressBrush,
                    topLeft = Offset(
                        x = spikeX,
                        y = size.height / 2F - amplitude / 2F
                    ),
                    size = Size(
                        width = _spikeWidth.toPx(),
                        height = amplitude
                    ),
                    cornerRadius = CornerRadius(_spikeRadius.toPx(), _spikeRadius.toPx()),
                    style = style,
                )
            }
        }

        spikesAmplitudes.forEachIndexed { index, amplitude ->
            val spikeX = index * _spikeTotalWidth.toPx()
            if (spikeX >= _progress * size.width) {
                drawRoundRect(
                    brush = waveformBrush,
                    topLeft = Offset(
                        x = spikeX,
                        y = size.height / 2F - amplitude / 2F
                    ),
                    size = Size(
                        width = _spikeWidth.toPx(),
                        height = amplitude
                    ),
                    cornerRadius = CornerRadius(_spikeRadius.toPx(), _spikeRadius.toPx()),
                    style = style,
                )
            }
        }
    }
}

private fun List<Int>.toDrawableAmplitudes(
    spikes: Int,
    minHeight: Float,
    maxHeight: Float
): List<Float> {
    val amplitudes = map(Int::toFloat)
    if(amplitudes.isEmpty() || spikes == 0) {
        return List(spikes) { minHeight }
    }

    val interpolatedAmplitudes = mutableListOf<Float>()
    val scaleFactor = amplitudes.size.toFloat() / spikes

    for (i in 0 until spikes) {
        val index = (i * scaleFactor).toInt().coerceIn(0, amplitudes.size - 1)
        interpolatedAmplitudes.add(amplitudes[index])
    }

    return interpolatedAmplitudes.normalize(minHeight, maxHeight)
}