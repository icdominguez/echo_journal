package com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables

import android.content.Context
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.presentation.designsystem.PermissionTextProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RecordAudioTextProvider @Inject constructor(
    @ApplicationContext private val context: Context
): PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            context.getString(R.string.audio_record_permission_permanent_declined)
        } else {
            context.getString(R.string.audio_record_permission_not_declined)
        }
    }

}