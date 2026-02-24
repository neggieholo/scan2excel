package com.dragsville.scan2excel.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scans")
data class ScanEntity(
    @PrimaryKey val id: Long,
    val filePath: String,
    val timestamp: Long
)
