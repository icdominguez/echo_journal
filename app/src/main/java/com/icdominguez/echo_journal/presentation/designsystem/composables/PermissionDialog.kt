package com.icdominguez.echo_journal.presentation.designsystem.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.presentation.designsystem.PermissionTextProvider

@Composable
fun PermissionDialog(
    modifier: Modifier = Modifier,
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean = false,
    onDismissClicked: () -> Unit = {},
    onOkClicked: () -> Unit = {},
    onGoToAppSettingsClick: () -> Unit = {},
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissClicked,
        confirmButton = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                HorizontalDivider()
                Text(
                    text = if (isPermanentlyDeclined) {
                        stringResource(R.string.grant_permission)
                    } else {
                        stringResource(R.string.ok)
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingsClick()
                            } else {
                                onOkClicked()
                            }
                        }
                        .padding(16.dp)
                )
            }
        },
        title = { Text(text = stringResource(R.string.permission_required)) },
        text = { Text(text = permissionTextProvider.getDescription(isPermanentlyDeclined = isPermanentlyDeclined)) },
    )

}