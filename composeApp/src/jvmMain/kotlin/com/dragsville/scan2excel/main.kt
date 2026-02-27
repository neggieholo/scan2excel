package com.dragsville.scan2excel

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.dragsville.scan2excel.data.local.ScanContainer
import com.dragsville.scan2excel.data.local.getDatabaseBuilder
import com.dragsville.scan2excel.ui.ScanViewModelProvider
import io.ktor.client.engine.okhttp.OkHttp

fun main() = application {
    val builder = getDatabaseBuilder()
    // 2. Hand it to the same shared container
    val container = ScanContainer(builder, OkHttp.create())
    ScanViewModelProvider.container = container

    Window(
        onCloseRequest = ::exitApplication,
        title = "Scan2Excel",
    ) {
        App()
    }
}
