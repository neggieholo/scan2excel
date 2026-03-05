package com.dragsville.scan2excel.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "scans")
data class ScanEntity(
    @PrimaryKey val id: Long,
    val filePath: String,
    val timestamp: Long
)

@Entity(tableName = "templates")
@Serializable // Useful if you ever need to pass this via Voyager or Ktor
data class TemplateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val fields: List<String>
)