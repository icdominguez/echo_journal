package com.icdominguez.echo_journal.presentation

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toHoursAndMinutes(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return this.format(formatter)
}