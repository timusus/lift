package com.simplecityapps.lift.android.ui.auth.signin

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.auth.AuthRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GoogleSignInProviderTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInProvider: GoogleSignInProvider

    @BeforeEach
    fun setup() {
        authRepository = mockk<AuthRepository>(relaxed = true)
        googleSignInClient = mockk<GoogleSignInClient>()
        googleSignInProvider = GoogleSignInProvider(authRepository, googleSignInClient)

        mockkStatic(GoogleSignIn::class)
    }

    @AfterEach
    fun cleanup() {
        unmockkStatic(GoogleSignIn::class)
    }

    @Test
    fun `test signIn success`() {
        // Arrange
        val launcher = mockk<ActivityResultLauncher<Intent>>(relaxed = true)
        val signInIntent = mockk<Intent>()
        every { googleSignInClient.signInIntent } returns signInIntent

        // Act
        googleSignInProvider.signIn(launcher)

        // Assert
        verify { launcher.launch(signInIntent) }
    }

    @Test
    fun `test handleSignInResult success`() = runBlocking {
        // Arrange
        val intent = mockk<Intent>()
        val account = mockk<GoogleSignInAccount>()
        val idToken = "token"

        every { GoogleSignIn.getSignedInAccountFromIntent(intent) } returns mockk {
            every { getResult(ApiException::class.java) } returns account
        }
        every { account.idToken } returns idToken

        // Act
        googleSignInProvider.handleSignInResult(intent)

        // Assert
        coVerify { authRepository.signIn(idToken) }
    }

    @Test
    fun `test handleSignInResult failure due to null idToken`() = runBlocking {
        // Arrange
        val intent = mockk<Intent>()
        val account = mockk<GoogleSignInAccount>()
        every { GoogleSignIn.getSignedInAccountFromIntent(intent) } returns mockk {
            every { getResult(ApiException::class.java) } returns account
        }
        every { account.idToken } returns null

        // Act
        googleSignInProvider.handleSignInResult(intent)

        // Assert
        verify { authRepository.updateAuthState(match { it is AsyncState.Failure }) }
    }

    @Test
    fun `test handleSignInResult failure due to ApiException`() = runBlocking {
        // Arrange
        val intent = mockk<Intent>()
        val exception = ApiException(Status.RESULT_CANCELED)
        every { GoogleSignIn.getSignedInAccountFromIntent(intent) } throws exception

        // Act
        googleSignInProvider.handleSignInResult(intent)

        // Assert
        verify { authRepository.updateAuthState(AsyncState.Failure(exception)) }
    }
}