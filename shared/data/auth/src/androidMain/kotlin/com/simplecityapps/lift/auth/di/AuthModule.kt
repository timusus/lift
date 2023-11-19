package com.simplecityapps.lift.auth.di

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.simplecityapps.lift.auth.FirebaseAuthProvider
import com.simplecityapps.lift.auth.signout.GoogleOneTapSignOutProvider
import com.simplecityapps.lift.auth.signout.GoogleSignInSignOutProvider
import com.simplecityapps.lift.common.auth.AuthProvider
import com.simplecityapps.lift.common.auth.SignOutProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    @IntoSet
    fun bindGoogleOneTapSignInProvider(
        googleOneTapSignOutProvider: GoogleOneTapSignOutProvider
    ): SignOutProvider

    @Binds
    @IntoSet
    fun bindGoogleSignInSignOutOutProvider(
        googleSignInSignOutOutProvider: GoogleSignInSignOutProvider
    ): SignOutProvider

    @Binds
    fun bindsAuthProvider(
        firebaseAuthProvider: FirebaseAuthProvider
    ): AuthProvider

    companion object {

        val CLIENT_ID = "229223510486-cfp9ct3s3vb1mu43g0l436jqr551be6h.apps.googleusercontent.com"

        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }

        @Provides
        fun provideOneTapClient(
            @ApplicationContext context: Context
        ): SignInClient {
            return Identity.getSignInClient(context)
        }

        @Provides
        @Singleton
        fun provideGoogleSignInClient(
            @ApplicationContext context: Context
        ): GoogleSignInClient {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(CLIENT_ID)
                .requestEmail()
                .build()

            return GoogleSignIn.getClient(context, gso)
        }
    }
}