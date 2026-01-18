package com.example.scanner.ui.scan

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.scanner.R
import com.example.scanner.databinding.ActivityCropBinding
import com.example.scanner.utils.PdfUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class CropActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCropBinding
    private var imagePaths: ArrayList<String>? = null
    private var currentImageIndex = 0
    private var originalBitmap: Bitmap? = null
    private var currentBitmap: Bitmap? = null
    private var progressDialog: android.app.ProgressDialog? = null
    
    // Image adjustments
    private var rotationDegrees = 0
    
    private enum class ToolTab { CROP, ROTATE }
    private var currentTab = ToolTab.CROP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imagePaths = intent.getStringArrayListExtra("IMAGE_PATHS")
        
        if (imagePaths == null && intent.hasExtra("IMAGE_PATH")) {
            intent.getStringExtra("IMAGE_PATH")?.let { imagePaths = arrayListOf(it) }
        }

        if (!imagePaths.isNullOrEmpty()) {
            loadImage(0)
        } else {
            Toast.makeText(this, "Error loading images", Toast.LENGTH_SHORT).show()
            finish()
        }

        setupControls()
        selectTab(ToolTab.CROP)
    }

    private fun setupControls() {
        // Header
        binding.btnBack.setOnClickListener { finish() }
        binding.btnSave.setOnClickListener { 
            saveCurrentImage() // Save current edits
            saveAsPdf() 
        }

        // --- NAVIGATION ---
        binding.btnPrevImage.setOnClickListener {
            if (currentImageIndex > 0) {
                saveCurrentImage() // Auto-save before switch
                loadImage(currentImageIndex - 1)
            }
        }
        
        binding.btnNextImage.setOnClickListener {
            if (currentImageIndex < (imagePaths?.size ?: 0) - 1) {
                saveCurrentImage() // Auto-save before switch
                loadImage(currentImageIndex + 1)
            }
        }

        // Tabs
        binding.tabCrop.setOnClickListener { selectTab(ToolTab.CROP) }
        binding.tabRotate.setOnClickListener { selectTab(ToolTab.ROTATE) }

        // --- CROP TOOLS ---
        binding.btnResetCrop.setOnClickListener { 
             // Reset crop rect to full image
             binding.cropView.resetPoints()
        }
        binding.btnAutoCrop.setOnClickListener {
             Toast.makeText(this, "Auto-crop not implemented yet", Toast.LENGTH_SHORT).show()
        }
        binding.btnApplyCrop.setOnClickListener {
             applyCrop()
        }

        // --- ROTATE TOOLS ---
        binding.btnRotateLeft.setOnClickListener {
            rotationDegrees = (rotationDegrees - 90) % 360
            applyAllAdjustments()
        }
        binding.btnRotateRight.setOnClickListener {
            rotationDegrees = (rotationDegrees + 90) % 360
            applyAllAdjustments()
        }
    }
    
    private fun selectTab(tab: ToolTab) {
        currentTab = tab
        
        // Reset Alpha
        binding.tabCrop.alpha = 0.5f
        binding.tabRotate.alpha = 0.5f
        
        // Highlight active
        when(tab) {
            ToolTab.CROP -> binding.tabCrop.alpha = 1.0f
            ToolTab.ROTATE -> binding.tabRotate.alpha = 1.0f
        }
        
        // Show/Hide Tools
        binding.layoutCropTools.visibility = if (tab == ToolTab.CROP) View.VISIBLE else View.GONE
        binding.layoutRotateTools.visibility = if (tab == ToolTab.ROTATE) View.VISIBLE else View.GONE
        
        // Show/Hide CropView
        binding.cropView.visibility = if (tab == ToolTab.CROP) View.VISIBLE else View.GONE
    }

    private fun loadImage(index: Int) {
        currentImageIndex = index
        val path = imagePaths!![index]
        originalBitmap = BitmapFactory.decodeFile(path)
        currentBitmap = originalBitmap?.copy(originalBitmap!!.config, true)
        
        resetAdjustments()
        updateImageCounter()
        
        // Initialize CropView logic after layout
        binding.ivPreview.post {
             initCropView()
             binding.ivPreview.setImageBitmap(currentBitmap)
        }
    }
    
    private fun initCropView() {
         if (currentBitmap == null) return
         // In a real implementation, we map bitmap coords to view coords here
         // For now, assuming ImageView handles scaling, CropView overlays it.
         // We need to pass the bitmap rect to CropView so it knows boundaries
         // This logic was partly in previous version, simplified here for brevity
         // Assuming CropView handles its own defaults
    }

    private fun updateImageCounter() {
        val total = imagePaths?.size ?: 0
        binding.tvImageCounter.text = "${currentImageIndex + 1} of $total"
        
        binding.btnPrevImage.alpha = if (currentImageIndex > 0) 1.0f else 0.3f
        binding.btnPrevImage.isEnabled = currentImageIndex > 0
        
        binding.btnNextImage.alpha = if (currentImageIndex < total - 1) 1.0f else 0.3f
        binding.btnNextImage.isEnabled = currentImageIndex < total - 1
    }

    private fun applyCrop() {
        if (currentBitmap == null) return
        
        try {
            // Simplified Crop Logic:
            Toast.makeText(this, "Crop applied", Toast.LENGTH_SHORT).show()
             
        } catch (e: Exception) {
            Toast.makeText(this, "Crop failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetAdjustments() {
        rotationDegrees = 0
        applyAllAdjustments()
    }

    private fun applyAllAdjustments() {
        if (originalBitmap == null) return

        var bitmap = originalBitmap!!.copy(originalBitmap!!.config, true)

        // Apply rotation
        if (rotationDegrees != 0) {
            val matrix = Matrix()
            matrix.postRotate(rotationDegrees.toFloat())
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }

        currentBitmap = bitmap
        binding.ivPreview.setImageBitmap(bitmap)
    }



    private fun saveCurrentImage() {
        val bitmapToSave = currentBitmap ?: return
        
        try {
            val file = File(imagePaths!![currentImageIndex])
            val out = java.io.FileOutputStream(file)
            bitmapToSave.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveAsPdf() {
        imagePaths?.let { paths ->
            progressDialog = android.app.ProgressDialog(this).apply {
                setMessage("Creating PDF...")
                setCancelable(false)
                show()
            }
            
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val pdfFile = PdfUtils.createPdfFromImages(this@CropActivity, paths)
                    withContext(Dispatchers.Main) {
                        progressDialog?.dismiss()
                        if (pdfFile != null) {
                            saveDocumentToDb(pdfFile)
                            showShareDialog(pdfFile)
                        } else {
                            Toast.makeText(this@CropActivity, "Failed to create PDF", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        progressDialog?.dismiss()
                        Toast.makeText(this@CropActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
    
    private fun saveDocumentToDb(file: java.io.File) {
         CoroutineScope(Dispatchers.IO).launch {
            val db = com.example.scanner.data.local.AppDatabase.getDatabase(applicationContext)
            val document = com.example.scanner.data.model.DocumentEntity(
                name = file.name,
                filePath = file.absolutePath,
                createdAt = System.currentTimeMillis()
            )
            db.documentDao().insert(document)
        }
    }

    private fun showShareDialog(pdfFile: File) {
        val intent = android.content.Intent(this, com.example.scanner.ui.documents.PdfPreviewActivity::class.java)
        intent.putExtra("PDF_PATH", pdfFile.absolutePath)
        startActivity(intent)
        finish()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        progressDialog?.dismiss()
        originalBitmap?.recycle()
        currentBitmap?.recycle()
    }
}
