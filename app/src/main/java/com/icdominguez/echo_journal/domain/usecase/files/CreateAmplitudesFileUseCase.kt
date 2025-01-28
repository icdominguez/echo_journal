package com.icdominguez.echo_journal.domain.usecase.files

import com.icdominguez.echo_journal.domain.repository.FileManagerRepository
import javax.inject.Inject

class CreateAmplitudesFileUseCase @Inject constructor(
    private val fileManagerRepository: FileManagerRepository
) {
    operator fun invoke(audioAmplitudes: List<Float>) {
        fileManagerRepository.createAmplitudesFile(audioAmplitudes)
    }
}