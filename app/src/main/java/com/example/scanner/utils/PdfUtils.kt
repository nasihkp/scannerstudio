package com.example.scanner.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfDocument
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PdfUtils {

    fun createPdfFromImages(context: Context, imagePaths: List<String>): File? {
        val pdfDocument = PdfDocument()
        
        for (path in imagePaths) {
            val bitmap = BitmapFactory.decodeFile(path) ?: continue
            val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas
            canvas.drawBitmap(bitmap, 0f, 0f, null)
            pdfDocument.finishPage(page)
        }
        
        val fileName = "Scan_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}.pdf"
        val file = File(context.getExternalFilesDir(null), fileName)
        
        try {
            pdfDocument.writeTo(FileOutputStream(file))
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            pdfDocument.close()
        }
        
        return file
    }

    // Keep old method for compatibility or redirect
    fun createPdfFromImage(context: Context, imagePath: String): File? {
        return createPdfFromImages(context, listOf(imagePath))
    }
}
