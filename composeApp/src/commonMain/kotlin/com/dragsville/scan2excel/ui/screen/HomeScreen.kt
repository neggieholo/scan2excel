package com.dragsville.scan2excel.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dragsville.scantoexcel.ui.viewmodel.ScanViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults

@Composable
fun HomeScreen(viewModel: ScanViewModel) {
    val scans by viewModel.scans.collectAsState()

    // Style Constants
    val sectionBg = Color(0xFFF1F3F4) // Light stylish grey
    val titleColor = Color(0xFF202124) // Darker grey/black for text

    Scaffold (
        bottomBar = {
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { scanViewModel.addFakeScan() },
                    containerColor = customBlue,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Scan Document")
                }
            } }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            // --- SECTION 1: RECENT SCANS ---
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Recent Scans",
                    style = MaterialTheme.typography.headlineSmall, // More stylish font
                    fontWeight = FontWeight.Bold,
                    color = titleColor
                )
                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp)) // Added shadow
                        .background(sectionBg, RoundedCornerShape(12.dp)) // Darker background
                        .padding(8.dp)
                ) {
                    if (scans.isEmpty()) {
                        Text(
                            "No recent scans",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    } else {
                        LazyRow {
                            items(scans.take(10)) { scan ->
                                Card(
                                    modifier = Modifier
                                        .size(120.dp)
                                        .padding(4.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("Scan\n#${scan.id}", fontWeight = FontWeight.Medium)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- SECTION 2: EXPORTED DOCUMENTS ---
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Exported Documents",
                    style = MaterialTheme.typography.headlineSmall, // Consistent styling
                    fontWeight = FontWeight.Bold,
                    color = titleColor
                )
                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
                        .background(sectionBg, RoundedCornerShape(12.dp))
                        .padding(8.dp)
                ) {
                    if (scans.isEmpty()) {
                        Text(
                            "No recent exports",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(scans) { scan ->
                                Card(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White)
                                ) {
                                    ListItem(
                                        headlineContent = {
                                            Text(
                                                "Document ${scan.id}",
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        },
                                        supportingContent = { Text(scan.filePath) },
                                        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
}