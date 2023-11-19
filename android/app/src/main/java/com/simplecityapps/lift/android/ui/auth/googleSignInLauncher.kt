package com.simplecityapps.lift.android.ui.auth

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.auth.signin.GoogleSignInProvider
import com.simplecityapps.lift.common.repository.AuthRepository
import com.simplecityapps.lift.logging.logcat
import kotlinx.coroutines.launch

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
    authRepository: AuthRepository
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
            authRepository.updateAuthState(AsyncState.Idle)
        }
    }
}