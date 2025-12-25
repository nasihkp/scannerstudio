package com.example.scanner.ui.scan

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.databinding.ActivityImagePreviewBinding
import java.io.File

class ImagePreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImagePreviewBinding
    private var imagePaths = mutableListOf<String>()
    private lateinit var adapter: ImagePreviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get image paths from intent
        imagePaths = intent.getStringArrayListExtra("IMAGE_PATHS")?.toMutableList() ?: mutableListOf()

        setupRecyclerView()
        setupButtons()
        updateUI()
    }

    private fun setupRecyclerView() {
        adapter = ImagePreviewAdapter(
            images = imagePaths,
            onDeleteClick = { position -> showDeleteConfirmation(position) }
        )

        binding.rvImages.layoutManager = GridLayoutManager(this, 2)
        binding.rvImages.adapter = adapter
    }

    private fun setupButtons() {
        binding.btnAddMore.setOnClickListener {
            // Return to ScanActivity to capture more images
            finish()
        }

        binding.btnProceed.setOnClickListener {
            if (imagePaths.isEmpty()) {
                Toast.makeText(this, "Please capture at least one image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Navigate to CropActivity with all images
            val intent = Intent(this, CropActivity::class.java)
            intent.putStringArrayListExtra("IMAGE_PATHS", ArrayList(imagePaths))
            startActivity(intent)
            finish()
        }
    }

    private fun showDeleteConfirmation(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete Image")
            .setMessage("Are you sure you want to delete this image?")
            .setPositiveButton("Delete") { _, _ ->
                deleteImage(position)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteImage(position: Int) {
        if (position >= 0 && position < imagePaths.size) {
            // Delete the file
            try {
                val file = File(imagePaths[position])
                if (file.exists()) {
                    file.delete()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Remove from list
            imagePaths.removeAt(position)
            adapter.updateImages(imagePaths)
            updateUI()

            Toast.makeText(this, "Image deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val count = imagePaths.size
        binding.tvImageCount.text = "$count image${if (count != 1) "s" else ""} captured"

        if (count == 0) {
            binding.emptyState.visibility = View.VISIBLE
            binding.rvImages.visibility = View.GONE
            binding.btnProceed.isEnabled = false
        } else {
            binding.emptyState.visibility = View.GONE
            binding.rvImages.visibility = View.VISIBLE
            binding.btnProceed.isEnabled = true
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh the list in case new images were added
        intent.getStringArrayListExtra("IMAGE_PATHS")?.let { newPaths ->
            imagePaths = newPaths.toMutableList()
            adapter.updateImages(imagePaths)
            updateUI()
        }
    }
}

// Adapter for image preview
class ImagePreviewAdapter(
    private var images: List<String>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<ImagePreviewAdapter.ImageViewHolder>() {

    class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val ivThumbnail: android.widget.ImageView = view.findViewById(com.example.scanner.R.id.ivThumbnail)
        val btnDelete: android.widget.ImageButton = view.findViewById(com.example.scanner.R.id.btnDelete)
        val tvImageNumber: android.widget.TextView = view.findViewById(com.example.scanner.R.id.tvImageNumber)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ImageViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(com.example.scanner.R.layout.item_image_preview, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imagePath = images[position]

        // Load thumbnail
        try {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            holder.ivThumbnail.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Set image number
        holder.tvImageNumber.text = "${position + 1}"

        // Delete button click
        holder.btnDelete.setOnClickListener {
            onDeleteClick(position)
        }
    }

    override fun getItemCount() = images.size

    fun updateImages(newImages: List<String>) {
        images = newImages
        notifyDataSetChanged()
    }
}
