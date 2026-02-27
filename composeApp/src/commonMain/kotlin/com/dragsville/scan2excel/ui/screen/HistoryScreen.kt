package com.dragsville.scan2excel.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dragsville.scan2excel.data.models.ScanResult
import com.dragsville.scan2excel.ui.viewmodel.ScanViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    scanViewModel: ScanViewModel
) {
    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    val scans by scanViewModel.scans.collectAsState()
    val customBlue = Color(0xFF2196F3)

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("History") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = customBlue,      // Usually Blue in Light, Darker Blue in Dark
                    titleContentColor = colorScheme.onPrimary, // White text on Blue
                    actionIconContentColor = colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            // 🔹 Tabs
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Scans") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Docs") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search...") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Content
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if (selectedTab == 0) {
                    // Show all scans
                    items(
                        scans.filter {
                            it.filePath.contains(searchQuery, ignoreCase = true)
                        }
                    ) { scan ->
                        HistoryScanItem(scan)
                    }
                } else {
                    // Placeholder for exported docs
                    items(10) {
                        HistoryDocItemPlaceholder(index = it)
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryDocItemPlaceholder(index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Exported Document #$index")
            Text("Placeholder.xlsx")
        }
    }
}

@Composable
fun HistoryScanItem(scan: ScanResult) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Scan ID: ${scan.id}")
            Text("Path: ${scan.filePath}")
        }
    }
}
