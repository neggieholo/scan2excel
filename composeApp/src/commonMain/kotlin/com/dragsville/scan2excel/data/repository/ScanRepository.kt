package com.dragsville.scan2excel.data.repository
import com.dragsville.scan2excel.data.local.TemplateEntity
import com.dragsville.scan2excel.data.models.ScanResult
import kotlinx.coroutines.flow.Flow

interface ScanRepository {
    suspend fun saveScan(result: ScanResult)
    fun getAllScans(): Flow<List<ScanResult>>
    suspend fun saveTemplate(name: String, fields: List<String>)
    fun getAllTemplates(): Flow<List<TemplateEntity>>

    suspend fun deleteTemplate(id: Long)
}