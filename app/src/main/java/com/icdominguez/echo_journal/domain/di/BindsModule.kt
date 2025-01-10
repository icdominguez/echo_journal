package com.icdominguez.echo_journal.domain.di

import com.icdominguez.echo_journal.data.repository.FileManagerRepositoryImpl
import com.icdominguez.echo_journal.data.repository.LocalEchoJournalRepositoryImpl
import com.icdominguez.echo_journal.data.timer.AndroidTimer
import com.icdominguez.echo_journal.domain.repository.FileManagerRepository
import com.icdominguez.echo_journal.domain.repository.LocalEchoJournalRepository
import com.icdominguez.echo_journal.domain.timer.Timer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {
    @Binds
    fun provideLocalEchoJournalRepository(localEchoJournalRepositoryImpl: LocalEchoJournalRepositoryImpl): LocalEchoJournalRepository

    @Binds
    fun provideFileManagerRepository(fileManagerRepositoryImpl: FileManagerRepositoryImpl): FileManagerRepository

    @Binds
    fun provideTimer(androidTimer: AndroidTimer): Timer
}