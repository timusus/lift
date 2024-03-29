package com.simplecityapps.lift.android.ui.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.simplecityapps.lift.android.ui.theme.AppTheme
import com.simplecityapps.lift.common.repository.AuthRepository
import com.simplecityapps.lift.common.repository.SyncRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val syncRepositories: @JvmSuppressWildcards Set<SyncRepository>
) : ViewModel() {

    suspend fun signOut() {
        authRepository.signOut()
    }

    suspend fun importData() {
        syncRepositories.forEach { it.importData() }
    }
}

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    SettingsScreen(
        onSignOut = {
            coroutineScope.launch {
                viewModel.signOut()
                onNavigateHome()
            }
        },
        onImportData = {
            coroutineScope.launch {
                viewModel.importData()
            }
        }
    )
}

@Composable
internal fun SettingsScreen(
    onSignOut: () -> Unit = {},
    onImportData: () -> Unit = {}
) {
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Button(onClick = { onSignOut() }) {
                Text(text = "Sign out")
            }
            Button(onClick = { onImportData() }) {
                Text(text = "Import Data")
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SettingsScreenPreview() {
    AppTheme {
        SettingsScreen(onSignOut = {})
    }
}