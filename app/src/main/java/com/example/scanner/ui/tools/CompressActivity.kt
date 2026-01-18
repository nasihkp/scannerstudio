package com.example.scanner.ui.tools

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scanner.data.local.AppDatabase
import com.example.scanner.data.model.DocumentEntity
import com.example.scanner.databinding.ActivityCompressBinding
import com.example.scanner.ui.documents.PdfPreviewActivity
import com.example.scanner.utils.PdfUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CompressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompressBinding
    private lateinit var adapter: MergeDocumentAdapter // Reuse adapter but enforce single selection
    private var selectedDocument: DocumentEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        loadDocuments()
        setupControls()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        // Reuse MergeDocumentAdapter but handling single selection locally
        adapter = MergeDocumentAdapter(emptyList()) { count ->
             // Re-evaluate local selection
             checkSelection()
        }
        binding.rvDocuments.layoutManager = LinearLayoutManager(this)
        binding.rvDocuments.adapter = adapter
    }
    
    private fun checkSelection() {
        val selected = adapter.getSelectedDocuments()
        if (selected.size > 1) {
            Toast.makeText(this, "Please select only one document", Toast.LENGTH_SHORT).show()
            binding.btnCompress.isEnabled = false
        } else if (selected.isNotEmpty()) {
            selectedDocument = selected[0]
            binding.btnCompress.isEnabled = true
        } else {
            selectedDocument = null
            binding.btnCompress.isEnabled = false
        }
    }

    private fun loadDocuments() {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(applicationContext)
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

    private fun setupControls() {
        binding.seekQuality.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvQualityValue.text = "Quality: $progress%"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.btnCompress.setOnClickListener {
            selectedDocument?.let { doc ->
                performCompress(doc, binding.seekQuality.progress)
            }
        }
    }

    private fun performCompress(document: DocumentEntity, quality: Int) {
        val progressDialog = android.app.ProgressDialog(this).apply {
            setMessage("Compressing PDF...")
            setCancelable(false)
            show()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val compressedFile = PdfUtils.compressPdf(this@CompressActivity, document.filePath, quality)
            
            withContext(Dispatchers.Main) {
                progressDialog.dismiss()
                
                if (compressedFile != null) {
                    Toast.makeText(this@CompressActivity, "Compression Successful!", Toast.LENGTH_SHORT).show()
                    
                    // Save to DB
                    saveToDb(compressedFile)
                    
                    // Open Preview
                    val intent = Intent(this@CompressActivity, PdfPreviewActivity::class.java)
                    intent.putExtra("PDF_PATH", compressedFile.absolutePath)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@CompressActivity, "Compression Failed", Toast.LENGTH_SHORT).show()
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
