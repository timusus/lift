package com.acompany.lift.features.sessions.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    modifier: Modifier = Modifier
) {
    val sessions by viewModel.getSessions().collectAsState(emptyList())
    SessionScreen(
        sessions = sessions,
        dateFormatter = viewModel.dateFormatter,
        modifier = modifier,
    )
}

@Composable
fun SessionScreen(
    sessions: List<Session>,
    dateFormatter: DateFormatter,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text(text = "Sessions") })
        },
        content = {
            if (sessions.isNotEmpty()) {
                SessionList(
                    sessions = sessions,
                    dateFormatter = dateFormatter
                )
            } else {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "No sessions recorded"
                )
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
            )
        )
    }
}
