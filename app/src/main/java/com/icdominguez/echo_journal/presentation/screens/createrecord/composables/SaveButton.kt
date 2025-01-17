package com.icdominguez.echo_journal.presentation.screens.createrecord.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography

@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val brush = if (isPressed) {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFF578CFF),
                Color(0xFF0057CC),
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFF578CFF),
                Color(0xFF1F70F5)
            )
        )
    }
    val disabledModifier = Modifier
        .background(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(100.dp),
        )
    val enabledModifier = Modifier
        .background(
            brush = brush,
            shape = RoundedCornerShape(100.dp),
        )
    val color = if(enabled) Color.White else MaterialTheme.colorScheme.outline
    Box(
        modifier = modifier
            .then(if (enabled) enabledModifier else disabledModifier)
            .padding(vertical = 10.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = {
                    onClick()
                }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(16.dp),
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = color
            )
            Text(
                modifier = Modifier
                    .padding(start = 4.dp),
                text = stringResource(R.string.confirm),
                style = LocalEchoJournalTypography.current.buttonLarge.copy(
                    color = color,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun SaveButtonPreview() {
    SaveButton()
}