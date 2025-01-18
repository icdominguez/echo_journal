package com.icdominguez.echo_journal.data.repository

import android.content.Context
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
    }

    override fun createFile(): String {
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
}