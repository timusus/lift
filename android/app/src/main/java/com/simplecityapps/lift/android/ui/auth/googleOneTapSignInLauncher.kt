package com.simplecityapps.lift.android.ui.auth

import android.app.Activity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.auth.AuthRepository
import com.simplecityapps.lift.auth.signin.GoogleOneTapSignInProvider
import com.simplecityapps.lift.logging.logcat
import kotlinx.coroutines.launch

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
    authRepository: AuthRepository,
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
            authRepository.updateAuthState(AsyncState.Idle)
            onUserCancelled()
        }
    }
}