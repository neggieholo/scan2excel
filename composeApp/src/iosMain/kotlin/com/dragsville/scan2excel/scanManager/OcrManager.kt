package com.dragsville.scan2excel.scanManager
import platform.Vision.*
import platform.UIKit.*

actual class OcrManager {
    actual suspend fun extractText(imageBytes: ByteArray): String {
        return "iOS OCR Logic ready for Vision Framework"
    }
}