package com.icdominguez.echo_journal.presentation.screens.createrecord.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography

@Composable
fun DescriptionTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    onDescriptionTextChange: (String) -> Unit = {},
) {
    var hasFocus by remember { mutableStateOf(false) }
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                hasFocus = focusState.isFocused
            },
        value = text,
        onValueChange = {
            onDescriptionTextChange(it)
        },
        textStyle = LocalEchoJournalTypography.current.bodyMedium.copy(MaterialTheme.colorScheme.onSurfaceVariant),
        decorationBox = { innerTextField ->
            Row {
                if(text.isEmpty() && !hasFocus) {
                    Text(
                        text = stringResource(R.string.add_description),
                        style = LocalEchoJournalTypography.current.bodyMedium,
                        color = MaterialTheme.colorScheme.outlineVariant,
                    )
                }
                innerTextField()
            }
        }
    )
}
@Preview
@Composable
private fun DescriptionTextFieldPreview() {
    DescriptionTextField()
}