package com.example.scanner.ui.scan;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001)B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u0014H\u0002J\b\u0010\u0016\u001a\u00020\u0014H\u0002J\u0010\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\bH\u0002J\u0012\u0010\u0019\u001a\u00020\u00142\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J\b\u0010\u001c\u001a\u00020\u0014H\u0014J\b\u0010\u001d\u001a\u00020\u0014H\u0002J\b\u0010\u001e\u001a\u00020\u0014H\u0002J\b\u0010\u001f\u001a\u00020\u0014H\u0002J\u0010\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\"H\u0002J\u0010\u0010#\u001a\u00020\u00142\u0006\u0010$\u001a\u00020\nH\u0002J\b\u0010%\u001a\u00020\u0014H\u0002J\u0010\u0010&\u001a\u00020\u00142\u0006\u0010\'\u001a\u00020\"H\u0002J\b\u0010(\u001a\u00020\u0014H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010\u000b\u001a\u0016\u0012\u0004\u0012\u00020\r\u0018\u00010\fj\n\u0012\u0004\u0012\u00020\r\u0018\u0001`\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/example/scanner/ui/scan/CropActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/scanner/databinding/ActivityCropBinding;", "currentBitmap", "Landroid/graphics/Bitmap;", "currentImageIndex", "", "currentTab", "Lcom/example/scanner/ui/scan/CropActivity$ToolTab;", "imagePaths", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "originalBitmap", "progressDialog", "Landroid/app/ProgressDialog;", "rotationDegrees", "applyAllAdjustments", "", "applyCrop", "initCropView", "loadImage", "index", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "resetAdjustments", "saveAsPdf", "saveCurrentImage", "saveDocumentToDb", "file", "Ljava/io/File;", "selectTab", "tab", "setupControls", "showShareDialog", "pdfFile", "updateImageCounter", "ToolTab", "app_debug"})
public final class CropActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.scanner.databinding.ActivityCropBinding binding;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<java.lang.String> imagePaths;
    private int currentImageIndex = 0;
    @org.jetbrains.annotations.Nullable
    private android.graphics.Bitmap originalBitmap;
    @org.jetbrains.annotations.Nullable
    private android.graphics.Bitmap currentBitmap;
    @org.jetbrains.annotations.Nullable
    private android.app.ProgressDialog progressDialog;
    private int rotationDegrees = 0;
    @org.jetbrains.annotations.NotNull
    private com.example.scanner.ui.scan.CropActivity.ToolTab currentTab = com.example.scanner.ui.scan.CropActivity.ToolTab.CROP;
    
    public CropActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupControls() {
    }
    
    private final void selectTab(com.example.scanner.ui.scan.CropActivity.ToolTab tab) {
    }
    
    private final void loadImage(int index) {
    }
    
    private final void initCropView() {
    }
    
    private final void updateImageCounter() {
    }
    
    private final void applyCrop() {
    }
    
    private final void resetAdjustments() {
    }
    
    private final void applyAllAdjustments() {
    }
    
    private final void saveCurrentImage() {
    }
    
    private final void saveAsPdf() {
    }
    
    private final void saveDocumentToDb(java.io.File file) {
    }
    
    private final void showShareDialog(java.io.File pdfFile) {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/example/scanner/ui/scan/CropActivity$ToolTab;", "", "(Ljava/lang/String;I)V", "CROP", "ROTATE", "app_debug"})
    static enum ToolTab {
        /*public static final*/ CROP /* = new CROP() */,
        /*public static final*/ ROTATE /* = new ROTATE() */;
        
        ToolTab() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.example.scanner.ui.scan.CropActivity.ToolTab> getEntries() {
            return null;
        }
    }
}