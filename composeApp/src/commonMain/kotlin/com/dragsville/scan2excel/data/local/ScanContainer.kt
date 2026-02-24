package com.dragsville.scan2excel.data.local

import androidx.room.RoomDatabase
import com.dragsville.scan2excel.data.repository.ScanRepository
import com.dragsville.scan2excel.data.repository.ScanRepositoryImpl


class ScanContainer(
    // We pass the platform-specific builder in here!
    databaseBuilder: RoomDatabase.Builder<ScanDatabase>
) {
    // 1. This calls your 'createDatabase' function to finish the handshake
    private val database: ScanDatabase = createDatabase(databaseBuilder)

    // 2. This provides the repository to the rest of the app
    val scanRepository: ScanRepository by lazy {
        ScanRepositoryImpl(database.scanDao())
    }
}