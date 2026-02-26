@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.dragsville.scan2excel.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(entities = [ScanEntity::class], version = 1, exportSchema = false)
@ConstructedBy(ScanDatabaseConstructor::class)
abstract class ScanDatabase : RoomDatabase() {
    abstract fun scanDao(): ScanDao
}

// Room uses this to generate the actual platform implementation
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object ScanDatabaseConstructor : RoomDatabaseConstructor<ScanDatabase> {
    override fun initialize(): ScanDatabase
}