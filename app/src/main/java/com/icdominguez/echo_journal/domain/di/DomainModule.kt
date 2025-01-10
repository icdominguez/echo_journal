package com.icdominguez.echo_journal.domain.di

import android.content.Context
import com.icdominguez.echo_journal.data.audio.AndroidAudioPlayer
import com.icdominguez.echo_journal.data.audio.AndroidAudioRecorder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Singleton
    @Provides
    fun provideAudioRecorder(@ApplicationContext context: Context): AndroidAudioRecorder {
        return AndroidAudioRecorder(context)
    }

    @Singleton
    @Provides
    fun provideAudioPlayer(@ApplicationContext context: Context): AndroidAudioPlayer {
        return AndroidAudioPlayer(context)
    }
}