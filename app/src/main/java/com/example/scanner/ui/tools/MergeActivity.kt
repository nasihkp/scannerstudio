package com.example.scanner.ui.tools

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanner.data.local.AppDatabase
import com.example.scanner.data.model.DocumentEntity
import com.example.scanner.databinding.ActivityMergeBinding
import com.example.scanner.ui.documents.PdfPreviewActivity
import com.example.scanner.utils.PdfUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MergeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMergeBinding
    private lateinit var adapter: MergeDocumentAdapter

    private val importPdfLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                importPdf(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMergeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        loadDocuments()
        setupButtons()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        adapter = MergeDocumentAdapter(emptyList()) { count ->
             updateSelectionUI(count)
        }
        binding.rvDocuments.layoutManager = LinearLayoutManager(this)
        binding.rvDocuments.adapter = adapter
    }

    private fun loadDocuments() {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(applicationContext)
            // Use runCatching or try-catch for robustness if DB fails? Not strictly needed if previously working.
            val docs = db.documentDao().getDocumentsList()
            
            withContext(Dispatchers.Main) {
                if (docs.isEmpty()) {
                    binding.tvEmpty.visibility = View.VISIBLE
                    binding.rvDocuments.visibility = View.GONE
                } else {
                    binding.tvEmpty.visibility = View.GONE
                    binding.rvDocuments.visibility = View.VISIBLE
                    adapter.setDocuments(docs)
                }
            }
        }
    }

    private fun updateSelectionUI(count: Int) {
        binding.tvSelectionCount.text = "$count documents selected"
        binding.btnMerge.isEnabled = count >= 2
    }

    private fun setupButtons() {
        binding.btnMerge.setOnClickListener {
            val selectedDocs = adapter.getSelectedDocuments()
            if (selectedDocs.size < 2) return@setOnClickListener
            
            performMerge(selectedDocs)
        }
        
        binding.btnImport.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/pdf"
            }
            importPdfLauncher.launch(intent)
        }
    }
    
    private fun importPdf(uri: android.net.Uri) {
         lifecycleScope.launch(Dispatchers.IO) {
             try {
                 val inputStream = contentResolver.openInputStream(uri)
                 if (inputStream != null) {
                     val fileName = getFileName(uri) ?: "imported_${System.currentTimeMillis()}.pdf"
                     val file = java.io.File(getExternalFilesDir(null), fileName)
                     val outputStream = java.io.FileOutputStream(file)
                     
                     inputStream.copyTo(outputStream)
                     inputStream.close()
                     outputStream.close()
                     
                     saveToDb(file)
                     
                     // Refresh list on Main thread
                     loadDocuments() // This runs on IO but switches to Main inside
                     
                     withContext(Dispatchers.Main) {
                         Toast.makeText(this@MergeActivity, "Imported: $fileName", Toast.LENGTH_SHORT).show()
                     }
                 }
             } catch (e: Exception) {
                 e.printStackTrace()
                 withContext(Dispatchers.Main) {
                     Toast.makeText(this@MergeActivity, "Import Failed", Toast.LENGTH_SHORT).show()
                 }
             }
         }
    }
    
    private fun getFileName(uri: android.net.Uri): String? {
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

    private fun performMerge(documents: List<DocumentEntity>) {
        val progressDialog = android.app.ProgressDialog(this).apply {
            setMessage("Merging PDFs...")
            setCancelable(false)
            show()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val paths = documents.map { it.filePath }
            val mergedFile = PdfUtils.mergePdfs(this@MergeActivity, paths)
            
            withContext(Dispatchers.Main) {
                progressDialog.dismiss()
                
                if (mergedFile != null) {
                    Toast.makeText(this@MergeActivity, "Merge Successful!", Toast.LENGTH_SHORT).show()
                    
                    // Save to DB
                    saveToDb(mergedFile)
                    
                    // Open Preview
                    val intent = Intent(this@MergeActivity, PdfPreviewActivity::class.java)
                    intent.putExtra("PDF_PATH", mergedFile.absolutePath)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@MergeActivity, "Merge Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun saveToDb(file: java.io.File) {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(applicationContext)
            val doc = DocumentEntity(
                name = file.name,
                filePath = file.absolutePath,
                createdAt = System.currentTimeMillis()
            )
            db.documentDao().insert(doc)
        }
    }
}
