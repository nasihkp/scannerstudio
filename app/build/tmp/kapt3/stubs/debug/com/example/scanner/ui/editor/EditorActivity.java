package com.example.scanner.ui.editor;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\tH\u0002J\u0012\u0010\u0016\u001a\u00020\u00112\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u0011H\u0014J\u0010\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u0011H\u0002J\b\u0010\u001e\u001a\u00020\u0011H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0018\u00010\u0006R\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/example/scanner/ui/editor/EditorActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/scanner/databinding/ActivityEditorBinding;", "currentPage", "Landroid/graphics/pdf/PdfRenderer$Page;", "Landroid/graphics/pdf/PdfRenderer;", "currentPageIndex", "", "documentPath", "", "parcelFileDescriptor", "Landroid/os/ParcelFileDescriptor;", "pdfRenderer", "totalPages", "addItem", "", "type", "Lcom/example/scanner/ui/editor/ItemType;", "displayPdfPage", "pageIndex", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "populateOverlaysFromOcr", "text", "Lcom/google/mlkit/vision/text/Text;", "runOcr", "saveWithOverlays", "app_debug"})
public final class EditorActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.scanner.databinding.ActivityEditorBinding binding;
    @org.jetbrains.annotations.Nullable
    private java.lang.String documentPath;
    @org.jetbrains.annotations.Nullable
    private android.graphics.pdf.PdfRenderer pdfRenderer;
    @org.jetbrains.annotations.Nullable
    private android.graphics.pdf.PdfRenderer.Page currentPage;
    @org.jetbrains.annotations.Nullable
    private android.os.ParcelFileDescriptor parcelFileDescriptor;
    private int currentPageIndex = 0;
    private int totalPages = 0;
    
    public EditorActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void addItem(com.example.scanner.ui.editor.ItemType type) {
    }
    
    private final void runOcr() {
    }
    
    private final void populateOverlaysFromOcr(com.google.mlkit.vision.text.Text text) {
    }
    
    private final void saveWithOverlays() {
    }
    
    private final void displayPdfPage(int pageIndex) {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
}