package com.icdominguez.echo_journal.domain

import com.icdominguez.echo_journal.domain.repository.FileManagerRepository
import javax.inject.Inject

class CreateFileUseCase @Inject constructor(
    private val fileManagerRepository: FileManagerRepository,
){
    operator fun invoke() = fileManagerRepository.createFile()
}