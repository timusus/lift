package com.simplecityapps.lift.data.repository

import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.common.auth.AuthProvider
import com.simplecityapps.lift.common.auth.SignOutProvider
import com.simplecityapps.lift.common.model.User
import com.simplecityapps.lift.common.repository.AuthRepository
import com.simplecityapps.lift.logging.LogPriority
import com.simplecityapps.lift.logging.asLog
import com.simplecityapps.lift.logging.logcat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.jvm.JvmSuppressWildcards

/**
 * Manages authentication using Firebase and coordinates with various sign out providers.
 *
 * @property auth The Firebase authentication instance.
 * @property signOutProviders A set of sign out providers to coordinate sign out operations.
 */
class DefaultAuthRepository(
    private val authProvider: AuthProvider,
    private val signOutProviders: Set<@JvmSuppressWildcards SignOutProvider>
) : AuthRepository {
    private val _authState = MutableStateFlow<AsyncState<User>>(AsyncState.Idle)
    override val authState: StateFlow<AsyncState<User>> = _authState.asStateFlow()

    init {
        authProvider.getUser()?.let { user ->
            updateAuthState(AsyncState.Success(user))
        }
    }

    /**
     * Signs in using a provided ID token.
     *
     * @param idToken The ID token used to authenticate.
     */
    override suspend fun signIn(idToken: String) {
        updateAuthState((AsyncState.Loading))

        try {
            authProvider.signIn(idToken)
            val user = authProvider.getUser()
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
    override fun updateAuthState(state: AsyncState<User>) {
        _authState.update { state }
    }

    /**
     * Signs out from Firebase and all registered sign out providers.
     */
    override suspend fun signOut() {
        authProvider.signOut()
        signOutProviders.forEach { it.signOut() }
        updateAuthState((AsyncState.Idle))
    }
}

