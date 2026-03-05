package com.dragsville.scan2excel.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dragsville.scan2excel.data.models.ScanResult
import com.dragsville.scan2excel.data.repository.ScanRepository
import com.dragsville.scan2excel.scanManager.OcrManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Clock

class ScanViewModel(
    private val repository: ScanRepository,
    private val savedStateHandle: SavedStateHandle? = null
) : ViewModel() {

    private val ocrManager = OcrManager()
    private val _scans: StateFlow<List<ScanResult>> = repository.getAllScans()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val scans: StateFlow<List<ScanResult>> = _scans

    private val _ocrDialogText = MutableStateFlow<String?>(null)
    val ocrDialogText: StateFlow<String?> = _ocrDialogText

    private val _templateFields = MutableStateFlow(listOf(""))
    val templateFields: StateFlow<List<String>> = _templateFields

    private val _templateName = MutableStateFlow("")
    val templateName: StateFlow<String> = _templateName

    fun processImage(imageBytes: ByteArray) {
        viewModelScope.launch {
            try {
                // This calls your platform-specific OCR
                val result = ocrManager.extractText(imageBytes)
                _ocrDialogText.value = result // Trigger the dialog
            } catch (e: Exception) {
                _ocrDialogText.value = "Error: ${e.message}"
            }
        }
    }
    fun dismissOcrDialog() {
        _ocrDialogText.value = null
    }

    fun updateTemplateName(name: String) {
        _templateName.value = name
    }

    fun addFieldInput() {
        _templateFields.value = _templateFields.value + ""
    }

    fun updateField(index: Int, newValue: String) {
        val currentList = _templateFields.value.toMutableList()
        currentList[index] = newValue
        _templateFields.value = currentList
    }

    fun removeField(index: Int) {
        val currentList = _templateFields.value.toMutableList()
        if (currentList.size > 1) {
            currentList.removeAt(index)
            _templateFields.value = currentList
        }
    }

    fun saveNewTemplate() {
        viewModelScope.launch {
            val fieldsToSave = _templateFields.value.filter { it.isNotBlank() }
            if (_templateName.value.isNotBlank() && fieldsToSave.isNotEmpty()) {
                repository.saveTemplate(_templateName.value, fieldsToSave)
                // Reset after saving
                _templateName.value = ""
                _templateFields.value = listOf("")
            }
        }
    }

    fun deleteTemplate(id: Long) {
        viewModelScope.launch {
            repository.deleteTemplate(id)
        }
    }
}