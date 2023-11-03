package com.simplecityapps.lift.auth.signin

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.auth.AuthRepository
import com.simplecityapps.lift.logging.LogPriority
import com.simplecityapps.lift.logging.asLog
import com.simplecityapps.lift.logging.logcat
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides Google Sign-In functionality and interacts with [AuthRepository] for authentication operations.
 *
 * @property authRepository The AuthManager instance to interact with for authentication operations.
 * @property googleSignInClient The GoogleSignInClient instance to initiate the sign in process.
 */
@Singleton
class GoogleSignInProvider @Inject constructor(
    private val authRepository: AuthRepository,
    private val googleSignInClient: GoogleSignInClient,
) {

    /**
     * Initiates the sign-in process.
     *
     * @param launcher The activity result launcher to use for starting the sign in activity.
     */
    fun signIn(launcher: ActivityResultLauncher<Intent>) {
        authRepository.updateAuthState(AsyncState.Loading)
        try {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        } catch (e: Exception) {
            logcat(LogPriority.ERROR) { "signIn() failed: ${e.asLog()}" }
            authRepository.updateAuthState(AsyncState.Failure(e))
        }
    }

    /**
     * Handles the result of the sign-in process.
     *
     * @param intent The intent containing the result data of the sign in process.
     */
    suspend fun handleSignInResult(intent: Intent?) {
        authRepository.updateAuthState(AsyncState.Loading)
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(intent).getResult(ApiException::class.java)
            // Get the IdToken from the GoogleSignInAccount object
            val idToken = account?.idToken
            if (idToken != null) {
                // Pass the token to AuthManager
                authRepository.signIn(idToken)
            } else {
                // Handle sign-in failure
                logcat(LogPriority.ERROR) { "Failed to sign in, idToken null" }
                authRepository.updateAuthState(AsyncState.Failure(Exception("authManager.signIn() failed: idToken null")))
            }
        } catch (e: ApiException) {
            // Handle the exception
            logcat(LogPriority.ERROR) { "Failed to sign in: ${e.asLog()}" }
            authRepository.updateAuthState(AsyncState.Failure(e))
        }
    }
}
