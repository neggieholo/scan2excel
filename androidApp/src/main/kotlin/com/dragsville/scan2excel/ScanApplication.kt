package com.dragsville.scan2excel

import android.app.Application
import com.dragsville.scan2excel.data.local.ScanContainer
import com.dragsville.scan2excel.data.local.getDatabaseBuilder
import com.dragsville.scan2excel.ui.ScanViewModelProvider
import io.ktor.client.engine.okhttp.OkHttp

class ScanApplication : Application() {
    lateinit var container: ScanContainer

    override fun onCreate() {
        super.onCreate()
        // Here is the call!
        // 1. Get the Android builder
        val builder = getDatabaseBuilder(this)
        // 2. Hand it to the shared container
        container = ScanContainer(builder,OkHttp.create())
        
        ScanViewModelProvider.container = container
    }
}
