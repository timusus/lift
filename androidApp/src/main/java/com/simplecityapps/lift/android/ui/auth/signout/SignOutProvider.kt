package com.simplecityapps.lift.android.ui.auth.signout

/**
 * Initiates the sign-out process.
 */
interface SignOutProvider {
    suspend fun signOut()
}