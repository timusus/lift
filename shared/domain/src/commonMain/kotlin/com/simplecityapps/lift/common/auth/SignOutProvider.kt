package com.simplecityapps.lift.common.auth

/**
 * Initiates the sign-out process.
 */
interface SignOutProvider {
    suspend fun signOut()
}