package com.example.wareline_photos_app.custom_Image_Loader

import android.util.LruCache

object MemoryCache {
    private val cache = LruCache<String, ByteArray>(1024 * 1024 * 10)

    fun put(key: String, data: ByteArray) {
        cache.put(key, data)
    }

    fun get(key: String): ByteArray? {
        return cache.get(key)
    }
}
