package com.example.scanner.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object GalleryUtils {

    fun copyUriToCache(context: Context, uri: Uri): String? {
        return try {
            val contentResolver = context.contentResolver
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            
            if (inputStream != null) {
                val fileName = "Share_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}.jpg"
                val cacheDir = context.externalCacheDir ?: context.cacheDir
                val file = File(cacheDir, fileName)
                
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                
                inputStream.close()
                outputStream.close()
                
                file.absolutePath
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
