package com.icdominguez.echo_journal.domain.di

import com.icdominguez.echo_journal.data.repository.LocalEchoJournalRepositoryImpl
import com.icdominguez.echo_journal.domain.repository.LocalEchoJournalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    @Binds
    fun provideLocalEchoJournalRepository(localEchoJournalRepositoryImpl: LocalEchoJournalRepositoryImpl): LocalEchoJournalRepository
}