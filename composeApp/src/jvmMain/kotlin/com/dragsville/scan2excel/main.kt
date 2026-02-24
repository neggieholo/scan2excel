package com.dragsville.scan2excel

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.dragsville.scan2excel.data.local.ScanContainer
import com.dragsville.scan2excel.data.local.getDatabaseBuilder

fun main() = application {
    val builder = getDatabaseBuilder()
    // 2. Hand it to the same shared container
    val container = ScanContainer(builder)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Scan2Excel",
    ) {
        App()
    }
}