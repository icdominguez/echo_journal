package com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.SelectableChipElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.echo_journal.presentation.designsystem.theme.Primary30
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.composables.filters.CustomDropdownMenu

@Composable
fun CustomFilterChip(
    selectedList: List<Any>,
    filterChipLabel: @Composable () -> Unit,
    dropdownMenuContent: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        FilterChip(
            modifier = Modifier.padding(start = 4.dp,end = 4.dp),
            onClick = {
                expanded = !expanded
            },
            label = {
                filterChipLabel()
            },
            elevation = SelectableChipElevation(
                elevation = if (selectedList.isNotEmpty() && !expanded) 4.dp else 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 4.dp,
                hoveredElevation = 0.dp,
                focusedElevation = 0.dp,
                draggedElevation = 0.dp,
            ),
            selected = expanded,
            colors = FilterChipDefaults.filterChipColors(
                containerColor = if (selectedList.isNotEmpty()) Color.White else Color.Transparent,
                selectedContainerColor = Color.White,
                disabledSelectedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent

            ),
            border = if (expanded) {
                BorderStroke(width = 1.dp, color = Primary30)
            } else if (selectedList.isEmpty()) {
                BorderStroke(width = 1.dp, color = Color.Gray)
            } else {
                BorderStroke(0.dp, color = Color.Transparent)
            },
            shape = CircleShape,
            interactionSource = null
        )

        CustomDropdownMenu(
            expanded = expanded,
            dropdownMenuContent = dropdownMenuContent,
            onDismissRequest = { expanded = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomFilterChipPreview() {
    MoodFilterChip(
        selectedMoodList = listOf()
    )
}