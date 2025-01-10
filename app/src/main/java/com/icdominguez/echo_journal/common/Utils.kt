package com.icdominguez.echo_journal.common

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val DATE_FORMAT = "dd-MM-yyyy HH:mm:ss"
private const val TIMER_MILLISECONDS_FORMAT = "mm:ss:SS"

fun LocalDateTime.toDateFormatted(): String {
    return DateTimeFormatter.ofPattern(DATE_FORMAT).format(this)
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