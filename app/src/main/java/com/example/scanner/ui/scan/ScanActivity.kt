package com.example.scanner.ui.scan

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.scanner.databinding.ActivityScanBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    private val capturedImages = mutableListOf<String>()
    private lateinit var btnDone: android.widget.Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startCamera()

        binding.btnCapture.setOnClickListener { takePhoto() }
        
        // Add Edit Images Button dynamically
        btnDone = android.widget.Button(this).apply {
            text = "Edit Images (0)"
            visibility = android.view.View.GONE
            setOnClickListener { finishScanning() }
            // Improve button styling
            setBackgroundColor(android.graphics.Color.parseColor("#4CAF50"))
            setTextColor(android.graphics.Color.WHITE)
            textSize = 16f
            setPadding(32, 16, 32, 16)
        }
        val params = androidx.constraintlayout.widget.ConstraintLayout.LayoutParams(
            androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.WRAP_CONTENT,
            androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        params.bottomToBottom = androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
        params.endToEnd = androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
        params.setMargins(0, 0, 48, 120)
        btnDone.layoutParams = params
        binding.root.addView(btnDone)

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
                Toast.makeText(this, "Failed to start camera", Toast.LENGTH_SHORT).show()
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            externalCacheDir,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                .format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    // Save to MediaStore (Gallery)
                    saveToGallery(photoFile)
                    
                    val msg = "Captured page ${capturedImages.size + 1}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    capturedImages.add(photoFile.absolutePath)
                    
                    btnDone.text = "Edit Images (${capturedImages.size})"
                    btnDone.visibility = android.view.View.VISIBLE
                }
            }
        )
    }

    private fun saveToGallery(file: File) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Android 10+ - Use MediaStore
                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, file.name)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Scanner")
                }
                
                val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                uri?.let {
                    contentResolver.openOutputStream(it)?.use { outputStream ->
                        file.inputStream().use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                }
            } else {
                // Android 9 and below - Copy to Pictures directory
                val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val scannerDir = File(picturesDir, "Scanner")
                if (!scannerDir.exists()) {
                    scannerDir.mkdirs()
                }
                val destFile = File(scannerDir, file.name)
                file.copyTo(destFile, overwrite = true)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save to gallery", e)
        }
    }

    private fun finishScanning() {
        if (capturedImages.isEmpty()) {
            Toast.makeText(this, "Please capture at least one page", Toast.LENGTH_SHORT).show()
            return
        }
        
        try {
            val intent = android.content.Intent(this, ImagePreviewActivity::class.java)
            intent.putStringArrayListExtra("IMAGE_PATHS", ArrayList(capturedImages))
            startActivity(intent)
            Toast.makeText(this, "Review ${capturedImages.size} image(s)...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start ImagePreviewActivity", e)
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "ScanActivity"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}
