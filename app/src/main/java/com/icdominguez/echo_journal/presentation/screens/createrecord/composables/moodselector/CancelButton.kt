package com.icdominguez.echo_journal.presentation.screens.createrecord.composables.moodselector

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.presentation.designsystem.theme.ButtonBackground
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography

@Composable
fun CancelButton(
    modifier: Modifier = Modifier,
    onCancelButtonClicked: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val color = if (isPressed) MaterialTheme.colorScheme.primary else ButtonBackground

    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.inverseOnSurface,
                shape = RoundedCornerShape(100.dp),
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                onCancelButtonClicked()
            }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center,
        ) {
        Text(
            text = stringResource(R.string.cancel),
            style = LocalEchoJournalTypography.current.buttonLarge.copy(color = color, fontWeight = FontWeight.Bold)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun CancelButtonPreview() {
    CancelButton()
}