package com.dragsville.scan2excel.data.repository

import com.dragsville.scan2excel.data.local.ScanDao
import com.dragsville.scan2excel.data.local.ScanEntity
import com.dragsville.scan2excel.data.models.ScanResult

class ScanRepositoryImpl(
    private val dao: ScanDao
) : ScanRepository {

    override suspend fun saveScan(result: ScanResult) {
        dao.insert(
            ScanEntity(
                id = result.id,
                filePath = result.filePath,
                timestamp = result.timestamp
            )
        )
    }

    override suspend fun getAllScans(): List<ScanResult> {
        return dao.getAll().map {
            ScanResult(
                id = it.id,
                filePath = it.filePath,
                timestamp = it.timestamp
            )
        }
    }
}