package com.icdominguez.echo_journal.core.di

import android.content.Context
import android.content.SharedPreferences
import com.icdominguez.echo_journal.core.PreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Provides
    fun provideSharedPreferences(
       @ApplicationContext context: Context,
    ): SharedPreferences = PreferencesHelper.getPrefs(context)
}