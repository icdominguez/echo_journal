package com.icdominguez.echo_journal.domain.repository

interface FileManagerRepository {
    fun createAudioFile(): String
    fun deleteFile(path: String)
    fun moveFileFromCacheToInternalStorage(path: String): String?
    fun createAmplitudesFile(amplitudes: List<Float>)
    fun retrieveAmplitudes(): List<Float>
    fun cleanAmplitudesFile()
}