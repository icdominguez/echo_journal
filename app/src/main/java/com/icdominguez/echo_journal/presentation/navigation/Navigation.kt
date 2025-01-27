package com.icdominguez.echo_journal.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.icdominguez.echo_journal.presentation.screens.createrecord.CreateRecordScreen
import com.icdominguez.echo_journal.presentation.screens.createrecord.CreateRecordScreenViewModel
import com.icdominguez.echo_journal.presentation.screens.settings.SettingsScreen
import com.icdominguez.echo_journal.presentation.screens.settings.SettingsScreenViewModel
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.YourEchoJournalScreenViewModel
import com.icdominguez.echo_journal.presentation.screens.yourechojournal.YourEchoJournalScreen

@Composable
fun Navigation(
    isLaunchedFromWidget: Boolean = false,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavItem.YourEchoJournal.route
    ) {
        composable(route = NavItem.YourEchoJournal.baseRoute) {
            val viewModel = hiltViewModel<YourEchoJournalScreenViewModel>()

            YourEchoJournalScreen(
                state = viewModel.state.collectAsStateWithLifecycle().value,
                uiEvent = viewModel::uiEvent,
                navigateToCreateRecordScreen = { fileRecordedPath ->
                    navController.navigate(NavItem.CreateRecord.createNavRoute(fileRecordedPath))
                },
                navigateToSettingsScreenScreen = {
                    navController.navigate(NavItem.Settings.route)
                },
                isLaunchedFromWidget = isLaunchedFromWidget,
            )
        }

        composable(
            route = NavItem.CreateRecord.route,
            arguments = NavItem.CreateRecord.args,
        ) { backStackEntry ->
            val fileRecordedPath = backStackEntry.arguments?.getString(NavArg.FileRecordedPath.key)
            requireNotNull(fileRecordedPath) { "Can't be null, create record required a file path" }
            val viewModel = hiltViewModel<CreateRecordScreenViewModel>()

            CreateRecordScreen(
                state = viewModel.state.collectAsStateWithLifecycle().value,
                uiEvent = viewModel::uiEvent,
                navigateBack = { navController.popBackStack() },
            )
        }

        composable(route = NavItem.Settings.route) {
            val viewModel = hiltViewModel<SettingsScreenViewModel>()

            SettingsScreen(
                state = viewModel.state.collectAsStateWithLifecycle().value,
                uiEvent = viewModel::uiEvent,
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}