package com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.filters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun CustomDropdownMenu(
    expanded: Boolean = false,
    columnSize: Size = Size.Zero,
    dropdownMenuContent: @Composable () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {

    AnimatedVisibility(
        visible = expanded,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Popup(
            onDismissRequest = {
                onDismissRequest()
            },
            properties = PopupProperties(focusable = true)
        ) {
            Column(
                modifier = Modifier
                    .width(width = with(LocalDensity.current) { columnSize.width.toDp() })
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                dropdownMenuContent()
            }
        }
    }
}


@Preview
@Composable
fun CustomDropdownMenuPreview() {
    CustomDropdownMenu()
}