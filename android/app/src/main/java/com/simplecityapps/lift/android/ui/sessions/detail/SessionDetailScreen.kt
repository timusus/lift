package com.simplecityapps.lift.android.ui.sessions.detail

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.android.ui.sessions.detail.edit.SessionExerciseSheetContent
import com.simplecityapps.lift.common.model.Routine
import com.simplecityapps.lift.common.model.Session
import com.simplecityapps.lift.common.model.SessionExercise
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import java.time.Duration
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.time.toJavaDuration

@Composable
fun SessionDetailScreen(
    viewModel: SessionDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val viewState by viewModel.sessionState.collectAsStateWithLifecycle()
    val dialogSessionState by viewModel.useExistingSessionDialogState.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()

    SessionDetailScreen(
        viewState = viewState,
        dialogSessionState = dialogSessionState,
        onUseExistingSession = {
            viewModel.useExistingSession()
        },
        onCreateNewSession = { routine ->
            viewModel.createNewSession(routine)
        },
        onNextClicked = { session ->
            if (session.isComplete) {
                onNavigateBack()
            } else {
                viewModel.updateProgress(session)
            }
        },
        onWeightChanged = { sessionExercises, weight ->
            viewModel.updateSessionExerciseWeight(sessionExercises, weight)
        },
        onDeleteSession = { session ->
            coroutineScope.launch {
                viewModel.deleteSession(session)
                onNavigateBack()
            }
        },
        onBackClicked = {
            onNavigateBack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SessionDetailScreen(
    viewState: AsyncState<SessionDetailViewState>,
    dialogSessionState: DialogSessionState,
    onUseExistingSession: () -> Unit = {},
    onCreateNewSession: (routine: Routine) -> Unit = {},
    onNextClicked: (session: Session) -> Unit = {},
    onWeightChanged: (sessionExercise: SessionExercise, weight: Float?) -> Unit,
    onDeleteSession: (session: Session) -> Unit = {},
    onBackClicked: () -> Unit = {}
) {
    var sessionExerciseToEdit by remember {
        mutableStateOf<SessionExercise?>(null)
    }
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(sessionExerciseToEdit) {
        if (sessionExerciseToEdit == null) {
            sheetState.hide()
            showBottomSheet = false
        } else {
            showBottomSheet = true
            sheetState.show()
        }
    }

    when (viewState) {
        is AsyncState.Idle, is AsyncState.Loading -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            ) {
                CircularProgressIndicator()
            }
        }

        is AsyncState.Success -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(viewState.value.session.routine.name)
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = { onBackClicked() }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        },
                        actions = {
                            Box(
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                val time by getTimerFlow(viewState.value.session).collectAsState(
                                    initial = null
                                )
                                time?.let {
                                    Text(text = DateUtils.formatElapsedTime(it.seconds))
                                }
                            }
                            IconButton(onClick = { onDeleteSession(viewState.value.session) }) {
                                Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                            }
                        }
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(viewState.value.session.sessionExercises) { sessionExercise ->
                            val restDuration by viewState.value.restDuration.collectAsStateWithLifecycle(initialValue = null)
                            SessionExerciseRow(
                                sessionExercise = sessionExercise,
                                isCurrentExercise = viewState.value.session.currentExercise?.id == sessionExercise.id,
                                isResting = viewState.value.restStartTime != null,
                                restDuration = restDuration,
                                onClick = {
                                    sessionExerciseToEdit = sessionExercise
                                }
                            )
                        }
                    }
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        onClick = {
                            onNextClicked(viewState.value.session)
                        }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = buttonText(
                                    session = viewState.value.session
                                )
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Outlined.ChevronRight,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }

        is AsyncState.Failure -> {
            Text(text = "An error ruined your life")
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                sessionExerciseToEdit = null
            }
        ) {
            sessionExerciseToEdit?.let { sessionExercise ->
                if (viewState is AsyncState.Success) {
                    SessionExerciseSheetContent(
                        sessionExercise = sessionExercise,
                        onOneRepMaxChanged = {
                            // Todo
                        },
                        onWeightChanged = { weight ->
                            onWeightChanged(sessionExercise, weight)
                        },
                        onPercentOneRepMaxChanged = {
                            // Todo
                        }
                    ) {
                        sessionExerciseToEdit = null
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .heightIn(min = 200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

    if (dialogSessionState is DialogSessionState.Show) {
        ExistingSessionDialog(
            dialogSessionState = dialogSessionState,
            onUseExistingSession = onUseExistingSession,
            onCreateNewSession = onCreateNewSession
        )
    }
}

@Composable
private fun ExistingSessionDialog(
    dialogSessionState: DialogSessionState.Show,
    onUseExistingSession: () -> Unit,
    onCreateNewSession: (routine: Routine) -> Unit
) {

    val dateTimeFormatter: DateTimeFormatter = remember {
        DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT)
            .withZone(ZoneId.systemDefault())
    }

    AlertDialog(
        title = {
            Text(text = "Continue existing session?")
        },
        text = {
            Text("You have an incomplete session from ${dateTimeFormatter.format(dialogSessionState.session.startDate.toJavaInstant())}")
        },
        tonalElevation = 8.dp,
        confirmButton = {
            TextButton(
                onClick = {
                    onUseExistingSession()
                }
            ) {
                Text("Continue")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onCreateNewSession(dialogSessionState.session.routine)
                }
            ) {
                Text("Create new")
            }
        },
        onDismissRequest = {
            onUseExistingSession()
        }
    )
}

private fun getTimerFlow(session: Session): Flow<Duration> {
    return (0..Int.MAX_VALUE)
        .asFlow()
        .map {
            Clock.System.now().minus(session.startDate).toJavaDuration()
        }
        .onEach { delay(1000) }
        .onStart {
            emit(Clock.System.now().minus(session.startDate).toJavaDuration())
        }
}

private fun buttonText(session: Session): String {
    var text = "Next"
    if (session.isComplete) {
        text = "Complete"
    }
    return text
}

