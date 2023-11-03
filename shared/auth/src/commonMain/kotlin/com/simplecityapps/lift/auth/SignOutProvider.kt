package com.simplecityapps.lift.auth

/**
 * Initiates the sign-out process.
 */
interface SignOutProvider {
    suspend fun signOut()
}