package com.icdominguez.echo_journal.data.timer

import androidx.compose.runtime.mutableLongStateOf
import com.icdominguez.echo_journal.domain.timer.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class AndroidTimer @Inject constructor() : Timer {
    private var isActive = false
    private var coroutineScope = CoroutineScope(Dispatchers.IO)
    private var timeMillis = 0L
    private var lastTimestamp = 0L
    var millis = mutableLongStateOf(0L)

    override fun start() {
        if (isActive) return

        coroutineScope.launch {
            lastTimestamp = System.currentTimeMillis()
            this@AndroidTimer.isActive = true
            while (this@AndroidTimer.isActive) {
                delay(10L)
                timeMillis += System.currentTimeMillis() - lastTimestamp
                lastTimestamp = System.currentTimeMillis()
                millis.longValue = timeMillis
            }
        }
    }

    override fun pause() {
        isActive = false
    }

    override fun reset() {
        isActive = false
    }
}