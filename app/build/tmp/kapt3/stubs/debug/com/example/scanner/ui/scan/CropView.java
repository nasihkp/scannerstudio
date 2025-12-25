package com.example.scanner.ui.scan;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001(B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0015\u001a\u00020\fJ\u001a\u0010\u0016\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u0010H\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0014J(\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0007H\u0014J\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0016J\u000e\u0010&\u001a\u00020\u001a2\u0006\u0010\'\u001a\u00020\fR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Lcom/example/scanner/ui/scan/CropView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "activeHandle", "Lcom/example/scanner/ui/scan/CropView$Handle;", "cropRect", "Landroid/graphics/RectF;", "handlePaint", "Landroid/graphics/Paint;", "handleRadius", "", "lastX", "lastY", "overlayPaint", "paint", "getCropRect", "getHandleAt", "x", "y", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "onSizeChanged", "w", "h", "oldw", "oldh", "onTouchEvent", "", "event", "Landroid/view/MotionEvent;", "setCropRect", "rect", "Handle", "app_debug"})
public final class CropView extends android.view.View {
    @org.jetbrains.annotations.NotNull
    private final android.graphics.RectF cropRect = null;
    @org.jetbrains.annotations.NotNull
    private final android.graphics.Paint paint = null;
    @org.jetbrains.annotations.NotNull
    private final android.graphics.Paint overlayPaint = null;
    @org.jetbrains.annotations.NotNull
    private final android.graphics.Paint handlePaint = null;
    private final float handleRadius = 30.0F;
    @org.jetbrains.annotations.Nullable
    private com.example.scanner.ui.scan.CropView.Handle activeHandle;
    private float lastX = 0.0F;
    private float lastY = 0.0F;
    
    @kotlin.jvm.JvmOverloads
    public CropView(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.Nullable
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    @java.lang.Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
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
    
    private final com.example.scanner.ui.scan.CropView.Handle getHandleAt(float x, float y) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final android.graphics.RectF getCropRect() {
        return null;
    }
    
    public final void setCropRect(@org.jetbrains.annotations.NotNull
    android.graphics.RectF rect) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2 = {"Lcom/example/scanner/ui/scan/CropView$Handle;", "", "(Ljava/lang/String;I)V", "TOP_LEFT", "TOP_RIGHT", "BOTTOM_LEFT", "BOTTOM_RIGHT", "CENTER", "app_debug"})
    public static enum Handle {
        /*public static final*/ TOP_LEFT /* = new TOP_LEFT() */,
        /*public static final*/ TOP_RIGHT /* = new TOP_RIGHT() */,
        /*public static final*/ BOTTOM_LEFT /* = new BOTTOM_LEFT() */,
        /*public static final*/ BOTTOM_RIGHT /* = new BOTTOM_RIGHT() */,
        /*public static final*/ CENTER /* = new CENTER() */;
        
        Handle() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.example.scanner.ui.scan.CropView.Handle> getEntries() {
            return null;
        }
    }
}