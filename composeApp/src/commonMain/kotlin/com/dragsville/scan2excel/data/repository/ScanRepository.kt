package com.dragsville.scan2excel.data.repository
import com.dragsville.scan2excel.data.models.ScanResult
import kotlinx.coroutines.flow.Flow

interface ScanRepository {
    suspend fun saveScan(result: ScanResult)
    fun getAllScans(): Flow<List<ScanResult>>
}