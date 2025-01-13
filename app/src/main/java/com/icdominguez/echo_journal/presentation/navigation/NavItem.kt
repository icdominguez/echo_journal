package com.icdominguez.echo_journal.presentation.navigation

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(
    val baseRoute: String,
    val navArgs: List<NavArg> = emptyList(),
) {
    val route = run {
        val argsKey = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(argsKey)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

    data object YourEchoJournal: NavItem(baseRoute = "your_echo_journal")
    data object CreateRecord: NavItem(baseRoute = "create_record", listOf(NavArg.FileRecordedPath)) {
        fun createNavRoute(fileRecordedPath: String) = "$baseRoute/${Uri.encode(fileRecordedPath)}"
    }
}

enum class NavArg(
    val key: String,
    val navType: NavType<*>,
) {
    FileRecordedPath(
        key = "fileRecordedPath",
        navType = NavType.StringType,
    )
}