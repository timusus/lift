package com.simplecityapps.lift.common.auth

import com.simplecityapps.lift.common.model.User

interface AuthProvider {
    fun getUser(): User?
    suspend fun signIn(idToken: String)
    suspend fun signOut()
}