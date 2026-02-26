package com.dragsville.scan2excel.ui.screen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object History : Screen("history")
    object Accounts : Screen("accounts")
    object Settings : Screen("settings")
}