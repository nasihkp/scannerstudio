package com.example.scanner.ui.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.example.scanner.databinding.ActivitySettingsBinding
import com.example.scanner.ui.MainActivity
import com.example.scanner.utils.GoogleDriveManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val PREFS_NAME = "scanner_prefs"
    private val PREF_DARK_MODE = "dark_mode"

    private val signInLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(com.google.android.gms.common.api.ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: com.google.android.gms.common.api.ApiException) {
                Toast.makeText(this, "Sign in failed: ${e.statusCode}", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener { finish() }

        setupUI()
        updateUI(GoogleDriveManager.getSignedInAccount(this))
    }

    private fun setupUI() {
        // Sign In
        binding.btnGoogleSignIn.setOnClickListener {
            val intent = GoogleDriveManager.getSignInIntent(this)
            signInLauncher.launch(intent)
        }

        // Sign Out
        binding.btnSignOut.setOnClickListener {
            // Confirm sign out?
            GoogleDriveManager.signOut(this) {
                FirebaseAuth.getInstance().signOut()
                updateUI(null)
                Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
                 // Redirect to ensure Home updates or just finish? 
                 // If we finish, Home onResume updates.
            }
        }

        // Dark Mode
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        binding.switchDarkMode.isChecked = prefs.getBoolean(PREF_DARK_MODE, false)
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean(PREF_DARK_MODE, isChecked).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        // Scan Settings (Mocked for now)
        binding.rowScanQuality.setOnClickListener {
            Toast.makeText(this, "Scan Quality: High (Default)", Toast.LENGTH_SHORT).show()
        }
        binding.rowPageSize.setOnClickListener {
            Toast.makeText(this, "Page Size: A4 (Default)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            binding.layoutLoggedIn.visibility = View.VISIBLE
            binding.layoutGuest.visibility = View.GONE
            binding.btnSignOut.visibility = View.VISIBLE

            binding.tvUserName.text = account.displayName ?: "User"
            binding.tvUserEmail.text = account.email ?: ""
            binding.tvProfileInitials.text = (account.displayName?.firstOrNull() ?: 'U').toString()

            if (account.photoUrl != null) {
                Glide.with(this)
                    .load(account.photoUrl)
                    .circleCrop()
                    .into(object : com.bumptech.glide.request.target.CustomTarget<android.graphics.drawable.Drawable>() {
                        override fun onResourceReady(resource: android.graphics.drawable.Drawable, transition: com.bumptech.glide.request.transition.Transition<in android.graphics.drawable.Drawable>?) {
                            binding.tvProfileInitials.visibility = View.GONE
                            binding.ivProfileImage.visibility = View.VISIBLE
                            binding.ivProfileImage.setImageDrawable(resource)
                        }
                        override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {}
                    })
            }
        } else {
            binding.layoutLoggedIn.visibility = View.GONE
            binding.layoutGuest.visibility = View.VISIBLE
            binding.btnSignOut.visibility = View.GONE
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val idToken = account.idToken
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    updateUI(account)
                    Toast.makeText(this, "Signed in as ${account.email}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Firebase Auth Failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
}
