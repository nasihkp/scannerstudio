package com.example.scanner.ui.scan;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\u0012\u0010\u0010\u001a\u00020\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014J\b\u0010\u0013\u001a\u00020\u000fH\u0014J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u000fH\u0002J\b\u0010\u0018\u001a\u00020\u000fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/example/scanner/ui/scan/ScanActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/scanner/databinding/ActivityScanBinding;", "btnDone", "Landroid/widget/Button;", "cameraExecutor", "Ljava/util/concurrent/ExecutorService;", "capturedImages", "", "", "imageCapture", "Landroidx/camera/core/ImageCapture;", "finishScanning", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "saveToGallery", "file", "Ljava/io/File;", "startCamera", "takePhoto", "Companion", "app_debug"})
public final class ScanActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.scanner.databinding.ActivityScanBinding binding;
    @org.jetbrains.annotations.Nullable
    private androidx.camera.core.ImageCapture imageCapture;
    private java.util.concurrent.ExecutorService cameraExecutor;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> capturedImages = null;
    private android.widget.Button btnDone;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "ScanActivity";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";
    @org.jetbrains.annotations.NotNull
    public static final com.example.scanner.ui.scan.ScanActivity.Companion Companion = null;
    
    public ScanActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void startCamera() {
    }
    
    private final void takePhoto() {
    }
    
    private final void saveToGallery(java.io.File file) {
    }
    
    private final void finishScanning() {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/example/scanner/ui/scan/ScanActivity$Companion;", "", "()V", "FILENAME_FORMAT", "", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}