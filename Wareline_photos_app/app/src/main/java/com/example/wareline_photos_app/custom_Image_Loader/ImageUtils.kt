package com.example.wareline_photos_app.custom_Image_Loader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.example.wareline_photos_app.R
import java.io.ByteArrayInputStream
import java.io.InputStream

object ImageUtils {
    fun byteArrayToBitmap(data: ByteArray?): Bitmap? {
        if (data == null || data.isEmpty()) {
            Log.e("ImageUtils", "Received empty or null byte array")
            return null
        }

        Log.d("ImageUtils", "Attempting to decode byte array of length: ${data.size}")
        val inputStream: InputStream = ByteArrayInputStream(data)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        if (bitmap == null) {
            Log.e("ImageUtils", "Failed to decode byte array to bitmap")
        }
        return bitmap
    }

    fun setBitmapToImageView(imageView: ImageView, bitmap: Bitmap?) {
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
        } else {
            imageView.setImageResource(R.drawable.baseline_error_24)
        }
    }
}
