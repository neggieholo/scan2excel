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
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dragsville.scan2excel.ui.viewmodel.ScanViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: ScanViewModel) {
    val scans by viewModel.scans.collectAsState()
    val rootNavigator = LocalNavigator.currentOrThrow.parent
    val customBlue = Color(0xFF2196F3)
    // USE THEME COLORS INSTEAD OF HARD-CODED ONES
    val colorScheme = MaterialTheme.colorScheme
    val metallicGradient = androidx.compose.ui.graphics.Brush.verticalGradient(
        colors = listOf(
            Color(0xFFBDBDBD), // Darker Silver top
            Color(0xFFF5F5F5), // White-ish shine center
            Color(0xFFEEEEEE), // Light Gray middle
            Color(0xFF9E9E9E)  // Darker Silver bottom
        )
    )

    Scaffold(
        containerColor = colorScheme.background, // Respects dark mode background
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(metallicGradient)
                    .shadow(elevation = 8.dp)
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "ScanToExcel",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                // Using the .sp extension property directly
                                letterSpacing = 1.5.sp,
                                // Subtle shadow makes blue text look "etched" into metal
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.2f),
                                    offset = Offset(2f, 2f),
                                    blurRadius = 4f
                                )
                            ),
                            color = customBlue
                        )
                    },
                    actions = {
                        IconButton(onClick = { rootNavigator?.push(NotificationScreen()) }) {
                            Icon(Icons.Default.Notifications, null, tint = customBlue)
                        }
                        IconButton(onClick = { rootNavigator?.push(ProfileScreen()) }) {
                            Icon(Icons.Default.Person, null, tint = customBlue)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent // Allows gradient to show
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.addFakeScan() },
                containerColor = customBlue,
                contentColor = colorScheme.onPrimaryContainer
            ) {
                Icon(Icons.Default.Add, contentDescription = "Scan")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // --- SECTION 1: RECENT SCANS ---
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Recent Scans",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground // Automatically switches White/Black
                )
                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
                        .background(colorScheme.surfaceVariant, RoundedCornerShape(12.dp)) // Adapts to Dark Mode
                        .padding(8.dp)
                ) {
                    if (scans.isEmpty()) {
                        Text(
                            "No recent exports",
                            modifier = Modifier.align(Alignment.Center),
                            color = colorScheme.onSurfaceVariant
                        )
                    } else {
                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(scans) { scan ->
                                Card(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    // Use default card colors so they adapt
                                    colors = CardDefaults.cardColors(
                                        containerColor = colorScheme.surface
                                    )
                                ) {
                                    ListItem(
                                        headlineContent = { Text("Document ${scan.id}") },
                                        supportingContent = { Text(scan.filePath) },
                                        // ListItem defaults to surface, so no hard-coded white here!
                                    )
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
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
                        .background(colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                        .padding(8.dp)
                ) {
                    if (scans.isEmpty()) {
                        Text(
                            "No recent exports",
                            modifier = Modifier.align(Alignment.Center),
                            color = colorScheme.onSurfaceVariant
                        )
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(scans) { scan ->
                                Card(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    // Use default card colors so they adapt
                                    colors = CardDefaults.cardColors(
                                        containerColor = colorScheme.surface
                                    )
                                ) {
                                    ListItem(
                                        headlineContent = { Text("Document ${scan.id}") },
                                        supportingContent = { Text(scan.filePath) },
                                        // ListItem defaults to surface, so no hard-coded white here!
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}