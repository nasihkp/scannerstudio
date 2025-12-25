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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.scanner.databinding.ActivityMainBinding
import com.example.scanner.ui.scan.ScanActivity
import com.example.scanner.data.model.DocumentEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        binding.fabScan.setOnClickListener {
            if (allPermissionsGranted()) {
                startScan()
            } else {
                ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                )
            }
        }
        
        val adapter = com.example.scanner.ui.documents.DocumentAdapter(
            onDocumentClick = { document ->
                val intent = Intent(this, com.example.scanner.ui.editor.EditorActivity::class.java)
                intent.putExtra("DOCUMENT_PATH", document.filePath)
                startActivity(intent)
            },
            onShareClick = { document -> shareDocument(document) },
            onSaveClick = { document -> saveToDownloads(document) },
            onDeleteClick = { document -> deleteDocument(document) }
        )
        binding.rvDocuments.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        binding.rvDocuments.adapter = adapter

        viewModel = androidx.lifecycle.ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.allDocuments.observe(this) { documents ->
            adapter.setDocuments(documents)
            binding.tvEmptyState.visibility = if (documents.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
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
                    // Use MediaStore for Android 10+
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
                    // For older Android versions
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
                        // Delete from database
                        val db = com.example.scanner.data.local.AppDatabase.getDatabase(applicationContext)
                        db.documentDao().delete(document)
                        
                        // Delete file
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
