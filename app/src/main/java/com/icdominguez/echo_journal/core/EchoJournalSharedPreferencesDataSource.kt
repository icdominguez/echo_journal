package com.icdominguez.echo_journal.core

interface EchoJournalSharedPreferencesDataSource {
    fun setMood(mood: String)
    fun getMood(): String?
}