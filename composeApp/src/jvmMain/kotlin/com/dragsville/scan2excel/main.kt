package com.dragsville.scan2excel

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.dragsville.scan2excel.data.local.ScanContainer
import com.dragsville.scan2excel.data.local.getDatabaseBuilder
import com.dragsville.scan2excel.ui.ScanViewModelProvider
import io.ktor.client.engine.okhttp.OkHttp
import org.jetbrains.compose.resources.painterResource
import scan2excel.composeapp.generated.resources.Res
import scan2excel.composeapp.generated.resources.app_logo

fun main() = application {
    val builder = getDatabaseBuilder()
    // 2. Hand it to the same shared container
    val container = ScanContainer(builder, OkHttp.create())
    ScanViewModelProvider.container = container

    Window(
        onCloseRequest = ::exitApplication,
        title = "Scan2Excel",
        icon = painterResource(Res.drawable.app_logo)
    ) {
        App()
    }
}
