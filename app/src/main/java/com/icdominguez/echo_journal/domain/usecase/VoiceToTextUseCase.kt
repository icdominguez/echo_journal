package com.icdominguez.echo_journal.domain.usecase

import com.icdominguez.echo_journal.domain.repository.VoiceToTextRepository
import java.io.File
import javax.inject.Inject

class VoiceToTextUseCase @Inject constructor(
    private val voiceToTextRepository: VoiceToTextRepository
){
    suspend operator fun invoke(file: File): Result<String> =
        voiceToTextRepository.voiceToText(file)
}