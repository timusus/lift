package com.simplecityapps.lift.android.ui.auth.signin

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.android.ui.auth.AuthManager
import com.simplecityapps.lift.android.ui.auth.di.AuthModule
import com.simplecityapps.shuttle.logging.LogPriority
import com.simplecityapps.shuttle.logging.logcat
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import logcat.asLog
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Creates and provides a [ManagedActivityResultLauncher] for initiating Google One Tap Sign-In
 * and handling the result within a Composable.
 *
 * The returned launcher can be used to launch the Google Sign-In activity, and will
 * automatically handle the result, passing it to the [GoogleOneTapSignInProvider] for further processing.
 *
 * @param googleOneTapSignInProvider The [GoogleOneTapSignInProvider] instance used to handle the sign-in result.
 * @return A [ManagedActivityResultLauncher] that can be used to launch the Google Sign-In activity.
 */
@Composable
fun googleOneTapSignInLauncher(
    googleOneTapSignInProvider: GoogleOneTapSignInProvider,
    authManager: AuthManager,
    onUserCancelled: () -> Unit
): ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult> {
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            scope.launch {
                googleOneTapSignInProvider.handleSignInResult(
                    intent = result.data,
                    onUserCancelled = onUserCancelled
                )
            }
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            logcat(tag = "GoogleOneTapSignIn") { "Sign in failed - One-tap dialog closed.}" }
            authManager.updateAuthState(AsyncState.Idle)
            onUserCancelled()
        }
    }
}

/**
 * Provides Google Sign-In functionality and interacts with [AuthManager] for authentication operations.
 *
 * @property authManager The AuthManager instance to interact with for authentication operations.
 * @property oneTapSignInClient The [SignInClient] instance to initiate the sign in process.
 */
@Singleton
class GoogleOneTapSignInProvider @Inject constructor(
    private val authManager: AuthManager,
    private val oneTapSignInClient: SignInClient
) {

    /**
     * Initiates the sign-in process.
     *
     * @param launcher The activity result launcher to use for starting the sign in activity.
     */
    suspend fun signIn(launcher: ActivityResultLauncher<IntentSenderRequest>, onOneTapBlocked: () -> Unit) {
        authManager.updateAuthState(AsyncState.Loading)
        try {
            val result = attemptSignInOrSignUp(clientId = AuthModule.CLIENT_ID, filterByAccounts = true)
            val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent).build()
            launcher.launch(intentSenderRequest)
        } catch (cancellationException: CancellationException) {
            throw cancellationException
        } catch (e: ApiException) {
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    logcat { "signIn() failed, cancelled" }
                    authManager.updateAuthState(AsyncState.Failure(e))
                    onOneTapBlocked()
                }

                else -> {
                    logcat(LogPriority.ERROR) { "signIn() failed: ${e.asLog()}" }
                    authManager.updateAuthState(AsyncState.Failure(e))
                }
            }
        }
    }

    /**
     * Handles the result of the sign-in process.
     *
     * @param intent The intent containing the result data of the sign in process.
     */
    suspend fun handleSignInResult(intent: Intent?, onUserCancelled: () -> Unit) {
        authManager.updateAuthState(AsyncState.Loading)
        try {
            val credential = oneTapSignInClient.getSignInCredentialFromIntent(intent)
            val idToken = credential.googleIdToken
            if (idToken != null) {
                authManager.signIn(idToken)
            } else {
                logcat(LogPriority.ERROR) { "Failed to sign in, idToken null" }
                authManager.updateAuthState(AsyncState.Failure(Exception("authManager.signIn() failed: idToken null")))
            }
        } catch (e: ApiException) {
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    logcat { "Sign in failed - One-tap dialog closed.}" }
                    authManager.updateAuthState(AsyncState.Idle)
                    onUserCancelled()
                }

                else -> {
                    logcat(LogPriority.ERROR) { "Failed to sign in: ${e.asLog()}" }
                    authManager.updateAuthState(AsyncState.Failure(e))
                }
            }
        }
    }

    /**
     * Initiates the Google One Tap sign-in or sign-up process.
     *
     * This suspending function attempts to initiate a sign-in process with Google's One Tap Sign-In. If sign-in fails,
     * it retries the process as a sign-up.
     *
     * @param [filterByAccounts] A boolean indicating whether to filter by authorized accounts for sign-in.
     * Default is true for sign-in, and false for sign-up.
     * @return [BeginSignInResult] The result of the sign-in or sign-up attempt.
     *
     * @throws [CancellationException] if the coroutine running this function is cancelled.
     * @throws [Exception] for any other exception occurring during the process, if sign-up also fails.
     */
    private suspend fun attemptSignInOrSignUp(
        clientId: String,
        filterByAccounts: Boolean = true
    ): BeginSignInResult {
        return try {
            oneTapSignInClient.beginSignIn(
                BeginSignInRequest.builder()
                    .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                            .setSupported(true)
                            .setServerClientId(clientId)
                            .setFilterByAuthorizedAccounts(filterByAccounts)
                            .build()
                    ).build()
            ).await()
        } catch (cancellationException: CancellationException) {
            throw cancellationException
        } catch (e: Exception) {
            if (filterByAccounts) {
                attemptSignInOrSignUp(
                    clientId = clientId,
                    filterByAccounts = false
                )
            } else {
                throw e
            }
        }
    }
}
