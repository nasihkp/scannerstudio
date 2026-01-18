package com.example.scanner.ui.tools

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scanner.R
import com.example.scanner.data.model.DocumentEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MergeDocumentAdapter(
    private var documents: List<DocumentEntity>,
    private val onSelectionChanged: (Int) -> Unit
) : RecyclerView.Adapter<MergeDocumentAdapter.ViewHolder>() {

    private val selectedIds = mutableSetOf<Int>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val ivCheckbox: ImageView = view.findViewById(R.id.ivCheckbox)
        val root: View = view.findViewById(R.id.rootLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_document_merge, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doc = documents[position]
        
        holder.tvName.text = doc.name
        holder.tvDate.text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(doc.createdAt))
        
        val isSelected = selectedIds.contains(doc.id)
        holder.ivCheckbox.setImageResource(
            if (isSelected) R.drawable.ic_check_box else R.drawable.ic_check_box_outline_blank
        )
        
        holder.root.setOnClickListener {
            if (isSelected) selectedIds.remove(doc.id) else selectedIds.add(doc.id)
            notifyItemChanged(position)
            onSelectionChanged(selectedIds.size)
        }
    }

    override fun getItemCount() = documents.size
    
    fun setDocuments(newDocs: List<DocumentEntity>) {
        documents = newDocs
        notifyDataSetChanged()
    }
    
    fun getSelectedDocuments(): List<DocumentEntity> {
        return documents.filter { selectedIds.contains(it.id) }
    }
}
