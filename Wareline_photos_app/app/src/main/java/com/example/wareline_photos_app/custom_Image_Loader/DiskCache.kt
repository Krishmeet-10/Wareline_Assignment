package com.example.wareline_photos_app.custom_Image_Loader

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class DiskCache(context: Context) {
    private val cacheDir = File(context.cacheDir, "image_cache").apply {
        if (!exists()) mkdirs()
    }

    fun saveImage(url: String, data: ByteArray) {
        val file = File(cacheDir, url.hashCode().toString())
        FileOutputStream(file).use { output ->
            output.write(data)
        }
    }

    fun getImage(url: String): ByteArray? {
        val file = File(cacheDir, url.hashCode().toString())
        return if (file.exists()) file.readBytes() else null
    }
}
