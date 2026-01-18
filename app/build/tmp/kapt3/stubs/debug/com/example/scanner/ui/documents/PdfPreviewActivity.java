package com.example.scanner.ui.documents;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u000eH\u0002J\u0012\u0010\u0010\u001a\u00020\u000e2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014J\b\u0010\u0013\u001a\u00020\u000eH\u0014J\u0010\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u000eH\u0002J\b\u0010\u0018\u001a\u00020\u000eH\u0002J\u0010\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\tH\u0002J\b\u0010\u001b\u001a\u00020\u000eH\u0002J \u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u001f\u001a\u00020\u000eH\u0002J\b\u0010 \u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0018\u00010\u0006R\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/example/scanner/ui/documents/PdfPreviewActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/scanner/databinding/ActivityPdfPreviewBinding;", "currentPage", "Landroid/graphics/pdf/PdfRenderer$Page;", "Landroid/graphics/pdf/PdfRenderer;", "pageIndex", "", "pdfFile", "Ljava/io/File;", "pdfRenderer", "displayPdf", "", "editScans", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "renameFile", "newName", "", "setupButtons", "sharePdf", "showPage", "index", "showRenameDialog", "updateDocumentInDb", "oldPath", "newPath", "updateNavButtons", "uploadToDrive", "app_debug"})
public final class PdfPreviewActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.scanner.databinding.ActivityPdfPreviewBinding binding;
    @org.jetbrains.annotations.Nullable
    private java.io.File pdfFile;
    @org.jetbrains.annotations.Nullable
    private android.graphics.pdf.PdfRenderer pdfRenderer;
    @org.jetbrains.annotations.Nullable
    private android.graphics.pdf.PdfRenderer.Page currentPage;
    private int pageIndex = 0;
    
    public PdfPreviewActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void displayPdf() {
    }
    
    private final void showPage(int index) {
    }
    
    private final void updateNavButtons() {
    }
    
    private final void setupButtons() {
    }
    
    private final void editScans() {
    }
    
    private final void showRenameDialog() {
    }
    
    private final void renameFile(java.lang.String newName) {
    }
    
    private final void updateDocumentInDb(java.lang.String oldPath, java.lang.String newPath, java.lang.String newName) {
    }
    
    private final void uploadToDrive() {
    }
    
    private final void sharePdf() {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
}