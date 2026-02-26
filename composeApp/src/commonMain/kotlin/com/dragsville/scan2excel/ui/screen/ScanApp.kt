package com.dragsville.scan2excel.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dragsville.scan2excel.ui.ScanViewModelProvider
import com.dragsville.scan2excel.ui.viewmodel.ScanViewModel
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanApp(
    scanViewModel: ScanViewModel = viewModel(
        factory = ScanViewModelProvider.Factory
    )
) {
    val navController = rememberNavController()
    val customBlue = Color(0xFF2196F3) // You can change this hex code here
    val customWhite = Color.White

    Scaffold(
        containerColor = customWhite, // Makes the app background white
        bottomBar = {
            // Ensure your BottomNavigationBar implementation accepts containerColor
            // or wraps its content in a Surface/BottomAppBar
            Surface(color = customBlue) {
                BottomNavigationBar(navController)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Home.route) { HomeScreen(scanViewModel) }
            composable(Screen.History.route) { HistoryScreen(scanViewModel) }
            composable(Screen.Accounts.route) { AccountsScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}