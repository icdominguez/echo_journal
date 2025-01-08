package com.icdominguez.echo_journal.presentation.designsystem

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}