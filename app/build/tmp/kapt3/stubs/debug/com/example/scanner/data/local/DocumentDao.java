package com.example.scanner.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\r"}, d2 = {"Lcom/example/scanner/data/local/DocumentDao;", "", "delete", "", "document", "Lcom/example/scanner/data/model/DocumentEntity;", "(Lcom/example/scanner/data/model/DocumentEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllDocuments", "Landroidx/lifecycle/LiveData;", "", "getDocumentsList", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "app_debug"})
@androidx.room.Dao
public abstract interface DocumentDao {
    
    @androidx.room.Query(value = "SELECT * FROM documents ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull
    public abstract androidx.lifecycle.LiveData<java.util.List<com.example.scanner.data.model.DocumentEntity>> getAllDocuments();
    
    @androidx.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.example.scanner.data.model.DocumentEntity document, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull
    com.example.scanner.data.model.DocumentEntity document, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM documents ORDER BY createdAt DESC")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getDocumentsList(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.scanner.data.model.DocumentEntity>> $completion);
}