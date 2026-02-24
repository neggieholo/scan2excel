package com.dragsville.scan2excel.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dragsville.scan2excel.data.models.ScanResult
import com.dragsville.scan2excel.data.repository.ScanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.time.Clock

class ScanViewModel(
    private val repository: ScanRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _scans = MutableStateFlow<List<ScanResult>>(emptyList())
    val scans: StateFlow<List<ScanResult>> = _scans

    fun loadScans() {
        viewModelScope.launch {
            _scans.value = repository.getAllScans()
        }
    }

    fun addFakeScan() {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        viewModelScope.launch {
            val scan = ScanResult(
                id = currentTime,
                filePath = "dummy/path.jpg",
                timestamp = currentTime
            )
            repository.saveScan(scan)
            loadScans()
        }
    }
}