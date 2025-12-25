package com.example.scanner.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.scanner.data.local.AppDatabase
import com.example.scanner.data.model.DocumentEntity

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val documentDao = AppDatabase.getDatabase(application).documentDao()
    val allDocuments: LiveData<List<DocumentEntity>> = documentDao.getAllDocuments()
}
