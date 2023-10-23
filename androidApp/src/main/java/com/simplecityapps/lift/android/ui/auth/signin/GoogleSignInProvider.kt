package com.simplecityapps.lift.android.ui.auth.signin

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.android.ui.auth.AuthManager
import com.simplecityapps.shuttle.logging.logcat
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.asLog
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Creates and provides a [ManagedActivityResultLauncher] for initiating Google Sign-In
 * and handling the result within a Composable.
 *
 * The returned launcher can be used to launch the Google Sign-In activity, and will
 * automatically handle the result, passing it to the [GoogleSignInProvider] for further processing.
 *
 * @param googleSignInProvider The [GoogleSignInProvider] instance used to handle the sign-in result.
 * @return A [ManagedActivityResultLauncher] that can be used to launch the Google Sign-In activity.
 */
@Composable
fun googleSignInLauncher(
    googleSignInProvider: GoogleSignInProvider,
    authManager: AuthManager
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            scope.launch {
                googleSignInProvider.handleSignInResult(result.data)
            }
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            logcat(tag = "GoogleOneTapSignIn") { "Sign in failed - Google sign in closed.}" }
            authManager.updateAuthState(AsyncState.Idle)
        }
    }
}

/**
 * Provides Google Sign-In functionality and interacts with [AuthManager] for authentication operations.
 *
 * @property authManager The AuthManager instance to interact with for authentication operations.
 * @property googleSignInClient The GoogleSignInClient instance to initiate the sign in process.
 */
@Singleton
class GoogleSignInProvider @Inject constructor(
    private val authManager: AuthManager,
    private val googleSignInClient: GoogleSignInClient,
) {

    /**
     * Initiates the sign-in process.
     *
     * @param launcher The activity result launcher to use for starting the sign in activity.
     */
    fun signIn(launcher: ActivityResultLauncher<Intent>) {
        authManager.updateAuthState(AsyncState.Loading)
        try {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        } catch (e: Exception) {
            logcat(LogPriority.ERROR) { "signIn() failed: ${e.asLog()}" }
            authManager.updateAuthState(AsyncState.Failure(e))
        }
    }

    /**
     * Handles the result of the sign-in process.
     *
     * @param intent The intent containing the result data of the sign in process.
     */
    suspend fun handleSignInResult(intent: Intent?) {
        authManager.updateAuthState(AsyncState.Loading)
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(intent).getResult(ApiException::class.java)
            // Get the IdToken from the GoogleSignInAccount object
            val idToken = account?.idToken
            if (idToken != null) {
                // Pass the token to AuthManager
                authManager.signIn(idToken)
            } else {
                // Handle sign-in failure
                logcat(LogPriority.ERROR) { "Failed to sign in, idToken null" }
                authManager.updateAuthState(AsyncState.Failure(Exception("authManager.signIn() failed: idToken null")))
            }
        } catch (e: ApiException) {
            // Handle the exception
            logcat(LogPriority.ERROR) { "Failed to sign in: ${e.asLog()}" }
            authManager.updateAuthState(AsyncState.Failure(e))
        }
    }
}
