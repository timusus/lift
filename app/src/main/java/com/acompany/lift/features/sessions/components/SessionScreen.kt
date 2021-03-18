package com.acompany.lift.features.sessions.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.acompany.lift.common.DateFormatter
import com.acompany.lift.data.model.Session
import com.acompany.lift.di.AppModule
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.features.sessions.data.SessionScreenPreviewProvider
import com.acompany.lift.features.sessions.data.SessionScreenViewModel

@Composable
fun SessionScreen(
    viewModel: SessionScreenViewModel,
    modifier: Modifier = Modifier,
    onSessionClick: (Session) -> Unit
) {
    val sessions by viewModel.getSessions().collectAsState(null)
    SessionScreen(
        sessions = sessions,
        dateFormatter = viewModel.dateFormatter,
        modifier = modifier,
        onSessionClick = onSessionClick
    )
}

@Composable
fun SessionScreen(
    sessions: List<Session>?,
    dateFormatter: DateFormatter,
    modifier: Modifier = Modifier,
    onSessionClick: (Session) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text(text = "Sessions") })
        },
        content = {
            if (sessions == null) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    CircularProgressIndicator(modifier.padding(top = 16.dp))
                }
            } else {
                if (sessions.isEmpty()) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "No sessions recorded"
                    )
                } else {
                    SessionList(
                        sessions = sessions,
                        dateFormatter = dateFormatter,
                        onSessionClick = onSessionClick
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun SessionScreenPreview(
    @PreviewParameter(SessionScreenPreviewProvider::class) preview: Colors
) {
    MaterialTheme(colors = preview) {
        SessionScreen(
            sessions = DummyAppRepository.sessions,
            dateFormatter = DateFormatter(
                context = LocalContext.current,
                mediumDateFormatter = AppModule.provideMediumDateFormat(),
                shortDateFormatter = AppModule.provideShortDateFormat(),
                mediumDateTimeFormatter = AppModule.provideMediumDateTimeFormat(),
                shortDateTimeFormatter = AppModule.provideShortDateTimeFormat()
            ),
            onSessionClick = {}
        )
    }
}
