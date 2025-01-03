package com.icdominguez.echo_journal.data.di

import android.content.Context
import androidx.room.Room
import com.icdominguez.echo_journal.data.database.EchoJournalDatabase
import com.icdominguez.echo_journal.data.database.EntryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): EchoJournalDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            EchoJournalDatabase::class.java,
            "echo journal"
        ).build()
    }

    @Singleton
    @Provides
    fun provideEntryDao(echoJournalDatabase: EchoJournalDatabase): EntryDao {
        return echoJournalDatabase.entryDao()
    }
}