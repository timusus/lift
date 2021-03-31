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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.acompany.lift.common.DateFormatter
import com.acompany.lift.common.components.LoadingIndicator
import com.acompany.lift.data.model.Session
import com.acompany.lift.di.AppModule
import com.acompany.lift.features.main.components.LiftBottomNavigation
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.features.main.data.NavDestination
import com.acompany.lift.features.sessions.data.ScreenState
import com.acompany.lift.features.sessions.data.SessionScreenPreviewProvider
import com.acompany.lift.features.sessions.data.SessionScreenViewModel


@Composable
fun SessionListScreen(
    viewModel: SessionScreenViewModel,
    currentRoute: String?,
    modifier: Modifier = Modifier,
    onSessionClick: (Session) -> Unit,
    onNavigate: (String) -> Unit
) {
    val screenState: ScreenState by viewModel.screenState.collectAsState()
    SessionListScreen(
        screenState = screenState,
        currentRoute = currentRoute,
        dateFormatter = viewModel.dateFormatter,
        modifier = modifier,
        onSessionClick = onSessionClick,
        onNavigate = onNavigate
    )
}

@Composable
fun SessionListScreen(
    screenState: ScreenState,
    currentRoute: String?,
    dateFormatter: DateFormatter,
    modifier: Modifier = Modifier,
    onSessionClick: (Session) -> Unit,
    onNavigate: (String) -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Sessions") }
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
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
                                modifier = modifier,
                                sessions = screenState.sessions,
                                dateFormatter = dateFormatter,
                                onSessionClick = onSessionClick
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            LiftBottomNavigation(currentRoute) { destination ->
                onNavigate(destination.route)
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
            currentRoute = NavDestination.SessionNavDestination.route,
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
