package com.icdominguez.echo_journal.presentation.navigation

sealed class NavItem(
    val baseRoute: String,
) {
    data object YourEchoJournal: NavItem(baseRoute = "your_echo_journal")
    data object CreateRecord: NavItem(baseRoute = "create_record")
}