package com.acompany.lift.features.sessions.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.acompany.lift.data.model.Session
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.features.sessions.data.SessionDetailViewModel
import kotlinx.coroutines.GlobalScope

@Composable
fun SessionDetailScreen(viewModel: SessionDetailViewModel, session: Session, onSessionDeleted: () -> Unit) {
    val openDialog = remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = session.routine.name) }, actions = {
            IconButton(onClick = { openDialog.value = true }) {
                Icon(imageVector = Icons.Rounded.DeleteForever, contentDescription = "Delete")
            }
        })
    }) {

        if (openDialog.value) {
            DeleteConfirmationAlertDialog { confirmation ->
                if (confirmation) {
                    viewModel.deleteSession(session)
                    onSessionDeleted()
                }
            }
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
        viewModel = SessionDetailViewModel(DummyAppRepository(), GlobalScope),
        session = DummyAppRepository.sessions.first()
    ) {}
}