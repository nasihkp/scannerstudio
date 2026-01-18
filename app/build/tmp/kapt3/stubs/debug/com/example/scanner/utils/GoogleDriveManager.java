package com.example.scanner.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\f\u001a\u00020\rJ\u001c\u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\r2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0013J&\u0010\u0014\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0018R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/example/scanner/utils/GoogleDriveManager;", "", "()V", "RC_SIGN_IN", "", "getOrCreateAppFolder", "", "driveService", "Lcom/google/api/services/drive/Drive;", "folderName", "getSignInIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "getSignedInAccount", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;", "signOut", "", "callback", "Lkotlin/Function0;", "uploadFileToDrive", "file", "Ljava/io/File;", "mimeType", "(Landroid/content/Context;Ljava/io/File;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class GoogleDriveManager {
    private static final int RC_SIGN_IN = 9001;
    @org.jetbrains.annotations.NotNull
    public static final com.example.scanner.utils.GoogleDriveManager INSTANCE = null;
    
    private GoogleDriveManager() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final android.content.Intent getSignInIntent(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.android.gms.auth.api.signin.GoogleSignInAccount getSignedInAccount(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    public final void signOut(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> callback) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object uploadFileToDrive(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.io.File file, @org.jetbrains.annotations.NotNull
    java.lang.String mimeType, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final java.lang.String getOrCreateAppFolder(com.google.api.services.drive.Drive driveService, java.lang.String folderName) {
        return null;
    }
}