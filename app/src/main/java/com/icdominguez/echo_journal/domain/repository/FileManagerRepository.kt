package com.icdominguez.echo_journal.domain.repository

interface FileManagerRepository {
    fun createFile(): String
    fun deleteFile(path: String)
    fun moveFileFromCacheToInternalStorage(path: String): String?
}