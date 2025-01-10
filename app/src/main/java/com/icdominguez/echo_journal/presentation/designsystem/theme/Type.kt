package com.icdominguez.echo_journal.presentation.designsystem.theme

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.icdominguez.echo_journal.R

private val DefaultCustomFontFamily = FontFamily(
    Font(R.font.inter_regular)
)

private val DefaultCustomStyle = TextStyle(
    fontFamily = DefaultCustomFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 20.sp,
)

sealed class EchoJournalTypography(
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    val labelMedium: TextStyle,
    val labelLarge: TextStyle,
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val headlineSmall: TextStyle,
    val headlineXSmall: TextStyle,
    val buttonLarge: TextStyle,
    val button: TextStyle,
)

@Stable
internal data object MainTypography : EchoJournalTypography(
    bodyMedium = DefaultCustomStyle,
    bodySmall = DefaultCustomStyle.copy(
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    labelMedium = DefaultCustomStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 0.sp,
    ),
    labelLarge = DefaultCustomStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),
    headlineLarge = DefaultCustomStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 26.sp,
        lineHeight = 32.sp,
    ),
    headlineMedium = DefaultCustomStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 26.sp,
    ),
    headlineSmall = DefaultCustomStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp,
    ),
    headlineXSmall = DefaultCustomStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 18.sp,
    ),
    button = DefaultCustomStyle.copy(
        fontWeight = FontWeight.Medium,
    ),
    buttonLarge = DefaultCustomStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
)

val LocalEchoJournalTypography: ProvidableCompositionLocal<EchoJournalTypography> =
    staticCompositionLocalOf {
        MainTypography
    }