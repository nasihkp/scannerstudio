package com.example.scanner.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.scanner.data.model.DocumentEntity

@Dao
interface DocumentDao {
    @Query("SELECT * FROM documents ORDER BY createdAt DESC")
    fun getAllDocuments(): LiveData<List<DocumentEntity>>

    @Insert
    suspend fun insert(document: DocumentEntity)

    @Delete
    suspend fun delete(document: DocumentEntity)

    @Query("SELECT * FROM documents ORDER BY createdAt DESC")
    suspend fun getDocumentsList(): List<DocumentEntity>
}
