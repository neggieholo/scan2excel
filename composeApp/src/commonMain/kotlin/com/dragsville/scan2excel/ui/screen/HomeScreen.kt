package com.dragsville.scan2excel.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dragsville.scan2excel.scanManager.ScanOptionsDialog
import com.dragsville.scan2excel.scanManager.rememberScannerManager
import com.dragsville.scan2excel.ui.helpers.CreateTemplateSection
import io.github.ismoy.imagepickerkmp.domain.config.ImagePickerConfig
import io.github.ismoy.imagepickerkmp.domain.extensions.loadBytes
import io.github.ismoy.imagepickerkmp.presentation.ui.components.GalleryPickerLauncher
import io.github.ismoy.imagepickerkmp.presentation.ui.components.ImagePickerLauncher

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: ScanViewModel) {
    val scans by viewModel.scans.collectAsState()
    val rootNavigator = LocalNavigator.currentOrThrow.parent
    val colorScheme = MaterialTheme.colorScheme
    var showMenu by rememberSaveable { mutableStateOf(false) }
    var createActive by rememberSaveable { mutableStateOf(false) }
    val ocrResult by viewModel.ocrDialogText.collectAsState()

    val scannerManager = rememberScannerManager()

    Scaffold(
        containerColor = colorScheme.surface,
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
                            text = "Scan2Excel",
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
                    actions = {
                        IconButton(onClick = { rootNavigator?.push(NotificationScreen()) }) {
                            Icon(Icons.Default.Notifications, null, tint = colorScheme.primary)
                        }
                        IconButton(onClick = { rootNavigator?.push(ProfileScreen()) }) {
                            Icon(Icons.Default.Person, null, tint = colorScheme.primary)
                        }
                    },
                    windowInsets = WindowInsets(0, 0, 0, 0),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent // Allows gradient to show
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (showMenu || createActive) {
                        // If anything is open, the FAB acts as a global "Cancel/Close"
                        showMenu = false
                        createActive = false
                        scannerManager.reset()
                    } else {
                        // If we are on the dashboard, open the menu
                        showMenu = true
                    }
                },
                containerColor = colorScheme.primary,
                contentColor = colorScheme.onPrimaryContainer
            ) {
                Icon(
                    imageVector = if (showMenu || createActive) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = if (showMenu || createActive) "Close" else "Add"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if(!createActive) {
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

                Spacer(modifier = Modifier.weight(0.1f))
            }

            if (showMenu) {
                ScanOptionsDialog(
                    onDismiss = { showMenu = false }, // Just close the menu
                    onPickFile = {
                        showMenu = false
                        createActive = true
                        scannerManager.launchFilePicker()
                    },
                    onLiveScan = {
                        showMenu = false
                        createActive = true
                        scannerManager.launchLiveScan()
                    },
                    onCreateTemplate = {
                        showMenu = false
                        createActive = true
                        scannerManager.launchTemplateCreate()
                    }
                )
            }

            if (createActive) {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (scannerManager.showCamera) {
                        ImagePickerLauncher(
                            config = ImagePickerConfig(
                                onPhotoCaptured = { result ->
                                    scannerManager.reset()
                                    viewModel.processImage(result.loadBytes())
                                },
                                onError = { scannerManager.reset() },
                                onDismiss = { scannerManager.reset() }
                            )
                        )
                    }

                    if (scannerManager.showGallery) {
                        GalleryPickerLauncher(
                            onPhotosSelected = { photos ->
                                scannerManager.reset()
                                photos.firstOrNull()?.let { viewModel.processImage(it.loadBytes()) }
                            },
                            onError = { scannerManager.reset() },
                            onDismiss = { scannerManager.reset() },
                            allowMultiple = true
                        )
                    }

                    if (scannerManager.showTemplateCreate) {
                        CreateTemplateSection(
                            viewModel = viewModel
                        )
                    }
                }
            }
        }

        ocrResult?.let { text ->
            AlertDialog(
                onDismissRequest = { viewModel.dismissOcrDialog() },
                title = { Text("Extracted Text") },
                text = {
                    // Using a Column in case the text is long
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Text(text)
                    }
                },
                confirmButton = {
                    TextButton(onClick = { viewModel.dismissOcrDialog() }) {
                        Text("Close")
                    }
                }
            )
        }
    }
}
