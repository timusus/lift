package com.acompany.lift.features.sessions.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acompany.lift.common.DateFormatter
import com.acompany.lift.data.model.Session
import com.acompany.lift.features.main.components.LiftBottomNavigation
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.features.main.data.NavDestination
import com.acompany.lift.features.sessions.detail.data.ScreenState
import com.acompany.lift.features.sessions.detail.data.SessionDetailViewModel
import com.acompany.lift.theme.MaterialColors
import com.acompany.lift.theme.MaterialTypography
import java.util.*

@Composable
fun SessionDetailScreen(
    viewModel: SessionDetailViewModel,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val screenState: ScreenState by viewModel.screenState.collectAsState()

    SessionDetailScreen(
        screenState = screenState,
        currentRoute = currentRoute,
        dateFormatter = viewModel.dateFormatter,
        deleteSession = { session ->
            viewModel.deleteSession(session)
            onDismiss()
        },
        onNavigate = onNavigate,
        onDismiss = onDismiss
    )
}

@Composable
fun SessionDetailScreen(
    screenState: ScreenState,
    currentRoute: String?,
    dateFormatter: DateFormatter,
    deleteSession: (session: Session) -> Unit = {},
    onNavigate: (String) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    var sessionToDelete by rememberSaveable { mutableStateOf<Session?>(null) }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = (screenState as? ScreenState.Ready)?.session?.routine?.name ?: "Session")
            },
            navigationIcon = {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "back to routines"
                    )
                }
            },
            actions = {
                if (screenState is ScreenState.Ready) {
                    IconButton(onClick = { sessionToDelete = screenState.session }) {
                        Icon(imageVector = Icons.Rounded.DeleteForever, contentDescription = "Delete")
                    }
                }
            })
    }, content = {
        sessionToDelete?.let { session ->
            DeleteConfirmationAlertDialog(onDismiss = { confirmation ->
                if (confirmation) {
                    deleteSession(session)
                }
                sessionToDelete = null
            })
        }
        when (screenState) {
            is ScreenState.Loading -> {

            }
            is ScreenState.Ready -> {
                val duration = ((screenState.session.endDate ?: Date()).time - screenState.session.startDate.time) / 1000
                Column {
                    Spacer(Modifier.size(16.dp))
                    Text(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        text = dateFormatter.formatShortDateTime(screenState.session.startDate),
                        style = MaterialTypography.body1,
                        color = MaterialColors.onBackground
                    )
                    Text(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        text = "Duration: ${String.format("%02d:%02d", duration / 60, duration % 60)}",
                        style = MaterialTypography.body1,
                        color = MaterialColors.onBackground
                    )
                    Spacer(Modifier.size(8.dp))
                    LazyColumn(
                        modifier = Modifier,
                        contentPadding = PaddingValues(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(screenState.session.exercises) { sessionExercise ->
                            CompositionLocalProvider(LocalContentColor provides MaterialColors.primary) {
                                Card(
                                    modifier = Modifier.heightIn(min = 72.dp),
                                    shape = CutCornerShape(topStart = 8.dp, topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 8.dp),
                                    backgroundColor = MaterialTheme.colors.surface
                                ) {
                                    Row(
                                        modifier = Modifier.padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = sessionExercise.routineExercise.exercise.name,
                                                style = MaterialTypography.body1,
                                                color = MaterialColors.onBackground
                                            )
                                            Text(
                                                text = "${sessionExercise.sets} sets x ${sessionExercise.reps} reps",
                                                style = MaterialTypography.body2,
                                                fontSize = 16.sp,
                                                color = MaterialColors.onBackground.copy(alpha = 0.85f)
                                            )
                                        }
                                        sessionExercise.weight?.let {
                                            Text(
                                                text = "${sessionExercise.weight} kg",
                                                style = MaterialTypography.body2,
                                                fontSize = 16.sp,
                                                color = MaterialColors.onBackground.copy(alpha = 0.85f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    },
        bottomBar = {
            LiftBottomNavigation(currentRoute) { item ->
                onNavigate(item.destination.route)
            }
        })
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
        screenState = ScreenState.Ready(DummyAppRepository.sessions.first()),
        currentRoute = NavDestination.SessionDetailNavDestination().route,
        dateFormatter = null as DateFormatter
    )
}