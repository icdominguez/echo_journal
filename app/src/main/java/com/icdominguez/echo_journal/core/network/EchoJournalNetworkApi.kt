package com.icdominguez.echo_journal.core.network

import com.icdominguez.echo_journal.BuildConfig
import com.icdominguez.echo_journal.core.network.model.VoiceToTextResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface EchoJournalNetworkApi {

    @POST("audio/transcriptions")
    @Multipart
    suspend fun voiceToText(
        @Header("Authorization") apiKey: String = "Bearer ${BuildConfig.API_KEY}",
        @Part file: MultipartBody.Part,
        @Part("model") model: RequestBody = "whisper-large-v3".toRequestBody("text/plain".toMediaTypeOrNull())
    ): VoiceToTextResponse
}