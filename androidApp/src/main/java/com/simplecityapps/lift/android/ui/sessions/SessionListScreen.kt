package com.simplecityapps.lift.android.ui.sessions

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simplecityapps.lift.android.ui.fake.FakeData
import com.simplecityapps.lift.android.ui.theme.AppTheme

@Composable
fun SessionListScreen(
    viewModel: SessionListViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    SessionListScreen(viewState = viewState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionListScreen(
    viewState: SessionListViewState
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = { Text("Sessions") })
        }
    ) { padding ->
        when (viewState) {
            is SessionListViewState.Loading -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .wrapContentHeight()
                        .padding(padding)
                ) {
                    CircularProgressIndicator()
                }
            }

            is SessionListViewState.Ready -> {
                LazyColumn(
                    modifier = Modifier.padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    viewState.groupedSessions.forEach { (group, sessions) ->
                        item {
                            Text(
                                text = when (group) {
                                    SessionGroup.LastWeek -> "Recent"
                                    SessionGroup.LastMonth -> "Last Month"
                                    SessionGroup.LastYear -> "Last Year"
                                    SessionGroup.Older -> "Older"
                                },
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        items(sessions) { session ->
                            SessionListItem(session = session)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SessionListScreenPreview() {
    AppTheme {


        SessionListScreen(
            viewState = SessionListViewState.Ready(
                groupedSessions = mapOf(
                    SessionGroup.LastWeek to listOf(
                        FakeData.sessions[0], FakeData.sessions[1]
                    ),
                    SessionGroup.LastMonth to listOf(
                        FakeData.sessions[2], FakeData.sessions[1]
                    )
                )
            )
        )
    }
}