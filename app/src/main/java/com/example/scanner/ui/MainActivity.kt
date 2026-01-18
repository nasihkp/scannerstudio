package com.example.scanner.ui

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.scanner.databinding.ActivityMainBinding
import com.example.scanner.ui.scan.ScanActivity
import com.example.scanner.data.model.DocumentEntity
import com.example.scanner.utils.GoogleDriveManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private val importLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                handleImport(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }
    
    // ... [onResume / updateUI / enableTool same as before] ...

    private fun setupUI() {
        // Main Scan Buttons
        binding.cvScan.setOnClickListener { checkPermissionsAndScan() }
        binding.fabScan.setOnClickListener { checkPermissionsAndScan() }

        // Import (Direct Open)
        binding.cvImport.setOnClickListener {
             val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                 addCategory(Intent.CATEGORY_OPENABLE)
                 type = "*/*" 
                 putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "application/pdf"))
             }
             importLauncher.launch(intent)
        }

        // Settings
        binding.btnSettings.setOnClickListener {
             val intent = Intent(this, com.example.scanner.ui.settings.SettingsActivity::class.java)
             startActivity(intent)
        }
        
        // Advanced Tools
        binding.cvToolMerge.setOnClickListener { 
            if (binding.cvToolMerge.isEnabled) {
                val intent = Intent(this, com.example.scanner.ui.tools.MergeActivity::class.java)
                startActivity(intent)
            } else {
                 Toast.makeText(this, "Please sign in to use this feature", Toast.LENGTH_SHORT).show()
            }
        }
        binding.cvToolCompress.setOnClickListener { 
            if (binding.cvToolCompress.isEnabled) {
                val intent = Intent(this, com.example.scanner.ui.tools.CompressActivity::class.java)
                startActivity(intent)
            } else {
                 Toast.makeText(this, "Please sign in to use this feature", Toast.LENGTH_SHORT).show()
            }
        }
        binding.cvToolSecurity.setOnClickListener { 
             Toast.makeText(this, "Security Tools coming soon", Toast.LENGTH_SHORT).show() 
        }

        // Documents List
        val adapter = com.example.scanner.ui.documents.DocumentAdapter(
            onDocumentClick = { document ->
                // USE PDF PREVIEW ACTIVITY instead of EditorActivity
                val intent = Intent(this, com.example.scanner.ui.documents.PdfPreviewActivity::class.java)
                intent.putExtra("PDF_PATH", document.filePath)
                startActivity(intent)
            },
            onShareClick = { document -> shareDocument(document) },
            onSaveClick = { document -> saveToDownloads(document) },
            onDeleteClick = { document -> deleteDocument(document) }
        )
        binding.rvDocuments.adapter = adapter
        binding.rvDocuments.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        viewModel = androidx.lifecycle.ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.allDocuments.observe(this) { documents ->
            adapter.setDocuments(documents)
            binding.tvEmptyState.visibility = if (documents.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun handleImport(uri: Uri) {
        val mimeType = contentResolver.getType(uri) ?: ""
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Copy to cache/files first
                val fileName = getFileName(uri) ?: "imported_${System.currentTimeMillis()}"
                val safeFileName = if (fileName.endsWith(".pdf", true) || fileName.endsWith(".jpg", true) || fileName.endsWith(".png", true)) fileName else "$fileName.imported"
                
                val destFile = File(externalCacheDir, safeFileName)
                val inputStream = contentResolver.openInputStream(uri)
                val outputStream = java.io.FileOutputStream(destFile)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()
                
                withContext(Dispatchers.Main) {
                    if (mimeType.startsWith("image/") || safeFileName.endsWith(".jpg", true) || safeFileName.endsWith(".png", true)) {
                        // Launch Crop Activity
                        val intent = Intent(this@MainActivity, com.example.scanner.ui.scan.CropActivity::class.java)
                        val list = ArrayList<String>()
                        list.add(destFile.absolutePath)
                        intent.putStringArrayListExtra("IMAGE_PATHS", list)
                        startActivity(intent)
                     } else if (mimeType == "application/pdf" || safeFileName.endsWith(".pdf", true)) {
                         // Insert into DB first
                         val db = com.example.scanner.data.local.AppDatabase.getDatabase(applicationContext)
                         val doc = DocumentEntity(
                             name = destFile.name,
                             filePath = destFile.absolutePath,
                             createdAt = System.currentTimeMillis()
                         )
                         db.documentDao().insert(doc)
                         
                         // Launch PDF Preview
                         val intent = Intent(this@MainActivity, com.example.scanner.ui.documents.PdfPreviewActivity::class.java)
                         intent.putExtra("PDF_PATH", destFile.absolutePath)
                         startActivity(intent)
                    } else {
                        Toast.makeText(this@MainActivity, "Unsupported file type", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                   Toast.makeText(this@MainActivity, "Import failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                    if (index >= 0) {
                        result = cursor.getString(index)
                    }
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != null && cut != -1) {
                result = result?.substring(cut + 1)
            }
        }
        return result
    }

    private fun checkPermissionsAndScan() {
        if (allPermissionsGranted()) {
            startScan()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun shareDocument(document: DocumentEntity) {
        try {
            val file = File(document.filePath)
            if (!file.exists()) {
                Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show()
                return
            }

            val uri = FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                file
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            startActivity(Intent.createChooser(shareIntent, "Share PDF"))
        } catch (e: Exception) {
            Toast.makeText(this, "Error sharing: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveToDownloads(document: DocumentEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val sourceFile = File(document.filePath)
                if (!sourceFile.exists()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "File not found", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val values = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, document.name)
                        put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                    }

                    val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
                    uri?.let {
                        contentResolver.openOutputStream(it)?.use { outputStream ->
                            FileInputStream(sourceFile).use { inputStream ->
                                inputStream.copyTo(outputStream)
                            }
                        }
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Saved to Downloads", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    val destFile = File(downloadsDir, document.name)
                    sourceFile.copyTo(destFile, overwrite = true)
                    
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Saved to Downloads", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error saving: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteDocument(document: DocumentEntity) {
        AlertDialog.Builder(this)
            .setTitle("Delete Document")
            .setMessage("Are you sure you want to delete ${document.name}?")
            .setPositiveButton("Delete") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val db = com.example.scanner.data.local.AppDatabase.getDatabase(applicationContext)
                        db.documentDao().delete(document)
                        
                        val file = File(document.filePath)
                        if (file.exists()) {
                            file.delete()
                        }
                        
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Document deleted", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Error deleting: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun startScan() {
        val intent = Intent(this, ScanActivity::class.java)
        startActivity(intent)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startScan()
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
