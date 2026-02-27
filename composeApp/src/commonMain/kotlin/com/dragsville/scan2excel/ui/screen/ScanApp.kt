package com.dragsville.scan2excel.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dragsville.scan2excel.ui.ScanViewModelProvider
import com.dragsville.scan2excel.ui.viewmodel.ScanViewModel
import androidx.compose.material3.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator

class MainViewScreen : Screen {
    @Composable
    override fun Content() {
        ScanApp()
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanApp(
    scanViewModel: ScanViewModel = viewModel(factory = ScanViewModelProvider.Factory)
) {
    // Define the Tabs once
    val homeTab = HomeTab(scanViewModel)
    val historyTab = HistoryTab(scanViewModel)
    val accountsTab = AccountsTab
    val settingsTab = SettingsTab

    TabNavigator(homeTab) { tabNavigator ->
        Scaffold(
            bottomBar = {
                // Pass the specific tabs to the bar
                BottomNavigationBar(
                    tabNavigator = tabNavigator,
                    tabs = listOf(homeTab, historyTab, accountsTab, settingsTab)
                )
            },
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { padding ->
            Box() {
                CurrentTab()
            }
        }
    }
}