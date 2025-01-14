package com.icdominguez.echo_journal.common

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val DATE_FORMAT = "d MMM yyyy HH:mm:ss"
private const val TIMER_MILLISECONDS_FORMAT = "mm:ss:SS"
private const val MINUTES_SECONDS_FORMAT = "mm:ss"

fun LocalDateTime.toDateFormatted(): String {
    return DateTimeFormatter.ofPattern(DATE_FORMAT).format(this).replace(":", "-")
}

fun Long.millisToFormattedString(): String {
    val localDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
    val formatter = DateTimeFormatter.ofPattern(
        TIMER_MILLISECONDS_FORMAT,
        Locale.getDefault()
    )

    return localDateTime.format(formatter)
}

fun Long.millisToMinutesSecondsFormat(): String {
    val localDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
    val formatter = DateTimeFormatter.ofPattern(
        MINUTES_SECONDS_FORMAT,
        Locale.getDefault()
    )

    return localDateTime.format(formatter)
}

fun LocalDateTime.toHoursAndMinutes(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return this.format(formatter)
}