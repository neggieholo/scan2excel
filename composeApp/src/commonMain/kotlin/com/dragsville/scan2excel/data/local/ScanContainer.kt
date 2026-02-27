package com.dragsville.scan2excel.data.local

import androidx.room.RoomDatabase
import com.dragsville.scan2excel.data.repository.ScanRepository
import com.dragsville.scan2excel.data.repository.ScanRepositoryImpl
import com.dragsville.scan2excel.networking.ApiClient
import com.dragsville.scan2excel.networking.createHttpClient
import io.ktor.client.engine.HttpClientEngine


class ScanContainer(
    databaseBuilder: RoomDatabase.Builder<ScanDatabase>,
    engine: HttpClientEngine
) {
    private val httpClient = createHttpClient(engine)
    private val database: ScanDatabase = createDatabase(databaseBuilder)

    val apiClient: ApiClient by lazy {
        ApiClient(httpClient)
    }
    val scanRepository: ScanRepository by lazy {
        ScanRepositoryImpl(database.scanDao(), apiClient)
    }
}