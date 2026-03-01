package com.dragsville.scan2excel.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val colorScheme = colorScheme

    Scaffold (
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorScheme.surface)
                    .drawBehind {
                        val strokeWidth = 2.dp.toPx()
                        drawLine(
                            color = colorScheme.primary,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = strokeWidth
                        )
                    }
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "History",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.5.sp,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.2f),
                                    offset = Offset(2f, 2f),
                                    blurRadius = 4f
                                )
                            ),
                            color = colorScheme.primary
                        )
                    },
                    windowInsets = WindowInsets(0, 0, 0, 0),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            // 🔹 Tabs
            SecondaryTabRow(selectedTabIndex = selectedTab) {
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
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("Templates") }
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
                } else if (selectedTab == 2) {
                    // Show all scans
                    items(
                        scans.filter {
                            it.filePath.contains(searchQuery, ignoreCase = true)
                        }
                    ) { scan ->
                        HistoryTemplateItem(scan)
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


@Composable
fun HistoryTemplateItem(scan: ScanResult) {
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
