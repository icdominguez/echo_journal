package com.icdominguez.echo_journal.core.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class LocalEchoJournalPreferencesDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): EchoJournalSharedPreferencesDataSource {

    override fun setMood(mood: String) {
        sharedPreferences.edit().putString(MOOD, mood).apply()
    }

    override fun getMood(): String? = sharedPreferences.getString(MOOD, null)

    companion object {
        private const val MOOD = "mood"
    }
}