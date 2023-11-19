package com.simplecityapps.lift.auth.signout

import com.google.android.gms.auth.api.identity.SignInClient
import com.simplecityapps.lift.common.auth.SignOutProvider
import com.simplecityapps.lift.logging.LogPriority
import com.simplecityapps.lift.logging.asLog
import com.simplecityapps.lift.logging.logcat
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides Google Sign-out functionality.
 *
 * @property signInClient The Google One Tap []SignInClient] instance to initiate the sign out process.
 */
@Singleton
class GoogleOneTapSignOutProvider @Inject constructor(
    private val signInClient: SignInClient
) : SignOutProvider {

    /**
     * Initiates the sign-out process.
     */
    override suspend fun signOut() {
        try {
            signInClient.signOut().await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            logcat(LogPriority.ERROR) { "signOut() failed: ${e.asLog()}" }
        }
    }
}

