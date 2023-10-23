package com.simplecityapps.lift.android.ui.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.android.ui.auth.signin.GoogleOneTapSignInProvider
import com.simplecityapps.lift.android.ui.auth.signout.SignOutProvider
import com.simplecityapps.shuttle.logging.logcat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import logcat.LogPriority
import logcat.asLog
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages authentication using Firebase and coordinates with various sign out providers.
 *
 * @property auth The Firebase authentication instance.
 * @property signOutProviders A set of sign out providers to coordinate sign out operations.
 */
@Singleton
class AuthManager @Inject constructor(
    private val auth: FirebaseAuth,
    private val signOutProviders: Set<@JvmSuppressWildcards SignOutProvider>
) {
    private val _authState = MutableStateFlow<AsyncState<User>>(AsyncState.Idle)
    val authState: StateFlow<AsyncState<User>> = _authState.asStateFlow()

    init {
        auth.currentUser?.let { firebaseUser ->
            updateAuthState(AsyncState.Success(firebaseUser.toUser()))
        }
    }

    /**
     * Signs in using a provided ID token.
     *
     * @param idToken The ID token used to authenticate.
     */
    suspend fun signIn(idToken: String) {
        updateAuthState((AsyncState.Loading))

        val credential = GoogleAuthProvider.getCredential(idToken, null)

        try {
            auth.signInWithCredential(credential).await()
            val user = auth.currentUser?.toUser()
            if (user == null) {
                logcat(LogPriority.ERROR) { "Failed to retrieve Firebase user after authentication" }
                updateAuthState((AsyncState.Failure(Exception("Failed to retrieve Firebase user after authentication"))))
            } else {
                updateAuthState(AsyncState.Success(user))
            }
        } catch (e: Exception) {
            logcat(LogPriority.ERROR) { "Failed to sign in with credentials: ${e.asLog()}" }
            updateAuthState((AsyncState.Failure(e)))
        }
    }

    /**
     * Updates the current authentication state. This method is intended to be used by authentication
     * providers, such as [GoogleOneTapSignInProvider] to update the auth state based on the result of authentication operations.
     *
     * It's crucial to ensure that this method is called appropriately to maintain a consistent
     * authentication state throughout the application.
     *
     * @param state The new [AsyncState] representing the current authentication state.
     */
    fun updateAuthState(state: AsyncState<User>) {
        _authState.update { state }
    }

    /**
     * Signs out from Firebase and all registered sign out providers.
     */
    suspend fun signOut() {
        auth.signOut()
        signOutProviders.forEach { it.signOut() }
        updateAuthState((AsyncState.Idle))
    }
}

fun FirebaseUser.toUser(): User {
    return User(
        id = uid,
        displayName = displayName,
        email = email,
        photoUrl = photoUrl?.toString()
    )
}