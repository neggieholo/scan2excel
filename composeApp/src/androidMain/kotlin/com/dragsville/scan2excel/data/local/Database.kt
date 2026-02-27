package com.dragsville.scan2excel.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<ScanDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("scan_database.db")
    return Room.databaseBuilder<ScanDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
