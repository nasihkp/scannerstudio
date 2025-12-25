package com.example.scanner.ui.documents;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0018BU\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\u0002\u0010\nJ\b\u0010\r\u001a\u00020\u000eH\u0016J\u001c\u0010\u000f\u001a\u00020\u00062\n\u0010\u0010\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u000eH\u0016J\u001c\u0010\u0012\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000eH\u0016J\u0014\u0010\u0016\u001a\u00020\u00062\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00050\fR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/example/scanner/ui/documents/DocumentAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/scanner/ui/documents/DocumentAdapter$DocumentViewHolder;", "onDocumentClick", "Lkotlin/Function1;", "Lcom/example/scanner/data/model/DocumentEntity;", "", "onShareClick", "onSaveClick", "onDeleteClick", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "documents", "", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setDocuments", "newDocuments", "DocumentViewHolder", "app_debug"})
public final class DocumentAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.scanner.ui.documents.DocumentAdapter.DocumentViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<com.example.scanner.data.model.DocumentEntity, kotlin.Unit> onDocumentClick = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<com.example.scanner.data.model.DocumentEntity, kotlin.Unit> onShareClick = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<com.example.scanner.data.model.DocumentEntity, kotlin.Unit> onSaveClick = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<com.example.scanner.data.model.DocumentEntity, kotlin.Unit> onDeleteClick = null;
    @org.jetbrains.annotations.NotNull
    private java.util.List<com.example.scanner.data.model.DocumentEntity> documents;
    
    public DocumentAdapter(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.example.scanner.data.model.DocumentEntity, kotlin.Unit> onDocumentClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.example.scanner.data.model.DocumentEntity, kotlin.Unit> onShareClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.example.scanner.data.model.DocumentEntity, kotlin.Unit> onSaveClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.example.scanner.data.model.DocumentEntity, kotlin.Unit> onDeleteClick) {
        super();
    }
    
    public final void setDocuments(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.scanner.data.model.DocumentEntity> newDocuments) {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.example.scanner.ui.documents.DocumentAdapter.DocumentViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.example.scanner.ui.documents.DocumentAdapter.DocumentViewHolder holder, int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/example/scanner/ui/documents/DocumentAdapter$DocumentViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/example/scanner/databinding/ItemDocumentBinding;", "(Lcom/example/scanner/ui/documents/DocumentAdapter;Lcom/example/scanner/databinding/ItemDocumentBinding;)V", "bind", "", "document", "Lcom/example/scanner/data/model/DocumentEntity;", "app_debug"})
    public final class DocumentViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final com.example.scanner.databinding.ItemDocumentBinding binding = null;
        
        public DocumentViewHolder(@org.jetbrains.annotations.NotNull
        com.example.scanner.databinding.ItemDocumentBinding binding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull
        com.example.scanner.data.model.DocumentEntity document) {
        }
    }
}