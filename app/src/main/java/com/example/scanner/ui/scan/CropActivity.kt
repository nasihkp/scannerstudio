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
    private var brightness = 0 // -100 to +100
    private var contrast = 1.0f // 0.5 to 2.0
    private var currentFilter = FilterType.ORIGINAL
    
    // Workflow state
    private var isCropMode = true

    enum class FilterType {
        ORIGINAL, GRAYSCALE, BW, SEPIA
    }

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
    }

    private fun setupControls() {
        // Crop controls
        binding.btnApplyCrop.setOnClickListener {
            applyCrop()
        }
        
        binding.btnSkipCrop.setOnClickListener {
            skipCrop()
        }

        // Rotation buttons
        binding.btnRotateLeft.setOnClickListener {
            rotationDegrees = (rotationDegrees - 90) % 360
            applyAllAdjustments()
        }

        binding.btnRotateRight.setOnClickListener {
            rotationDegrees = (rotationDegrees + 90) % 360
            applyAllAdjustments()
        }

        // Brightness slider
        binding.seekBrightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                brightness = progress - 100
                if (fromUser) applyAllAdjustments()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Contrast slider
        binding.seekContrast.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                contrast = progress / 100f
                if (fromUser) applyAllAdjustments()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Filter chips
        binding.chipGroupFilters.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                currentFilter = when (checkedIds[0]) {
                    binding.chipGrayscale.id -> FilterType.GRAYSCALE
                    binding.chipBW.id -> FilterType.BW
                    binding.chipSepia.id -> FilterType.SEPIA
                    else -> FilterType.ORIGINAL
                }
                applyAllAdjustments()
            }
        }

        // Reset button
        binding.btnReset.setOnClickListener {
            resetAdjustments()
        }

        // Navigation buttons
        binding.btnPrevious.setOnClickListener {
            if (currentImageIndex > 0) {
                saveCurrentImage()
                loadImage(currentImageIndex - 1)
            }
        }

        binding.btnNext.setOnClickListener {
            saveCurrentImage()
            if (currentImageIndex < (imagePaths?.size ?: 0) - 1) {
                loadImage(currentImageIndex + 1)
            } else {
                // Last image - show finish button
                Toast.makeText(this, "All images edited!", Toast.LENGTH_SHORT).show()
            }
        }
        
        // Create PDF button
        binding.btnCreatePdf.setOnClickListener {
            saveCurrentImage()
            saveAsPdf()
        }
    }

    private fun loadImage(index: Int) {
        currentImageIndex = index
        val path = imagePaths!![index]
        originalBitmap = BitmapFactory.decodeFile(path)
        currentBitmap = originalBitmap?.copy(originalBitmap!!.config, true)
        
        // Reset to crop mode for new image
        isCropMode = true
        showCropMode()
        
        resetAdjustments()
        updateImageCounter()
        updateNavigationButtons()
    }

    private fun updateImageCounter() {
        val total = imagePaths?.size ?: 0
        binding.tvImageCounter.text = "Image ${currentImageIndex + 1} of $total"
    }

    private fun updateNavigationButtons() {
        val total = imagePaths?.size ?: 0
        
        // Previous button
        binding.btnPrevious.isEnabled = currentImageIndex > 0
        
        // Next button text
        if (currentImageIndex < total - 1) {
            binding.btnNext.text = "Next →"
            binding.btnCreatePdf.visibility = View.GONE
        } else {
            binding.btnNext.text = "Save"
            binding.btnCreatePdf.visibility = View.VISIBLE
        }
    }

    private fun showCropMode() {
        isCropMode = true
        binding.cropView.visibility = View.VISIBLE
        binding.cropControls.visibility = View.VISIBLE
        binding.editControls.visibility = View.GONE
        
        // Display original image
        binding.ivPreview.setImageBitmap(originalBitmap)
    }

    private fun showEditMode() {
        isCropMode = false
        binding.cropView.visibility = View.GONE
        binding.cropControls.visibility = View.GONE
        binding.editControls.visibility = View.VISIBLE
        
        // Apply current adjustments
        applyAllAdjustments()
    }

    private fun applyCrop() {
        if (originalBitmap == null) return
        
        try {
            val cropRect = binding.cropView.getCropRect()
            
            // Calculate crop rectangle relative to bitmap size
            val imageView = binding.ivPreview
            val viewWidth = imageView.width.toFloat()
            val viewHeight = imageView.height.toFloat()
            
            val bitmap = originalBitmap!!
            val bitmapWidth = bitmap.width.toFloat()
            val bitmapHeight = bitmap.height.toFloat()
            
            // Calculate scale factor (image is fitCenter in ImageView)
            val scale = Math.min(viewWidth / bitmapWidth, viewHeight / bitmapHeight)
            val scaledWidth = bitmapWidth * scale
            val scaledHeight = bitmapHeight * scale
            
            // Calculate offset (image is centered)
            val offsetX = (viewWidth - scaledWidth) / 2
            val offsetY = (viewHeight - scaledHeight) / 2
            
            // Convert crop rect from view coordinates to bitmap coordinates
            val x = ((cropRect.left - offsetX) / scale).toInt().coerceAtLeast(0)
            val y = ((cropRect.top - offsetY) / scale).toInt().coerceAtLeast(0)
            val width = ((cropRect.width()) / scale).toInt().coerceAtMost(bitmap.width - x)
            val height = ((cropRect.height()) / scale).toInt().coerceAtMost(bitmap.height - y)
            
            if (width > 0 && height > 0) {
                val cropped = Bitmap.createBitmap(bitmap, x, y, width, height)
                originalBitmap = cropped
                currentBitmap = cropped.copy(cropped.config, true)
                
                Toast.makeText(this, "Crop applied", Toast.LENGTH_SHORT).show()
                showEditMode()
            } else {
                Toast.makeText(this, "Invalid crop area", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Crop failed: ${e.message}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun skipCrop() {
        Toast.makeText(this, "Crop skipped", Toast.LENGTH_SHORT).show()
        showEditMode()
    }

    private fun resetAdjustments() {
        rotationDegrees = 0
        brightness = 0
        contrast = 1.0f
        currentFilter = FilterType.ORIGINAL
        
        binding.seekBrightness.progress = 100
        binding.seekContrast.progress = 100
        binding.chipOriginal.isChecked = true
        
        applyAllAdjustments()
    }

    private fun applyAllAdjustments() {
        if (originalBitmap == null || isCropMode) return

        var bitmap = originalBitmap!!.copy(originalBitmap!!.config, true)

        // Apply rotation
        if (rotationDegrees != 0) {
            val matrix = Matrix()
            matrix.postRotate(rotationDegrees.toFloat())
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }

        // Apply brightness and contrast
        if (brightness != 0 || contrast != 1.0f) {
            bitmap = applyBrightnessContrast(bitmap, brightness, contrast)
        }

        // Apply filter
        bitmap = when (currentFilter) {
            FilterType.GRAYSCALE -> applyGrayscale(bitmap)
            FilterType.BW -> applyBlackAndWhite(bitmap)
            FilterType.SEPIA -> applySepia(bitmap)
            else -> bitmap
        }

        currentBitmap = bitmap
        binding.ivPreview.setImageBitmap(bitmap)
    }

    private fun applyBrightnessContrast(source: Bitmap, brightness: Int, contrast: Float): Bitmap {
        val cm = ColorMatrix(floatArrayOf(
            contrast, 0f, 0f, 0f, brightness.toFloat(),
            0f, contrast, 0f, 0f, brightness.toFloat(),
            0f, 0f, contrast, 0f, brightness.toFloat(),
            0f, 0f, 0f, 1f, 0f
        ))
        
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(cm)
        
        val result = Bitmap.createBitmap(source.width, source.height, source.config)
        val canvas = Canvas(result)
        canvas.drawBitmap(source, 0f, 0f, paint)
        
        return result
    }

    private fun applyGrayscale(source: Bitmap): Bitmap {
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(cm)
        
        val result = Bitmap.createBitmap(source.width, source.height, source.config)
        val canvas = Canvas(result)
        canvas.drawBitmap(source, 0f, 0f, paint)
        
        return result
    }

    private fun applyBlackAndWhite(source: Bitmap): Bitmap {
        val cm = ColorMatrix(floatArrayOf(
            1.5f, 1.5f, 1.5f, 0f, -255f,
            1.5f, 1.5f, 1.5f, 0f, -255f,
            1.5f, 1.5f, 1.5f, 0f, -255f,
            0f, 0f, 0f, 1f, 0f
        ))
        
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(cm)
        
        val result = Bitmap.createBitmap(source.width, source.height, source.config)
        val canvas = Canvas(result)
        canvas.drawBitmap(source, 0f, 0f, paint)
        
        return result
    }

    private fun applySepia(source: Bitmap): Bitmap {
        val cm = ColorMatrix()
        cm.set(floatArrayOf(
            0.393f, 0.769f, 0.189f, 0f, 0f,
            0.349f, 0.686f, 0.168f, 0f, 0f,
            0.272f, 0.534f, 0.131f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        ))
        
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(cm)
        
        val result = Bitmap.createBitmap(source.width, source.height, source.config)
        val canvas = Canvas(result)
        canvas.drawBitmap(source, 0f, 0f, paint)
        
        return result
    }

    private fun saveCurrentImage() {
        // Use currentBitmap if in edit mode, otherwise use original
        val bitmapToSave = if (isCropMode) originalBitmap else currentBitmap
        if (bitmapToSave == null) return
        
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
                setMessage("Creating PDF from ${paths.size} page(s)...")
                setCancelable(false)
                show()
            }
            
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val pdfFile = PdfUtils.createPdfFromImages(this@CropActivity, paths)
                    withContext(Dispatchers.Main) {
                        progressDialog?.dismiss()
                        
                        if (pdfFile != null) {
                            progressDialog = android.app.ProgressDialog(this@CropActivity).apply {
                                setMessage("Saving document...")
                                setCancelable(false)
                                show()
                            }
                            
                            withContext(Dispatchers.IO) {
                                val db = com.example.scanner.data.local.AppDatabase.getDatabase(applicationContext)
                                val document = com.example.scanner.data.model.DocumentEntity(
                                    name = pdfFile.name,
                                    filePath = pdfFile.absolutePath,
                                    createdAt = System.currentTimeMillis()
                                )
                                db.documentDao().insert(document)
                            }
                            
                            progressDialog?.dismiss()
                            
                            // Show share dialog
                            showShareDialog(pdfFile)
                        } else {
                            Toast.makeText(this@CropActivity, "Failed to create PDF", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        progressDialog?.dismiss()
                        Toast.makeText(this@CropActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        android.util.Log.e("CropActivity", "Failed to save PDF", e)
                    }
                }
            }
        }
    }

    private fun showShareDialog(pdfFile: File) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("PDF Created Successfully")
            .setMessage("Your document has been saved as ${pdfFile.name}")
            .setPositiveButton("Share") { _, _ ->
                sharePdf(pdfFile)
            }
            .setNegativeButton("Done") { _, _ ->
                navigateToHome()
            }
            .setOnDismissListener {
                navigateToHome()
            }
            .show()
    }

    private fun sharePdf(pdfFile: File) {
        try {
            val uri = androidx.core.content.FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                pdfFile
            )

            val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(android.content.Intent.EXTRA_STREAM, uri)
                addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            startActivity(android.content.Intent.createChooser(shareIntent, "Share PDF"))
        } catch (e: Exception) {
            Toast.makeText(this, "Error sharing: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToHome() {
        val intent = android.content.Intent(this, com.example.scanner.ui.MainActivity::class.java)
        intent.flags = android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
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
