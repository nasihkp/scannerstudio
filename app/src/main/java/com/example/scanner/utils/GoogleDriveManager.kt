package com.example.scanner.utils

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object GoogleDriveManager {

    private const val RC_SIGN_IN = 9001

    fun getSignInIntent(context: Context): Intent {
        val clientId = context.getString(com.example.scanner.R.string.default_web_client_id)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(clientId)
            .requestScopes(Scope(DriveScopes.DRIVE_FILE))
            .build()


        val client = GoogleSignIn.getClient(context, gso)
        return client.signInIntent
    }
    
    fun getSignedInAccount(context: Context): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(context)
    }

    fun signOut(context: Context, callback: () -> Unit) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val client = GoogleSignIn.getClient(context, gso)
        client.signOut().addOnCompleteListener { callback() }
    }

    suspend fun uploadFileToDrive(context: Context, file: File, mimeType: String): String {
        return withContext(Dispatchers.IO) {
            val account = GoogleSignIn.getLastSignedInAccount(context) 
                ?: throw Exception("No signed-in account found.")
            
            if (!GoogleSignIn.hasPermissions(account, Scope(DriveScopes.DRIVE_FILE))) {
                 throw Exception("Drive permission not granted. Please sign out and sign in again.")
            }
            
            val credential = GoogleAccountCredential.usingOAuth2(
                context, setOf(DriveScopes.DRIVE_FILE)
            )
            credential.selectedAccount = account.account

            val driveService = Drive.Builder(
                AndroidHttp.newCompatibleTransport(),
                GsonFactory.getDefaultInstance(),
                credential
            ).setApplicationName("SmartScan").build()

            // 1. Get or Create Folder
            val folderId = getOrCreateAppFolder(driveService, "SmartScan") 
                ?: throw Exception("Failed to create or find 'SmartScan' folder in Drive.")

            // 2. Upload File to that Folder
            val fileMetadata = com.google.api.services.drive.model.File()
            fileMetadata.name = file.name
            fileMetadata.mimeType = mimeType
            fileMetadata.parents = listOf(folderId) // Set parent folder

            val mediaContent = com.google.api.client.http.FileContent(mimeType, file)

            try {
                val uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute()
                
                uploadedFile.id ?: throw Exception("Upload failed: File ID is null.")
            } catch (e: Exception) {
                 e.printStackTrace()
                 throw Exception("Upload API failed: ${e.message}")
            }
        }
    }

    private fun getOrCreateAppFolder(driveService: Drive, folderName: String): String? {
        try {
            // Search for the folder
            val query = "mimeType = 'application/vnd.google-apps.folder' and name = '$folderName' and trashed = false"
            val result = driveService.files().list()
                .setQ(query)
                .setSpaces("drive")
                .setFields("files(id, name)")
                .execute()

            if (result.files.isNotEmpty()) {
                return result.files[0].id
            }

            // Create the folder if it doesn't exist
            val folderMetadata = com.google.api.services.drive.model.File()
            folderMetadata.name = folderName
            folderMetadata.mimeType = "application/vnd.google-apps.folder"

            val folder = driveService.files().create(folderMetadata)
                .setFields("id")
                .execute()

            return folder.id
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
