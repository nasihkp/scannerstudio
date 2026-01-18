package com.example.scanner.ui.scan

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.scanner.R
import com.example.scanner.databinding.ActivityScanBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding
    private val capturedImages = mutableListOf<String>()
    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null
    
    // States
    private var isFlashOn = false
    private var isAutoCapture = false

    private val galleryLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.GetContent()
    ) { uri: android.net.Uri? ->
        uri?.let {
            val path = com.example.scanner.utils.GalleryUtils.copyUriToCache(this, it)
            if (path != null) {
                capturedImages.add(path)
                updateUI()
            } else {
                Toast.makeText(this, "Failed to import image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startCamera()
        setupUI()
        
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun setupUI() {
        // Capture
        binding.btnCapture.setOnClickListener { 
            takePhoto()
            animateCaptureButton()
        }
        
        // Gallery
        binding.btnGallery.setOnClickListener { openGallery() }
        
        // Done
        binding.cvDone.setOnClickListener { finishScanning() }
        
        // Top Bar
        binding.btnClose.setOnClickListener { finish() }
        
        binding.btnFlash.setOnClickListener { toggleFlash() }
        
        binding.tvAuto.setOnClickListener {
            isAutoCapture = !isAutoCapture
            updateAutoUI()
        }
    }
    
    private fun animateCaptureButton() {
        binding.btnCapture.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).withEndAction {
            binding.btnCapture.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start()
        }.start()
    }

    private fun toggleFlash() {
        isFlashOn = !isFlashOn
        imageCapture?.flashMode = if (isFlashOn) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF
        binding.btnFlash.setImageResource(
            if (isFlashOn) R.drawable.ic_flash_on else R.drawable.ic_flash_off
        )
    }
    
    private fun updateAutoUI() {
        binding.tvAuto.alpha = if (isAutoCapture) 1.0f else 0.5f
        // TODO: Implement Auto Capture Logic (requires ImageAnalysis)
        if (isAutoCapture) {
            Toast.makeText(this, "Auto Capture Enabled (Simulated)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun updateUI() {
        val count = capturedImages.size
        binding.tvCount.text = "$count >"
        binding.cvDone.visibility = if (count > 0) View.VISIBLE else View.GONE
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .setFlashMode(if (isFlashOn) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF)
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
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
                    saveToGallery(photoFile)
                    capturedImages.add(photoFile.absolutePath)
                    updateUI()
                }
            }
        )
    }

    private fun saveToGallery(file: File) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
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
            val intent = android.content.Intent(this, CropActivity::class.java)
            intent.putStringArrayListExtra("IMAGE_PATHS", ArrayList(capturedImages))
            startActivity(intent)
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
