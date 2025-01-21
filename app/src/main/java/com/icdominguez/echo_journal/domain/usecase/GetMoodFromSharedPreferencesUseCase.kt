package com.icdominguez.echo_journal.domain.usecase

import com.icdominguez.echo_journal.core.EchoJournalSharedPreferencesDataSource
import javax.inject.Inject

class GetMoodFromSharedPreferencesUseCase @Inject constructor(
    private val echoJournalSharedPreferencesDataSource: EchoJournalSharedPreferencesDataSource
) {
    operator fun invoke() =
        echoJournalSharedPreferencesDataSource.getMood()
}