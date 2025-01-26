package com.icdominguez.echo_journal.core.di

import com.icdominguez.echo_journal.core.EchoJournalSharedPreferencesDataSource
import com.icdominguez.echo_journal.core.LocalEchoJournalPreferencesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesBinding {
    @Binds
    abstract fun bindEchoJournalPreferenceDataSource(
        localEchoJournalPreferencesDataSource: LocalEchoJournalPreferencesDataSource
    ): EchoJournalSharedPreferencesDataSource
}