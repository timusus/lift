package com.simplecityapps.lift.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.simplecityapps.lift.common.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthProvider @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthProvider {

    override fun getUser(): User? {
        return firebaseAuth.currentUser?.toUser()
    }

    override suspend fun signIn(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).await()
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }
}

private fun FirebaseUser.toUser(): User {
    return User(
        id = uid,
        displayName = displayName,
        email = email,
        photoUrl = photoUrl?.toString()
    )
}