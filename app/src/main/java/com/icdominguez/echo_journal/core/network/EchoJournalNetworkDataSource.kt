package com.icdominguez.echo_journal.core.network

import com.icdominguez.echo_journal.core.network.model.VoiceToTextResponse
import java.io.File

interface EchoJournalNetworkDataSource {

    suspend fun voiceToText(file: File): VoiceToTextResponse
}