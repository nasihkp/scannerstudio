package com.example.scanner.ui.tools;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0018B\'\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u00a2\u0006\u0002\u0010\nJ\b\u0010\r\u001a\u00020\bH\u0016J\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004J\u0018\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\bH\u0016J\u0018\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\bH\u0016J\u0014\u0010\u0016\u001a\u00020\t2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/example/scanner/ui/tools/MergeDocumentAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/scanner/ui/tools/MergeDocumentAdapter$ViewHolder;", "documents", "", "Lcom/example/scanner/data/model/DocumentEntity;", "onSelectionChanged", "Lkotlin/Function1;", "", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "selectedIds", "", "getItemCount", "getSelectedDocuments", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setDocuments", "newDocs", "ViewHolder", "app_debug"})
public final class MergeDocumentAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.scanner.ui.tools.MergeDocumentAdapter.ViewHolder> {
    @org.jetbrains.annotations.NotNull
    private java.util.List<com.example.scanner.data.model.DocumentEntity> documents;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<java.lang.Integer, kotlin.Unit> onSelectionChanged = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Set<java.lang.Integer> selectedIds = null;
    
    public MergeDocumentAdapter(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.scanner.data.model.DocumentEntity> documents, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onSelectionChanged) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.example.scanner.ui.tools.MergeDocumentAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.example.scanner.ui.tools.MergeDocumentAdapter.ViewHolder holder, int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    public final void setDocuments(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.scanner.data.model.DocumentEntity> newDocs) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.scanner.data.model.DocumentEntity> getSelectedDocuments() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000f\u00a8\u0006\u0012"}, d2 = {"Lcom/example/scanner/ui/tools/MergeDocumentAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Landroid/view/View;)V", "ivCheckbox", "Landroid/widget/ImageView;", "getIvCheckbox", "()Landroid/widget/ImageView;", "root", "getRoot", "()Landroid/view/View;", "tvDate", "Landroid/widget/TextView;", "getTvDate", "()Landroid/widget/TextView;", "tvName", "getTvName", "app_debug"})
    public static final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView tvName = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView tvDate = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.ImageView ivCheckbox = null;
        @org.jetbrains.annotations.NotNull
        private final android.view.View root = null;
        
        public ViewHolder(@org.jetbrains.annotations.NotNull
        android.view.View view) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getTvName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getTvDate() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.ImageView getIvCheckbox() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.view.View getRoot() {
            return null;
        }
    }
}