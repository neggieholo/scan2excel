package com.dragsville.scan2excel.ui

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dragsville.scan2excel.data.local.ScanContainer
import com.dragsville.scan2excel.ui.viewmodel.ScanViewModel

// In commonMain
object ScanViewModelProvider {
    // This will be set by each platform's entry point
    lateinit var container: ScanContainer

    val Factory = viewModelFactory {
        initializer {
            ScanViewModel(
                repository = container.scanRepository, // Pull from the shared container
                savedStateHandle = this.createSavedStateHandle()
            )
        }
    }
}