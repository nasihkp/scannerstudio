package com.example.scanner.ui.scan;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00100\u0018J\u0018\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u0013H\u0002J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0014J(\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u0007H\u0014J\u0010\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020(H\u0016J\u0006\u0010)\u001a\u00020\u001dJ\u0010\u0010*\u001a\u00020\u001d2\b\u0010+\u001a\u0004\u0018\u00010\u000bR\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0013X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/example/scanner/ui/scan/CropView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "activeHandleIndex", "bitmapRect", "Landroid/graphics/RectF;", "cornerPaint", "Landroid/graphics/Paint;", "cropPoints", "", "Landroid/graphics/PointF;", "[Landroid/graphics/PointF;", "handleRadius", "", "overlayPaint", "paint", "touchTolerance", "getCropPoints", "", "getHandleIndex", "x", "y", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "onSizeChanged", "w", "h", "oldw", "oldh", "onTouchEvent", "", "event", "Landroid/view/MotionEvent;", "resetPoints", "setImageEvent", "imageRect", "app_debug"})
public final class CropView extends android.view.View {
    @org.jetbrains.annotations.NotNull
    private final android.graphics.PointF[] cropPoints = null;
    @org.jetbrains.annotations.NotNull
    private final android.graphics.Paint paint = null;
    @org.jetbrains.annotations.NotNull
    private final android.graphics.Paint cornerPaint = null;
    @org.jetbrains.annotations.NotNull
    private final android.graphics.Paint overlayPaint = null;
    private final float handleRadius = 40.0F;
    private final float touchTolerance = 60.0F;
    private int activeHandleIndex = -1;
    @org.jetbrains.annotations.NotNull
    private android.graphics.RectF bitmapRect;
    
    @kotlin.jvm.JvmOverloads
    public CropView(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.Nullable
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    @java.lang.Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    }
    
    public final void setImageEvent(@org.jetbrains.annotations.Nullable
    android.graphics.RectF imageRect) {
    }
    
    public final void resetPoints() {
    }
    
    @java.lang.Override
    protected void onDraw(@org.jetbrains.annotations.NotNull
    android.graphics.Canvas canvas) {
    }
    
    @java.lang.Override
    public boolean onTouchEvent(@org.jetbrains.annotations.NotNull
    android.view.MotionEvent event) {
        return false;
    }
    
    private final int getHandleIndex(float x, float y) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<android.graphics.PointF> getCropPoints() {
        return null;
    }
    
    @kotlin.jvm.JvmOverloads
    public CropView(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads
    public CropView(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.Nullable
    android.util.AttributeSet attrs) {
        super(null);
    }
}