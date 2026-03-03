package com.dragsville.scan2excel.scanManager

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import io.github.ismoy.imagepickerkmp.domain.config.ImagePickerConfig
import io.github.ismoy.imagepickerkmp.domain.extensions.loadBytes
import io.github.ismoy.imagepickerkmp.presentation.ui.components.GalleryPickerLauncher
import io.github.ismoy.imagepickerkmp.presentation.ui.components.ImagePickerLauncher

interface ScannerManager {
    val showCamera: Boolean
    val showGallery: Boolean
    fun launchLiveScan()
    fun launchFilePicker()
    fun reset() // To close them
}

@Composable
fun rememberScannerManager(): ScannerManager {
    var cameraActive by remember { mutableStateOf(false) }
    var galleryActive by remember { mutableStateOf(false) }

    return remember {
        object : ScannerManager {
            override val showCamera get() = cameraActive
            override val showGallery get() = galleryActive
            override fun launchLiveScan() { cameraActive = true }
            override fun launchFilePicker() { galleryActive = true }
            override fun reset() {
                cameraActive = false
                galleryActive = false
            }
        }
    }
}
@Composable
fun ScanOptionsDialog(
    onDismiss: () -> Unit,
    onPickFile: () -> Unit,
    onLiveScan: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Source") },
        text = {
            Column {
                ListItem(
                    headlineContent = { Text("Live Scan") },
                    leadingContent = { Icon(Icons.Default.Add, null) },
                    modifier = Modifier.clickable { onLiveScan(); onDismiss() }
                )
                ListItem(
                    headlineContent = { Text("Choose from Files") },
                    leadingContent = { Icon(Icons.Default.Home, null) },
                    modifier = Modifier.clickable { onPickFile(); onDismiss() }
                )
            }
        },
        confirmButton = {}
    )
}