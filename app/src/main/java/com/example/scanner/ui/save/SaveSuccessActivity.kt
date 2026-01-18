package com.example.scanner.ui.save

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.scanner.databinding.ActivitySaveSuccessBinding
import com.example.scanner.ui.MainActivity
import com.example.scanner.utils.GoogleDriveManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream

class SaveSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaveSuccessBinding
    private var filePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filePath = intent.getStringExtra("FILE_PATH")
        if (filePath == null) {
            Toast.makeText(this, "Error: Unknown file", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupUI()
        handleDriveUpload()
    }

    private fun setupUI() {
        val file = File(filePath!!)
        binding.tvDocName.text = file.name

        binding.toolbar.setNavigationOnClickListener {
            navigateHome()
        }

        binding.btnDone.setOnClickListener {
            navigateHome()
        }

        binding.cvSaveDevice.setOnClickListener {
            saveToDownloads(file)
        }

        binding.cvShare.setOnClickListener {
            shareDocument(file)
        }
    }

    private fun navigateHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun handleDriveUpload() {
        val account = GoogleDriveManager.getSignedInAccount(this)
        if (account == null) {
            binding.tvUploadStatus.text = "Google Drive not connected"
            binding.pbUpload.visibility = android.view.View.GONE
            return
        }

        binding.tvUploadStatus.text = "Uploading to Google Drive..."
        binding.pbUpload.visibility = android.view.View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            val fileId = GoogleDriveManager.uploadFileToDrive(
                this@SaveSuccessActivity,
                File(filePath!!),
                "application/pdf"
            )

            withContext(Dispatchers.Main) {
                binding.pbUpload.visibility = android.view.View.GONE
                if (fileId != null) {
                    binding.tvUploadStatus.text = "Uploaded to Drive"
                    binding.tvUploadStatus.setTextColor(getColor(com.example.scanner.R.color.success_green))
                    Toast.makeText(this@SaveSuccessActivity, "Uploaded to Drive", Toast.LENGTH_SHORT).show()
                } else {
                    binding.tvUploadStatus.text = "Upload Failed"
                    binding.tvUploadStatus.setTextColor(getColor(com.example.scanner.R.color.danger_red))
                }
            }
        }
    }

    private fun saveToDownloads(sourceFile: File) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val values = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, sourceFile.name)
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
                            Toast.makeText(this@SaveSuccessActivity, "Saved to Downloads", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    val destFile = File(downloadsDir, sourceFile.name)
                    sourceFile.copyTo(destFile, overwrite = true)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SaveSuccessActivity, "Saved to Downloads", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SaveSuccessActivity, "Error saving: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun shareDocument(file: File) {
        try {
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
}
