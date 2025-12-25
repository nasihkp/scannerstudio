package com.example.scanner.ui.editor

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.scanner.databinding.ActivityEditorBinding
import java.io.File
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.min

class EditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditorBinding
    private var documentPath: String? = null
    private var pdfRenderer: PdfRenderer? = null
    private var currentPage: PdfRenderer.Page? = null
    private var parcelFileDescriptor: ParcelFileDescriptor? = null
    private var currentPageIndex = 0
    private var totalPages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        documentPath = intent.getStringExtra("DOCUMENT_PATH")

        if (documentPath != null) {
            displayPdfPage(0) // Show first page
        }

        binding.btnSave.setOnClickListener {
            saveWithOverlays()
        }
        
        // Page navigation
        binding.btnPrevPage.setOnClickListener {
            if (currentPageIndex > 0) {
                displayPdfPage(currentPageIndex - 1)
            }
        }
        
        binding.btnNextPage.setOnClickListener {
            if (currentPageIndex < totalPages - 1) {
                displayPdfPage(currentPageIndex + 1)
            }
        }
        
        // Add OCR Button dynamically
        val btnOcr = android.widget.Button(this).apply {
            text = "OCR"
            setOnClickListener { runOcr() }
        }
        binding.bottomToolbar.addView(btnOcr, 0)

        // Add Box Button
        val btnBox = android.widget.Button(this).apply {
            text = "Box"
            setOnClickListener { addItem(ItemType.BOX) }
        }
        binding.bottomToolbar.addView(btnBox, 1)

        // Add Table Button
        val btnTable = android.widget.Button(this).apply {
            text = "Table"
            setOnClickListener { addItem(ItemType.TABLE) }
        }
        binding.bottomToolbar.addView(btnTable, 2)
        
        binding.btnAddText.setOnClickListener { addItem(ItemType.TEXT) }
    }

    private fun addItem(type: ItemType) {
        val draggableItem = DraggableItemView(this, type)
        val params = FrameLayout.LayoutParams(400, 300)
        params.leftMargin = 100
        params.topMargin = 100
        draggableItem.layoutParams = params
        binding.overlayLayer.addView(draggableItem)
    }

    private fun runOcr() {
        if (currentPage == null) return
        
        val bitmap = Bitmap.createBitmap(currentPage!!.width, currentPage!!.height, Bitmap.Config.ARGB_8888)
        currentPage!!.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
            try {
                Toast.makeText(this@EditorActivity, "Running OCR...", Toast.LENGTH_SHORT).show()
                val result = withContext(kotlinx.coroutines.Dispatchers.Default) {
                    com.example.scanner.utils.OcrManager.processImage(bitmap)
                }
                
                // Populate overlays
                populateOverlaysFromOcr(result)
                
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@EditorActivity, "OCR Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun populateOverlaysFromOcr(text: com.google.mlkit.vision.text.Text) {
        // Clear existing overlays? Maybe ask user. For now, just add.
        // binding.overlayLayer.removeAllViews()

        val viewWidth = binding.ivPdfPage.width.toFloat()
        val viewHeight = binding.ivPdfPage.height.toFloat()
        val pdfWidth = currentPage!!.width.toFloat()
        val pdfHeight = currentPage!!.height.toFloat()

        // Calculate scale (fitCenter)
        val scaleX = viewWidth / pdfWidth
        val scaleY = viewHeight / pdfHeight
        val scale = minOf(scaleX, scaleY)
        
        val offsetX = (viewWidth - pdfWidth * scale) / 2
        val offsetY = (viewHeight - pdfHeight * scale) / 2

        for (block in text.textBlocks) {
            val rect = block.boundingBox ?: continue
            
            // Map PDF/Bitmap coords to Screen coords
            val screenX = rect.left * scale + offsetX
            val screenY = rect.top * scale + offsetY
            val screenW = rect.width() * scale
            val screenH = rect.height() * scale

            val draggableItem = DraggableItemView(this, ItemType.TEXT)
            draggableItem.editText?.setText(block.text)
            
            val params = FrameLayout.LayoutParams(screenW.toInt(), screenH.toInt())
            params.leftMargin = screenX.toInt()
            params.topMargin = screenY.toInt()
            draggableItem.layoutParams = params
            
            binding.overlayLayer.addView(draggableItem)
        }
    }

    private fun saveWithOverlays() {
        if (currentPage == null || documentPath == null) return

        try {
            // 1. Create a new PDF Document
            val newPdfDoc = android.graphics.pdf.PdfDocument()
            
            // 2. Create a Page description
            // We use the original page dimensions
            val pageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(currentPage!!.width, currentPage!!.height, 1).create()
            val newPage = newPdfDoc.startPage(pageInfo)
            val canvas = newPage.canvas

            // 3. Draw the original PDF page (as bitmap)
            // We need to render the current page to a bitmap first
            val bitmap = Bitmap.createBitmap(currentPage!!.width, currentPage!!.height, Bitmap.Config.ARGB_8888)
            currentPage!!.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            canvas.drawBitmap(bitmap, 0f, 0f, null)

            // 4. Draw Overlays
            // We need to map screen coordinates to PDF coordinates
            // The ImageView scales the bitmap to fit. We need the scale factor.
            val viewWidth = binding.ivPdfPage.width.toFloat()
            val viewHeight = binding.ivPdfPage.height.toFloat()
            val pdfWidth = currentPage!!.width.toFloat()
            val pdfHeight = currentPage!!.height.toFloat()

            // Calculate scale (fitCenter)
            val scaleX = viewWidth / pdfWidth
            val scaleY = viewHeight / pdfHeight
            val scale = minOf(scaleX, scaleY)
            
            // Calculate offsets (centering)
            val offsetX = (viewWidth - pdfWidth * scale) / 2
            val offsetY = (viewHeight - pdfHeight * scale) / 2

            for (i in 0 until binding.overlayLayer.childCount) {
                val child = binding.overlayLayer.getChildAt(i)
                if (child is DraggableItemView) {
                    if (child.itemType == ItemType.TEXT) {
                        val text = child.editText?.text.toString()
                        if (text.isNotEmpty()) {
                            // Map coordinates
                            val pdfX = (child.x - offsetX) / scale
                            val pdfY = (child.y - offsetY) / scale
                            
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.RED
                                textSize = 40f
                            }
                            canvas.drawText(text, pdfX, pdfY + 40f, paint)
                        }
                    } else if (child.itemType == ItemType.BOX) {
                        val pdfX = (child.x - offsetX) / scale
                        val pdfY = (child.y - offsetY) / scale
                        val pdfW = child.width / scale
                        val pdfH = child.height / scale
                        
                        val paint = android.graphics.Paint().apply {
                            style = android.graphics.Paint.Style.STROKE
                            strokeWidth = 5f
                            color = android.graphics.Color.RED
                        }
                        canvas.drawRect(pdfX, pdfY, pdfX + pdfW, pdfY + pdfH, paint)
                    }
                    // TODO: Implement Table drawing logic
                }
            }

            newPdfDoc.finishPage(newPage)

            // 5. Save file - REPLACE the original file
            val originalFile = File(documentPath!!)
            val tempFile = File(externalCacheDir, "temp_${System.currentTimeMillis()}.pdf")
            newPdfDoc.writeTo(java.io.FileOutputStream(tempFile))
            newPdfDoc.close()
            
            // Close renderer before replacing file
            currentPage?.close()
            pdfRenderer?.close()
            parcelFileDescriptor?.close()
            pdfRenderer = null
            parcelFileDescriptor = null
            
            // Replace original with temp
            tempFile.copyTo(originalFile, overwrite = true)
            tempFile.delete()

            Toast.makeText(this, "Changes saved successfully!", Toast.LENGTH_LONG).show()
            
            // Reload the PDF
            displayPdfPage(currentPageIndex)

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error saving: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayPdfPage(pageIndex: Int) {
        try {
            // Close current page if open
            currentPage?.close()
            
            if (pdfRenderer == null) {
                val file = File(documentPath!!)
                parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                pdfRenderer = PdfRenderer(parcelFileDescriptor!!)
                totalPages = pdfRenderer!!.pageCount
            }

            currentPageIndex = pageIndex
            currentPage = pdfRenderer?.openPage(pageIndex)
            
            val bitmap = Bitmap.createBitmap(
                currentPage!!.width, currentPage!!.height, Bitmap.Config.ARGB_8888
            )
            currentPage!!.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            
            binding.ivPdfPage.setImageBitmap(bitmap)
            
            // Update page info
            binding.tvPageInfo.text = "Page ${pageIndex + 1} of $totalPages"
            binding.btnPrevPage.isEnabled = pageIndex > 0
            binding.btnNextPage.isEnabled = pageIndex < totalPages - 1
            
            // Clear overlays when changing pages
            binding.overlayLayer.removeAllViews()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error opening PDF", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        currentPage?.close()
        pdfRenderer?.close()
        parcelFileDescriptor?.close()
    }
}
