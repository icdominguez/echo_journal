package com.icdominguez.echo_journal.domain.usecase

import com.icdominguez.echo_journal.domain.repository.FileManagerRepository
import javax.inject.Inject

class DeleteFileUseCase @Inject constructor(
    private val fileManagerRepository: FileManagerRepository,
) {
    operator fun invoke(path: String) =
        fileManagerRepository.deleteFile(path)
}