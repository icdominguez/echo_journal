package com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.entrytimeline

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.icdominguez.echo_journal.R
import com.icdominguez.echo_journal.presentation.designsystem.theme.LocalEchoJournalTypography
import com.icdominguez.echo_journal.presentation.designsystem.theme.Primary30
import com.icdominguez.echo_journal.presentation.screens.FakeData
import kotlin.math.roundToInt

@Composable
fun DescriptionText(
    text: String,
    minimizedMaxLines: Int = 3
) {
    var cutText by remember(text) { mutableStateOf<String?>(null)  }
    var expanded by remember { mutableStateOf(false) }
    var textLines by remember { mutableIntStateOf(0) }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    var seeMoreSize by remember { mutableStateOf(IntSize.Zero) }
    var seeMoreOffset by remember { mutableStateOf(Offset.Zero) }

    LaunchedEffect(text, expanded, textLayoutResult, seeMoreSize) {
        val lastLineIndex = minimizedMaxLines - 1
        if (!expanded && textLayoutResult != null && seeMoreSize != IntSize.Zero
            && lastLineIndex + 1 == textLayoutResult!!.lineCount
            && textLayoutResult!!.isLineEllipsized(lastLineIndex)
        ) {
            var lastCharIndex = textLayoutResult!!.getLineEnd(lineIndex = lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult!!.getCursorRect(lastCharIndex)
            } while (
                charRect.left > textLayoutResult!!.size.width - seeMoreSize.width
            )

            seeMoreOffset = Offset(x = charRect.left, y = charRect.bottom - seeMoreSize.height)
            cutText = text.substring(startIndex = 0, endIndex = lastCharIndex)
        }
    }

    Box(modifier = Modifier
        .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .animateContentSize(),
            text = cutText ?: text,
            style = LocalEchoJournalTypography.current.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            onTextLayout = {
                textLines = it.lineCount
                textLayoutResult = it
            }
        )

        if (!expanded && textLines >= minimizedMaxLines) {
            val showMoreText = buildAnnotatedString {
                append(text = "... ")
                withStyle(
                    style = SpanStyle(color = Primary30)
                ) {
                    append(text = stringResource(R.string.show_more))
                }
            }

            Text(
                text = showMoreText,
                style = LocalEchoJournalTypography.current.bodyMedium,
                onTextLayout = { seeMoreSize = it.size },
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = seeMoreOffset.x.roundToInt(),
                            y = seeMoreOffset.y.roundToInt()
                        )
                    }
                    .clickable {
                        expanded = true
                        cutText = null
                    }
                    .alpha(alpha = if (seeMoreOffset != Offset.Zero) 1f else 0f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DescriptionTextComponent() {
    DescriptionText(
        text = FakeData.timelineEntries[0].description
    )
}










