package com.icdominguez.echo_journal.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.icdominguez.echo_journal.presentation.screens.createrecord.CreateRecordScreen
import com.icdominguez.echo_journal.presentation.screens.createrecord.CreateRecordScreenViewModel
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.YourEchoJournalScreenViewModel
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.YourEchoJournalScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavItem.YourEchoJournal.baseRoute
    ) {
        composable(route = NavItem.YourEchoJournal.baseRoute) {
            val viewModel = hiltViewModel<YourEchoJournalScreenViewModel>()

            YourEchoJournalScreen(
                state = viewModel.state.collectAsStateWithLifecycle().value,
                uiEvent = viewModel::uiEvent,
                navigateToCreateRecordScreen = { navController.navigate(NavItem.CreateRecord.baseRoute) }
            )
        }

        composable(route = NavItem.CreateRecord.baseRoute) {
            val viewModel = hiltViewModel<CreateRecordScreenViewModel>()

            CreateRecordScreen(
                state = viewModel.state.collectAsStateWithLifecycle().value,
                uiEvent = viewModel::uiEvent,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}