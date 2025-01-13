package com.icdominguez.echo_journal.domain.repository

interface FileManagerRepository {
    fun createFile(inCache: Boolean = true): String
    fun deleteFile(path: String)
}