package com.example.wareline_photos_app.custom_Image_Loader

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.example.wareline_photos_app.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class CustomImageLoader(private val context: Context) {

    private val memoryCache = MemoryCache
    private val diskCache = DiskCache(context)

    fun loadImage(url: String, imageView: ImageView) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val cachedData = memoryCache.get(url) ?: diskCache.getImage(url)
                if (cachedData != null) {
                    val bitmap = ImageUtils.byteArrayToBitmap(cachedData)
                    ImageUtils.setBitmapToImageView(imageView, bitmap)
                } else {
                    val data = withContext(Dispatchers.IO) {
                        try {
                            NetworkClient.downloadImage(url)
                        } catch (e: IOException) {
                            Log.e("CustomImageLoader", "Error downloading image", e)
                            ByteArray(0)
                        }
                    }
                    if (data.isNotEmpty()) {
                        val bitmap = ImageUtils.byteArrayToBitmap(data)
                        ImageUtils.setBitmapToImageView(imageView, bitmap)
                        if (bitmap != null) {
                            memoryCache.put(url, data)
                            diskCache.saveImage(url, data)
                        } else {
                            Log.e("CustomImageLoader", "Failed to convert downloaded data to bitmap")
                            imageView.setImageResource(R.drawable.baseline_error_24)
                        }
                    } else {
                        Log.e("CustomImageLoader", "Downloaded data is empty")
                        imageView.setImageResource(R.drawable.baseline_error_24)
                    }
                }
            } catch (e: IOException) {
                Log.e("CustomImageLoader", "Error loading image", e)
                imageView.setImageResource(R.drawable.baseline_error_24)
            }
        }
    }
}
