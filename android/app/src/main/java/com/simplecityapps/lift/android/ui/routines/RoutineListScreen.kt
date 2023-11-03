package com.simplecityapps.lift.android.ui.routines

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
import com.simplecityapps.lift.common.model.Routine

@Composable
fun RoutineListScreen(
    viewModel: RoutineListViewModel = hiltViewModel(),
    onNavigateToDetailScreen: (Routine) -> Unit
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    RoutineListScreen(
        viewState = viewState,
        onNavigateToDetailScreen = onNavigateToDetailScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoutineListScreen(
    viewState: RoutineListViewState,
    onNavigateToDetailScreen: (Routine) -> Unit = {}
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = {
                    Text("Routines")
                }
            )
        },
        content = { padding ->
            when (viewState) {
                is RoutineListViewState.Loading -> {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .wrapContentHeight()
                            .padding(padding)
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is RoutineListViewState.Ready -> {
                    LazyColumn(
                        modifier = Modifier.padding(padding),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(viewState.routineSessionMap) { (routine, session) ->
                            RoutineListItem(
                                routine = routine,
                                session = session,
                                isCurrent = routine.id == viewState.leastRecentRoutine.id
                            ) {
                                onNavigateToDetailScreen(routine)
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun RoutineListScreenPreview() {
    AppTheme {
        RoutineListScreen(
            viewState = RoutineListViewState.Ready(
                FakeData.routines.mapIndexed { index, routine -> routine to (FakeData.sessions.getOrNull(index)) },
                FakeData.routines[1]
            )
        )
    }
}