package com.example.scanner.ui.scan;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u00014B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\nH\u0002J \u0010\u001c\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\b\u0010\u001d\u001a\u00020\u0019H\u0002J\u0010\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\nH\u0002J\u0010\u0010\u001f\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\nH\u0002J\u0010\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\u0006H\u0002J\b\u0010\"\u001a\u00020\u0019H\u0002J\u0012\u0010#\u001a\u00020\u00192\b\u0010$\u001a\u0004\u0018\u00010%H\u0014J\b\u0010&\u001a\u00020\u0019H\u0014J\b\u0010\'\u001a\u00020\u0019H\u0002J\b\u0010(\u001a\u00020\u0019H\u0002J\b\u0010)\u001a\u00020\u0019H\u0002J\b\u0010*\u001a\u00020\u0019H\u0002J\u0010\u0010+\u001a\u00020\u00192\u0006\u0010,\u001a\u00020-H\u0002J\b\u0010.\u001a\u00020\u0019H\u0002J\b\u0010/\u001a\u00020\u0019H\u0002J\u0010\u00100\u001a\u00020\u00192\u0006\u0010,\u001a\u00020-H\u0002J\b\u00101\u001a\u00020\u0019H\u0002J\b\u00102\u001a\u00020\u0019H\u0002J\b\u00103\u001a\u00020\u0019H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010\u000e\u001a\u0016\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fj\n\u0012\u0004\u0012\u00020\u0010\u0018\u0001`\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00065"}, d2 = {"Lcom/example/scanner/ui/scan/CropActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/scanner/databinding/ActivityCropBinding;", "brightness", "", "contrast", "", "currentBitmap", "Landroid/graphics/Bitmap;", "currentFilter", "Lcom/example/scanner/ui/scan/CropActivity$FilterType;", "currentImageIndex", "imagePaths", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "isCropMode", "", "originalBitmap", "progressDialog", "Landroid/app/ProgressDialog;", "rotationDegrees", "applyAllAdjustments", "", "applyBlackAndWhite", "source", "applyBrightnessContrast", "applyCrop", "applyGrayscale", "applySepia", "loadImage", "index", "navigateToHome", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "resetAdjustments", "saveAsPdf", "saveCurrentImage", "setupControls", "sharePdf", "pdfFile", "Ljava/io/File;", "showCropMode", "showEditMode", "showShareDialog", "skipCrop", "updateImageCounter", "updateNavigationButtons", "FilterType", "app_debug"})
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
    private int brightness = 0;
    private float contrast = 1.0F;
    @org.jetbrains.annotations.NotNull
    private com.example.scanner.ui.scan.CropActivity.FilterType currentFilter = com.example.scanner.ui.scan.CropActivity.FilterType.ORIGINAL;
    private boolean isCropMode = true;
    
    public CropActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupControls() {
    }
    
    private final void loadImage(int index) {
    }
    
    private final void updateImageCounter() {
    }
    
    private final void updateNavigationButtons() {
    }
    
    private final void showCropMode() {
    }
    
    private final void showEditMode() {
    }
    
    private final void applyCrop() {
    }
    
    private final void skipCrop() {
    }
    
    private final void resetAdjustments() {
    }
    
    private final void applyAllAdjustments() {
    }
    
    private final android.graphics.Bitmap applyBrightnessContrast(android.graphics.Bitmap source, int brightness, float contrast) {
        return null;
    }
    
    private final android.graphics.Bitmap applyGrayscale(android.graphics.Bitmap source) {
        return null;
    }
    
    private final android.graphics.Bitmap applyBlackAndWhite(android.graphics.Bitmap source) {
        return null;
    }
    
    private final android.graphics.Bitmap applySepia(android.graphics.Bitmap source) {
        return null;
    }
    
    private final void saveCurrentImage() {
    }
    
    private final void saveAsPdf() {
    }
    
    private final void showShareDialog(java.io.File pdfFile) {
    }
    
    private final void sharePdf(java.io.File pdfFile) {
    }
    
    private final void navigateToHome() {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/scanner/ui/scan/CropActivity$FilterType;", "", "(Ljava/lang/String;I)V", "ORIGINAL", "GRAYSCALE", "BW", "SEPIA", "app_debug"})
    public static enum FilterType {
        /*public static final*/ ORIGINAL /* = new ORIGINAL() */,
        /*public static final*/ GRAYSCALE /* = new GRAYSCALE() */,
        /*public static final*/ BW /* = new BW() */,
        /*public static final*/ SEPIA /* = new SEPIA() */;
        
        FilterType() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.example.scanner.ui.scan.CropActivity.FilterType> getEntries() {
            return null;
        }
    }
}