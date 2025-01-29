package com.icdominguez.echo_journal.domain.usecase.preferences

import com.icdominguez.echo_journal.core.preferences.EchoJournalSharedPreferencesDataSource
import javax.inject.Inject

class GetMoodFromSharedPreferencesUseCase @Inject constructor(
    private val echoJournalSharedPreferencesDataSource: EchoJournalSharedPreferencesDataSource
) {
    operator fun invoke() =
        echoJournalSharedPreferencesDataSource.getMood()
}