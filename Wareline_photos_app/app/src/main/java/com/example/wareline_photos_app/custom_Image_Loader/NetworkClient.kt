package com.example.wareline_photos_app.custom_Image_Loader

import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object NetworkClient {
    fun downloadImage(url: String): ByteArray {
        var connection: HttpURLConnection? = null
        try {
            val urlConnection = URL(url).openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            connection = urlConnection

            val inputStream: InputStream = connection.inputStream
            val byteArrayOutputStream = ByteArrayOutputStream()

            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead)
            }

            val data = byteArrayOutputStream.toByteArray()
            Log.d("NetworkClient", "Received byte array of length: ${data.size}")
            if (data.isEmpty()) {
                Log.e("NetworkClient", "Received empty byte array from the network")
            }
            return data
        } catch (e: IOException) {
            Log.e("NetworkClient", "Error downloading image", e)
            throw e
        } finally {
            connection?.disconnect()
        }
    }
}
