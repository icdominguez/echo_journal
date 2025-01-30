package com.icdominguez.echo_journal.domain.repository

import java.io.File

interface VoiceToTextRepository {
    suspend fun voiceToText(file: File): Result<String>
}