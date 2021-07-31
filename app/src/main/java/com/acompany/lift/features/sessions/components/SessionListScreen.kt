package com.acompany.lift.features.sessions.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.acompany.lift.common.DateFormatter
import com.acompany.lift.common.components.LoadingIndicator
import com.acompany.lift.data.model.Session
import com.acompany.lift.di.AppModule
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.features.sessions.data.ScreenState
import com.acompany.lift.features.sessions.data.SessionScreenPreviewProvider
import com.acompany.lift.features.sessions.data.SessionScreenViewModel

@Composable
fun SessionList(
    viewModel: SessionScreenViewModel,
    modifier: Modifier = Modifier,
    onSessionClick: (Session) -> Unit = {}
) {
    val screenState: ScreenState by viewModel.screenState.collectAsState()
    SessionList(
        screenState = screenState,
        dateFormatter = viewModel.dateFormatter,
        modifier = modifier,
        onSessionClick = onSessionClick
    )
}

@Composable
fun SessionList(
    screenState: ScreenState,
    dateFormatter: DateFormatter,
    modifier: Modifier = Modifier,
    onSessionClick: (Session) -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Sessions") },
            )
        }) {
        Box(modifier = modifier.semantics { contentDescription = "Session List Screen" }) {
            when (screenState) {
                is ScreenState.Loading -> {
                    LoadingIndicator(
                        showLoading = true,
                        contentAlignment = Alignment.TopCenter
                    )
                }
                is ScreenState.Ready -> {
                    if (screenState.sessions.isEmpty()) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "No sessions recorded"
                        )
                    } else {
                        LazySessionList(
                            modifier = modifier,
                            sessions = screenState.sessions,
                            dateFormatter = dateFormatter,
                            onSessionClick = onSessionClick
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SessionScreenPreview(
    @PreviewParameter(SessionScreenPreviewProvider::class) preview: Colors
) {
    MaterialTheme(colors = preview) {
        SessionList(
            ScreenState.Ready(sessions = DummyAppRepository.sessions),
            dateFormatter = DateFormatter(
                context = LocalContext.current,
                mediumDateFormatter = AppModule.provideMediumDateFormat(),
                shortDateFormatter = AppModule.provideShortDateFormat(),
                mediumDateTimeFormatter = AppModule.provideMediumDateTimeFormat(),
                shortDateTimeFormatter = AppModule.provideShortDateTimeFormat()
            )
        )
    }
}
