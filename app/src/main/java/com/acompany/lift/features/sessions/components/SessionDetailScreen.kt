package com.acompany.lift.features.sessions.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import com.acompany.lift.data.model.Session
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.features.sessions.data.ScreenState
import com.acompany.lift.features.sessions.data.SessionDetailViewModel

@Composable
fun SessionDetailScreen(
    viewModel: SessionDetailViewModel,
    onSessionDeleted: () -> Unit
) {
    val screenState: ScreenState by viewModel.screenState.collectAsState()

    SessionDetailScreen(
        screenState = screenState,
        deleteSession = { session ->
            viewModel.deleteSession(session)
            onSessionDeleted()
        }
    )
}

@Composable
fun SessionDetailScreen(
    screenState: ScreenState,
    deleteSession: (session: Session) -> Unit
) {
    var sessionToDelete by rememberSaveable { mutableStateOf<Session?>(null) }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = (screenState as? ScreenState.Ready)?.session?.routine?.name ?: "Session")
        }, actions = {
            if (screenState is ScreenState.Ready) {
                IconButton(onClick = { sessionToDelete = screenState.session }) {
                    Icon(imageVector = Icons.Rounded.DeleteForever, contentDescription = "Delete")
                }
            }
        })
    }) {
        sessionToDelete?.let { session ->
            DeleteConfirmationAlertDialog(onDismiss = { confirmation ->
                if (confirmation) {
                    deleteSession(session)
                }
                sessionToDelete = null
            })
        }
    }
}

@Composable
fun DeleteConfirmationAlertDialog(onDismiss: (confirmation: Boolean) -> Unit) {
    AlertDialog(
        onDismissRequest = {
            onDismiss(false)
        },
        title = {
            Text(text = "Delete session?")
        },
        text = {
            Text("This session will be permanently deleted")
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss(true)
                }) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss(false)
                }) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
fun SessionDetailScreenPreview() {
    SessionDetailScreen(
        screenState = ScreenState.Ready(DummyAppRepository.sessions.first())
    ) {}
}