package com.dragsville.scan2excel.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanDao {

    @Insert
    suspend fun insert(scan: ScanEntity)

    @Query("SELECT * FROM scans ORDER BY timestamp DESC")
    fun getAll(): Flow<List<ScanEntity>>

    @Insert suspend fun insertTemplate(template: TemplateEntity)

    @Query("SELECT * FROM templates")
    fun getAllTemplates(): Flow<List<TemplateEntity>>

    @Query("DELETE FROM templates WHERE id = :id")
    suspend fun deleteTemplate(id: Long)
}