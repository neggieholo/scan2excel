package com.dragsville.scan2excel.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
fun getIosDatabaseBuilder(): RoomDatabase.Builder<ScanDatabase> {
    val documentDirectory = NSFileManager.defaultManager()
        .URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
    val path = documentDirectory?.path + "/scan_database.db"

    return Room.databaseBuilder<ScanDatabase>(
        name = path,
        factory = { ScanDatabaseConstructor.initialize() } // This is why we need the 'expect' object!
    )
}