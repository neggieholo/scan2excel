package com.dragsville.scan2excel.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.dragsville.scan2excel.ui.viewmodel.ScanViewModel

// We pass the ViewModel into the Tab so the screens can use it
data class HomeTab(val viewModel: ScanViewModel) : Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(index = 0u, title = "Home", icon = rememberVectorPainter(
            Icons.Default.Home))

    @Composable override fun Content() = HomeScreen(viewModel)
}

data class HistoryTab(val viewModel: ScanViewModel) : Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(index = 1u, title = "History", icon = rememberVectorPainter(Icons.Default.History))

    @Composable override fun Content() = HistoryScreen(viewModel)
}
// Do the same for AccountsTab and SettingsTab...
object AccountsTab : Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(index = 2u, title = "Accounts", icon = rememberVectorPainter(Icons.Default.Person))

    @Composable override fun Content() = AccountsScreen()
}

object WorkspaceTab : Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(
            index = 3u,
            title = "Workspace",
            icon = rememberVectorPainter(Icons.Default.PendingActions)
        )

    @Composable override fun Content() = WorkspaceScreen()
}
//object SettingsTab : Tab {
//    override val options: TabOptions
//        @Composable get() = TabOptions(
//            index = 3u,
//            title = "Settings",
//            icon = rememberVectorPainter(Icons.Default.Settings)
//        )
//
//    @Composable override fun Content() = SettingsScreen()
//}
