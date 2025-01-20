package com.icdominguez.echo_journal.presentation.screens.createrecord.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.icdominguez.echo_journal.R

@Composable
fun LeaveCreateRecordDialog(
    onDismissRequest: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.leave_entry_dialog_title))
        },
        text = {
            Text(text = stringResource(R.string.leave_entry_dialog_message))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(
                    text = stringResource(R.string.leave_entry_dialog_confirm_button)
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource(R.string.leave_entry_dialog_dismiss_button))
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun LeaveCreateRecordDialogPreview() {
    LeaveCreateRecordDialog()
}

