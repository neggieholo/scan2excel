package com.dragsville.scan2excel.data.repository
import com.dragsville.scan2excel.data.models.ScanResult

interface ScanRepository {
    suspend fun saveScan(result: ScanResult)
    suspend fun getAllScans(): List<ScanResult>
}