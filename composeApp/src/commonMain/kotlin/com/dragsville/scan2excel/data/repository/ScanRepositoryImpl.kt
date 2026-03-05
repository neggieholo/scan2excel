package com.dragsville.scan2excel.data.repository

import com.dragsville.scan2excel.data.local.ScanDao
import com.dragsville.scan2excel.data.local.ScanEntity
import com.dragsville.scan2excel.data.local.TemplateEntity
import com.dragsville.scan2excel.data.models.ScanResult
import com.dragsville.scan2excel.networking.ApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ScanRepositoryImpl(
    private val dao: ScanDao,
    private val apiClient: ApiClient
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

    override suspend fun saveTemplate(name: String, fields: List<String>) {
        dao.insertTemplate(TemplateEntity(name = name, fields = fields))
    }

    override fun getAllTemplates(): Flow<List<TemplateEntity>> {
        return dao.getAllTemplates()
    }

    override suspend fun deleteTemplate(id: Long) {
        dao.deleteTemplate(id)
    }
}