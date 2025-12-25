package com.example.scanner.ui.documents

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.R
import com.example.scanner.data.model.DocumentEntity
import com.example.scanner.databinding.ItemDocumentBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DocumentAdapter(
    private val onDocumentClick: (DocumentEntity) -> Unit,
    private val onShareClick: (DocumentEntity) -> Unit,
    private val onSaveClick: (DocumentEntity) -> Unit,
    private val onDeleteClick: (DocumentEntity) -> Unit
) : RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder>() {

    private var documents: List<DocumentEntity> = emptyList()

    fun setDocuments(newDocuments: List<DocumentEntity>) {
        documents = newDocuments
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val binding = ItemDocumentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DocumentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        holder.bind(documents[position])
    }

    override fun getItemCount() = documents.size

    inner class DocumentViewHolder(private val binding: ItemDocumentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(document: DocumentEntity) {
            binding.tvName.text = document.name
            binding.tvDate.text = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
                .format(Date(document.createdAt))
            
            binding.root.setOnClickListener { onDocumentClick(document) }
            
            // Menu button click
            binding.btnMenu.setOnClickListener { view ->
                val popup = PopupMenu(view.context, view)
                popup.menuInflater.inflate(R.menu.document_menu, popup.menu)
                popup.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.action_share -> {
                            onShareClick(document)
                            true
                        }
                        R.id.action_save_downloads -> {
                            onSaveClick(document)
                            true
                        }
                        R.id.action_delete -> {
                            onDeleteClick(document)
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }
            
            // TODO: Load thumbnail using Glide/Coil if available
        }
    }
}
