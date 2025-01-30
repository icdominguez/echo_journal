package com.icdominguez.echo_journal.domain.usecase.preferences

import com.icdominguez.echo_journal.core.preferences.EchoJournalSharedPreferencesDataSource
import com.icdominguez.echo_journal.presentation.screens.createrecord.model.Mood
import javax.inject.Inject

class SetMoodInSharedPreferencesUseCase @Inject constructor(
    private val echoJournalSharedPreferencesDataSource: EchoJournalSharedPreferencesDataSource
) {
    operator fun invoke(mood: Mood) =
        echoJournalSharedPreferencesDataSource.setMood(mood.name)
}