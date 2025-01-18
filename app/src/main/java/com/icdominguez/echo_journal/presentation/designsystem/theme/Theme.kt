package com.icdominguez.echo_journal.presentation.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    surface = Primary100,
    inverseOnSurface = Secondary95,
    onSurface = NeutralVariant10,
    onSurfaceVariant = NeutralVariant30,
    outline = NeutralVariant50,
    outlineVariant = NeutralVariant80,
    primary = Primary30,
    primaryContainer = Primary50,
    onPrimary = Primary100,
    inversePrimary = Secondary80,
    secondary = Secondary30,
    secondaryContainer = Secondary50,
    surfaceTint = SurfaceTint12,
    background = NeutralVariant99,
    onErrorContainer = Error20,
    errorContainer = Error95,
    onError = Error100,
)

private val typography = Typography()

@Composable
fun EchoJournalTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = typography,
        content = content
    )
}