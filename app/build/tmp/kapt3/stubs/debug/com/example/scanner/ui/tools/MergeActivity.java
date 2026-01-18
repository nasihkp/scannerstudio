package com.example.scanner.ui.tools;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u0011\u001a\u00020\u0010H\u0002J\u0012\u0010\u0012\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\u0016\u0010\u0015\u001a\u00020\u00102\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0002J\u0010\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u0010H\u0002J\b\u0010\u001d\u001a\u00020\u0010H\u0002J\b\u0010\u001e\u001a\u00020\u0010H\u0002J\u0010\u0010\u001f\u001a\u00020\u00102\u0006\u0010 \u001a\u00020!H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\t0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/example/scanner/ui/tools/MergeActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/example/scanner/ui/tools/MergeDocumentAdapter;", "binding", "Lcom/example/scanner/databinding/ActivityMergeBinding;", "importPdfLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "getFileName", "", "uri", "Landroid/net/Uri;", "importPdf", "", "loadDocuments", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "performMerge", "documents", "", "Lcom/example/scanner/data/model/DocumentEntity;", "saveToDb", "file", "Ljava/io/File;", "setupButtons", "setupRecyclerView", "setupToolbar", "updateSelectionUI", "count", "", "app_debug"})
public final class MergeActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.scanner.databinding.ActivityMergeBinding binding;
    private com.example.scanner.ui.tools.MergeDocumentAdapter adapter;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> importPdfLauncher = null;
    
    public MergeActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupToolbar() {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void loadDocuments() {
    }
    
    private final void updateSelectionUI(int count) {
    }
    
    private final void setupButtons() {
    }
    
    private final void importPdf(android.net.Uri uri) {
    }
    
    private final java.lang.String getFileName(android.net.Uri uri) {
        return null;
    }
    
    private final void performMerge(java.util.List<com.example.scanner.data.model.DocumentEntity> documents) {
    }
    
    private final void saveToDb(java.io.File file) {
    }
}