package com.dragsville.scan2excel.scanManager

expect class OcrManager() {
    suspend fun extractText(imageBytes: ByteArray): String
}