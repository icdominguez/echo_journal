package com.icdominguez.echo_journal.data.repository

import android.content.Context
import android.util.Log
import com.icdominguez.echo_journal.common.toDateFormatted
import com.icdominguez.echo_journal.domain.repository.FileManagerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject

class FileManagerRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
): FileManagerRepository {

    private companion object {
        private const val AUDIOS_FOLDER = "audios"
        private const val AUDIO_EXTENSION = ".m4a"
        private const val AMPLITUDES_FILE_NAME = "amplitudes.txt"
    }

    override fun createAudioFile(): String {
        val folder = File(context.cacheDir, AUDIOS_FOLDER)

        if(!folder.exists()) {
            folder.mkdirs()
        }

        return File(folder, "${LocalDateTime.now().toDateFormatted()}$AUDIO_EXTENSION").path
    }

    override fun deleteFile(path: String) {
        val file = File(path)
        if(File(path).exists() && file.isFile) {
            file.delete()
        }
    }

    override fun moveFileFromCacheToInternalStorage(path: String): String? {
        val cacheFile = File(path)

        return try {
            val internalStorageFolder = File(context.filesDir, AUDIOS_FOLDER)

            if(!internalStorageFolder.exists()) {
                internalStorageFolder.mkdirs()
            }

            val internalStorageFile = File(internalStorageFolder, cacheFile.path.split("/").last())

            cacheFile.inputStream().use { input ->
                internalStorageFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            if (cacheFile.exists()) {
                cacheFile.delete()
            }

            internalStorageFile.path
        } catch(e: IOException) {
            e.printStackTrace()
            null
        }
    }

    override fun createAmplitudesFile(amplitudes: List<Float>) {
        val file = File(context.cacheDir, AMPLITUDES_FILE_NAME)
        file.writeText(amplitudes.joinToString(","))
    }

    override fun retrieveAmplitudes(): List<Float> {
        try {
            val file = File(context.cacheDir, AMPLITUDES_FILE_NAME)

            val amplitudes = file.readText()
                .split(",")
                .mapNotNull { it.takeIf { it.isNotEmpty() }?.trim()?.toFloat() }

            return amplitudes
        } catch (e: Exception) {
            Log.e("icd", "Error trying to read amplitudes from file with stacktrace -> $e")
            return emptyList()
        }
    }

    override fun cleanAmplitudesFile() {
        val file = File(context.cacheDir, AMPLITUDES_FILE_NAME)
        file.writeText("")
    }
}