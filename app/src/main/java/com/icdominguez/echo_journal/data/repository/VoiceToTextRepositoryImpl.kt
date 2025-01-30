package com.icdominguez.echo_journal.data.repository

import com.icdominguez.echo_journal.core.network.EchoJournalNetworkDataSource
import com.icdominguez.echo_journal.domain.repository.VoiceToTextRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class VoiceToTextRepositoryImpl @Inject constructor(
    private val echoJournalDataSource: EchoJournalNetworkDataSource
): VoiceToTextRepository {
    override suspend fun voiceToText(file: File): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(echoJournalDataSource.voiceToText(file).text)
            } catch (throwable: Throwable) {
                Result.failure(throwable)
            }
        }
    }
}