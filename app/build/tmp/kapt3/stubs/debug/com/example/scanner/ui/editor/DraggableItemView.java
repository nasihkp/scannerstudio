package com.example.scanner.ui.editor;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u0018\u001a\u00020\u0019H\u0002R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/example/scanner/ui/editor/DraggableItemView;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "itemType", "Lcom/example/scanner/ui/editor/ItemType;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Lcom/example/scanner/ui/editor/ItemType;Landroid/util/AttributeSet;)V", "editText", "Landroidx/appcompat/widget/AppCompatEditText;", "getEditText", "()Landroidx/appcompat/widget/AppCompatEditText;", "setEditText", "(Landroidx/appcompat/widget/AppCompatEditText;)V", "isResizing", "", "getItemType", "()Lcom/example/scanner/ui/editor/ItemType;", "lastX", "", "lastY", "resizeHandle", "Landroid/widget/ImageView;", "setupTouchListeners", "", "app_debug"})
public final class DraggableItemView extends android.widget.FrameLayout {
    @org.jetbrains.annotations.NotNull
    private final com.example.scanner.ui.editor.ItemType itemType = null;
    private float lastX = 0.0F;
    private float lastY = 0.0F;
    private boolean isResizing = false;
    @org.jetbrains.annotations.Nullable
    private androidx.appcompat.widget.AppCompatEditText editText;
    @org.jetbrains.annotations.NotNull
    private final android.widget.ImageView resizeHandle = null;
    
    @kotlin.jvm.JvmOverloads
    public DraggableItemView(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.example.scanner.ui.editor.ItemType itemType, @org.jetbrains.annotations.Nullable
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.scanner.ui.editor.ItemType getItemType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final androidx.appcompat.widget.AppCompatEditText getEditText() {
        return null;
    }
    
    public final void setEditText(@org.jetbrains.annotations.Nullable
    androidx.appcompat.widget.AppCompatEditText p0) {
    }
    
    private final void setupTouchListeners() {
    }
    
    @kotlin.jvm.JvmOverloads
    public DraggableItemView(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads
    public DraggableItemView(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.example.scanner.ui.editor.ItemType itemType) {
        super(null);
    }
}