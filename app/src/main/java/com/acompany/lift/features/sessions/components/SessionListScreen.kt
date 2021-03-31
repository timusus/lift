package com.acompany.lift.features.sessions.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
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
import com.acompany.lift.common.components.LoadingIndicator
import com.acompany.lift.data.model.Session
import com.acompany.lift.di.AppModule
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.features.sessions.data.ScreenState
import com.acompany.lift.features.sessions.data.SessionScreenPreviewProvider
import com.acompany.lift.features.sessions.data.SessionScreenViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import timber.log.Timber
import java.lang.reflect.Type


@Composable
fun SessionListScreen(
    viewModel: SessionScreenViewModel,
    modifier: Modifier = Modifier,
    onSessionClick: (Session) -> Unit
) {
    val screenState: ScreenState by viewModel.screenState.collectAsState()
    SessionListScreen(
        screenState = screenState,
        dateFormatter = viewModel.dateFormatter,
        modifier = modifier,
        onSessionClick = onSessionClick,
        onExportClick = { sessions ->
            val type: Type = Types.newParameterizedType(MutableList::class.java, Session::class.java)
            val adapter: JsonAdapter<List<Session>> = viewModel.moshi.adapter(type)
            val json = adapter.toJson(sessions)
            Timber.i("Json: $json")
        }
    )
}

@Composable
fun SessionListScreen(
    screenState: ScreenState,
    dateFormatter: DateFormatter,
    modifier: Modifier = Modifier,
    onSessionClick: (Session) -> Unit,
    onExportClick: (List<Session>) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Sessions") },
                actions = {
                    if (screenState is ScreenState.Ready) {
                        IconButton(onClick = { onExportClick(screenState.sessions) }) {
                            Icon(imageVector = Icons.Rounded.Download, contentDescription = "Export")
                        }
                    }
                })
        },
        content = {
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
                        SessionList(
                            sessions = screenState.sessions,
                            dateFormatter = dateFormatter,
                            onSessionClick = onSessionClick
                        )
                    }
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
        SessionListScreen(
            ScreenState.Ready(sessions = DummyAppRepository.sessions),
            dateFormatter = DateFormatter(
                context = LocalContext.current,
                mediumDateFormatter = AppModule.provideMediumDateFormat(),
                shortDateFormatter = AppModule.provideShortDateFormat(),
                mediumDateTimeFormatter = AppModule.provideMediumDateTimeFormat(),
                shortDateTimeFormatter = AppModule.provideShortDateTimeFormat()
            ),
            onSessionClick = {},
            onExportClick = {}
        )
    }
}
