package com.dragsville.scan2excel.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<ScanDatabase> {
    val dbFile = File(System.getProperty("user.home"), "scan_database.db")
    return Room.databaseBuilder<ScanDatabase>(
        name = dbFile.absolutePath
    )
}