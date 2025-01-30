package com.icdominguez.echo_journal.core.preferences

interface EchoJournalSharedPreferencesDataSource {
    fun setMood(mood: String)
    fun getMood(): String?
}