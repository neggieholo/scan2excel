package com.dragsville.scan2excel.scanManager
import org.bytedeco.tesseract.TessBaseAPI
import org.bytedeco.leptonica.global.lept.*

actual class OcrManager {
    actual suspend fun extractText(imageBytes: ByteArray): String {
        val api = TessBaseAPI()
        if (api.Init("tessdata", "eng") != 0) return "Error: Could not init Tesseract"

        // Convert Bytes to Leptonica PIX format
        val pix = pixReadMem(imageBytes, imageBytes.size.toLong())
        api.SetImage(pix)

        val text = api.GetUTF8Text().string

        api.End()
        pixDestroy(pix)
        return text
    }
}