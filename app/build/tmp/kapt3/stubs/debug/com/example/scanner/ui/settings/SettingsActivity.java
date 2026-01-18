package com.example.scanner.ui.settings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0012\u0010\u0010\u001a\u00020\r2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014J\b\u0010\u0013\u001a\u00020\rH\u0002J\u0012\u0010\u0014\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\b\u001a\u0010\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\n0\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/example/scanner/ui/settings/SettingsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "PREFS_NAME", "", "PREF_DARK_MODE", "binding", "Lcom/example/scanner/databinding/ActivitySettingsBinding;", "signInLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "firebaseAuthWithGoogle", "", "account", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupUI", "updateUI", "app_debug"})
public final class SettingsActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.scanner.databinding.ActivitySettingsBinding binding;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String PREFS_NAME = "scanner_prefs";
    @org.jetbrains.annotations.NotNull
    private final java.lang.String PREF_DARK_MODE = "dark_mode";
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> signInLauncher = null;
    
    public SettingsActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupUI() {
    }
    
    private final void updateUI(com.google.android.gms.auth.api.signin.GoogleSignInAccount account) {
    }
    
    private final void firebaseAuthWithGoogle(com.google.android.gms.auth.api.signin.GoogleSignInAccount account) {
    }
}