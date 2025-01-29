package com.icdominguez.echo_journal.core.di

import com.icdominguez.echo_journal.core.network.EchoJournalNetwork
import com.icdominguez.echo_journal.core.network.EchoJournalNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindings {

    @Binds
    abstract fun bindEchoJournalNetworkDataSource(
        echoJournalNetworkDataSource: EchoJournalNetwork
    ): EchoJournalNetworkDataSource
}