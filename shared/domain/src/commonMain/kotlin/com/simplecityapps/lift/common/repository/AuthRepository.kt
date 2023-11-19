package com.simplecityapps.lift.common.repository

import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.common.model.User
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val authState: StateFlow<AsyncState<User>>

    /**
     * Signs in using a provided ID token.
     *
     * @param idToken The ID token used to authenticate.
     */
    suspend fun signIn(idToken: String)

    /**
     * Updates the current authentication state. This method is intended to be used by authentication
     * providers, such as [GoogleOneTapSignInProvider] to update the auth state based on the result of authentication operations.
     *
     * It's crucial to ensure that this method is called appropriately to maintain a consistent
     * authentication state throughout the application.
     *
     * @param state The new [AsyncState] representing the current authentication state.
     */
    fun updateAuthState(state: AsyncState<User>)

    /**
     * Signs out from Firebase and all registered sign out providers.
     */
    suspend fun signOut()
}