package com.icdominguez.echo_journal.core.di

import com.icdominguez.echo_journal.BuildConfig
import com.icdominguez.echo_journal.core.network.EchoJournalNetworkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val VOICE_TO_TEXT_BASE_URL = "https://api.groq.com/openai/v1/"

    private fun okHttpClient(): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(10, TimeUnit.SECONDS)
        okHttpClient.readTimeout(30, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(30, TimeUnit.SECONDS)

        when {
            BuildConfig.DEBUG -> {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                okHttpClient.addInterceptor(logging)
            }
        }

        return okHttpClient
    }

    @Singleton
    @Provides
    fun providesAPI(): EchoJournalNetworkApi {
        val httpClient = okHttpClient()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(VOICE_TO_TEXT_BASE_URL)
            .client(httpClient.build())
            .build()
            .create(EchoJournalNetworkApi::class.java)
    }
}