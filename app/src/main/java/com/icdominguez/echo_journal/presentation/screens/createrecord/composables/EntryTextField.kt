package com.icdominguez.echo_journal.presentation.screens.createrecord.composables

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography

@Composable
fun EntryTextField(
    entryText: String = "",
    onEntryTextChange: (String) -> Unit = {},
) {
    var hasFocus by remember { mutableStateOf(false) }

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                hasFocus = focusState.isFocused
            },
        value = entryText,
        onValueChange = {
            onEntryTextChange(it)
        },
        singleLine = true,
        textStyle = LocalEchoJournalTypography.current.headlineLarge,
        decorationBox = { innerTextField ->
            Row {
                if(entryText.isEmpty() && !hasFocus) {
                    Text(
                        text = stringResource(R.string.add_title),
                        style = LocalEchoJournalTypography.current.headlineLarge,
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
private fun EntryTextFieldPreview() {
    EntryTextField()
}