package com.icdominguez.echo_journal.core.network

import com.icdominguez.echo_journal.core.network.model.VoiceToTextResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

const val FILE_NAME = "audio/m4a"
const val FORM_DATA_NAME = "file"

class EchoJournalNetwork @Inject constructor(
    private val networkApi: EchoJournalNetworkApi
) : EchoJournalNetworkDataSource {
    override suspend fun voiceToText(file: File): VoiceToTextResponse =
        networkApi.voiceToText(file = MultipartBody.Part.createFormData(FORM_DATA_NAME, file.name, file.asRequestBody(
            FILE_NAME.toMediaTypeOrNull())))
}