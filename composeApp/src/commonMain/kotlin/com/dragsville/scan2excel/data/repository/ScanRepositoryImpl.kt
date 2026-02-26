package com.dragsville.scan2excel.data.repository

import com.dragsville.scan2excel.data.local.ScanDao
import com.dragsville.scan2excel.data.local.ScanEntity
import com.dragsville.scan2excel.data.models.ScanResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override fun getAllScans(): Flow<List<ScanResult>> {
        return dao.getAll().map { entityList ->
            entityList.map { entity ->
                ScanResult(
                    id = entity.id,
                    filePath = entity.filePath,
                    timestamp = entity.timestamp
                )
            }
        }
    }
}