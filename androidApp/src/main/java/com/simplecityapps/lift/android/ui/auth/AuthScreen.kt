package com.simplecityapps.lift.android.ui.auth

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.android.ui.auth.signin.GoogleOneTapSignInProvider
import com.simplecityapps.lift.android.ui.auth.signin.GoogleSignInProvider
import com.simplecityapps.lift.android.ui.auth.signin.googleOneTapSignInLauncher
import com.simplecityapps.lift.android.ui.auth.signin.googleSignInLauncher
import com.simplecityapps.lift.android.ui.theme.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val googleOneTapSignInProvider: GoogleOneTapSignInProvider,
    private val googleSignInProvider: GoogleSignInProvider,
    private val authManager: AuthManager
) : ViewModel() {

    val authState = authManager.authState

    fun getAuthManager(): AuthManager {
        return authManager
    }

    fun getGoogleOneTapSignInProvider(): GoogleOneTapSignInProvider {
        return googleOneTapSignInProvider
    }

    fun getGoogleSignInProvider(): GoogleSignInProvider {
        return googleSignInProvider
    }
}

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
) {
    var useOneTap by remember { mutableStateOf(true) }
    val googleOneTapSignInProvider = viewModel.getGoogleOneTapSignInProvider()
    val googleOneTapLauncher = googleOneTapSignInLauncher(
        googleOneTapSignInProvider = googleOneTapSignInProvider,
        authManager = viewModel.getAuthManager(),
        onUserCancelled = {
            useOneTap = false
        }
    )

    val googleSignInProvider = viewModel.getGoogleSignInProvider()
    val googleSignInLauncher = googleSignInLauncher(
        googleSignInProvider = googleSignInProvider,
        authManager = viewModel.getAuthManager(),
    )

    val authState by viewModel.authState.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    AuthScreen(
        authsState = authState,
        onSignInClick = {
            coroutineScope.launch {
                if (useOneTap) {
                    googleOneTapSignInProvider.signIn(
                        launcher = googleOneTapLauncher,
                        onOneTapBlocked = {
                            useOneTap = false
                        }
                    )
                } else {
                    googleSignInProvider.signIn(
                        launcher = googleSignInLauncher
                    )
                }
            }
        },
        onSignInSuccess = {
            onNavigateToHome()
        }
    )
}

@Composable
fun AuthScreen(
    authsState: AsyncState<User>,
    onSignInClick: () -> Unit = {},
    onSignInSuccess: () -> Unit = {}
) {
    Scaffold { padding ->
        when (authsState) {
            is AsyncState.Idle -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Button(
                        onClick = { onSignInClick() }) {
                        Text(text = "Sign in")
                    }
                }
            }

            is AsyncState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text("Sign in successful")
                }
                LaunchedEffect(authsState) {
                    onSignInSuccess()
                }
            }

            is AsyncState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }

            is AsyncState.Failure -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("An error occurred")
                    Spacer(modifier = Modifier.size(4.dp))
                    Button(
                        onClick = { onSignInClick() }) {
                        Text(text = "Retry")
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AuthScreenPreviewLoading() {
    AppTheme {
        AuthScreen(authsState = AsyncState.Loading)
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AuthScreenPreviewReady() {
    AppTheme {
        AuthScreen(
            authsState = AsyncState.Success(
                User(
                    id = "",
                    displayName = "Tim",
                    email = null,
                    photoUrl = null
                )
            )
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AuthScreenPreviewFailure() {
    AppTheme {
        AuthScreen(authsState = AsyncState.Failure(Exception("Some Error")))
    }
}