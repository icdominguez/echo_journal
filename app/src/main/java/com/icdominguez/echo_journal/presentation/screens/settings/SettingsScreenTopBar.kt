package com.icdominguez.echo_journal.presentation.screens.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.icdominguez.echo_journal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenTopBar(
    modifier: Modifier = Modifier,
    onNavigationIconClicked: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        title = {
            Text(text = stringResource(R.string.settings))
        },
        navigationIcon = {
            IconButton(
                onClick = { onNavigationIconClicked() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview
@Composable
private fun SettingsScreenTopBarPreview() {
    SettingsScreen()
}