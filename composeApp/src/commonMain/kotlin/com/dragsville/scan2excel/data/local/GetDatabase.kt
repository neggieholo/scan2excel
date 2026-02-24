package com.dragsville.scan2excel.data.local

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

fun createDatabase(builder: RoomDatabase.Builder<ScanDatabase>): ScanDatabase {
    return builder
        .setDriver(BundledSQLiteDriver()) // Use the 2026 standard driver
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}