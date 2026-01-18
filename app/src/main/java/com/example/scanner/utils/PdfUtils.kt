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

    fun mergePdfs(context: Context, pdfPaths: List<String>): File? {
        val outputPdf = PdfDocument()
        
        try {
            for (path in pdfPaths) {
                val file = File(path)
                if (!file.exists()) continue
                
                val fileDescriptor = android.os.ParcelFileDescriptor.open(file, android.os.ParcelFileDescriptor.MODE_READ_ONLY)
                val renderer = android.graphics.pdf.PdfRenderer(fileDescriptor)
                
                for (i in 0 until renderer.pageCount) {
                    val page = renderer.openPage(i)
                    
                    val pageInfo = PdfDocument.PageInfo.Builder(page.width, page.height, outputPdf.pages.size + 1).create()
                    val pdfPage = outputPdf.startPage(pageInfo)
                    
                    val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                    page.render(bitmap, null, null, android.graphics.pdf.PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    
                    pdfPage.canvas.drawBitmap(bitmap, 0f, 0f, null)
                    outputPdf.finishPage(pdfPage)
                    
                    bitmap.recycle()
                    page.close()
                }
                
                renderer.close()
                fileDescriptor.close()
            }
            
            val fileName = "Merged_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}.pdf"
            val file = File(context.getExternalFilesDir(null), fileName)
            
            outputPdf.writeTo(FileOutputStream(file))
            outputPdf.close()
            
            return file
            
        } catch (e: Exception) {
            e.printStackTrace()
            outputPdf.close()
            return null
        }
    }

    fun compressPdf(context: Context, pdfPath: String, qualityPercent: Int): File? {
        val outputPdf = PdfDocument()
        
        try {
            val file = File(pdfPath)
            if (!file.exists()) return null
            
            val fileDescriptor = android.os.ParcelFileDescriptor.open(file, android.os.ParcelFileDescriptor.MODE_READ_ONLY)
            val renderer = android.graphics.pdf.PdfRenderer(fileDescriptor)
            
            // Calculate scale based on quality (100% = 1.0, 50% = 0.5 size)
            // This effectively reduces resolution
            val scale = qualityPercent / 100f
            
            for (i in 0 until renderer.pageCount) {
                val page = renderer.openPage(i)
                
                // Create smaller page info
                val width = (page.width * scale).toInt()
                val height = (page.height * scale).toInt()
                
                val pageInfo = PdfDocument.PageInfo.Builder(width, height, outputPdf.pages.size + 1).create()
                val pdfPage = outputPdf.startPage(pageInfo)
                
                // Render at smaller size
                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                page.render(bitmap, null, null, android.graphics.pdf.PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                
                pdfPage.canvas.drawBitmap(bitmap, 0f, 0f, null)
                outputPdf.finishPage(pdfPage)
                
                bitmap.recycle()
                page.close()
            }
            
            renderer.close()
            fileDescriptor.close()
            
            val fileName = "Compressed_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}.pdf"
            val outfile = File(context.getExternalFilesDir(null), fileName)
            
            outputPdf.writeTo(FileOutputStream(outfile))
            outputPdf.close()
            
            return outfile
            
        } catch (e: Exception) {
            e.printStackTrace()
            outputPdf.close()
            return null
        }
    }
}
