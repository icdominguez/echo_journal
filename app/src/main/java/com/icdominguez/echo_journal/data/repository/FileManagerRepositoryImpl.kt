package com.icdominguez.echo_journal.data.repository

import android.content.Context
import com.icdominguez.echo_journal.common.toDateFormatted
import com.icdominguez.echo_journal.domain.repository.FileManagerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.time.LocalDateTime
import javax.inject.Inject

class FileManagerRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
): FileManagerRepository {

    private companion object {
        private const val AUDIOS_FOLDER = "audios"
        private const val AUDIO_EXTENSION = ".mp3"
    }

    override fun createFile(inCache: Boolean): String {
        val folder = File(if(inCache) context.cacheDir else context.filesDir, AUDIOS_FOLDER)

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
}