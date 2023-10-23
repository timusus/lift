package com.simplecityapps.lift.android.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.android.ui.components.StaggeredVerticalGrid
import com.simplecityapps.lift.android.ui.components.charts.LineChart
import com.simplecityapps.lift.android.ui.components.charts.PointD
import com.simplecityapps.lift.android.ui.components.completionStatus
import com.simplecityapps.lift.model.Session
import com.simplecityapps.lift.model.totalVolume
import com.simplecityapps.lift.model.volume
import kotlin.math.pow


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToSignIn: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    val authState by viewModel.authState.collectAsStateWithLifecycle()

    if (authState is AsyncState.Success) {
        HomeScreen(
            viewState = viewState,
            onSettingsClicked = {
                onNavigateToSettings()
            }
        )
    } else {
        LaunchedEffect(authState) {
            onNavigateToSignIn()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewState: AsyncState<List<Session>>,
    onSettingsClicked: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    var showMenu by remember { mutableStateOf(false) }
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Settings") },
                            leadingIcon = {
                                Icon(imageVector = Icons.Filled.Refresh, null)
                            },
                            onClick = { onSettingsClicked() },
                        )
                    }
                }
            )
        }
    ) { padding ->
        when (viewState) {
            is AsyncState.Idle, is AsyncState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .wrapContentSize()
                )
            }

            is AsyncState.Success -> {
                StaggeredVerticalGrid(
                    modifier = Modifier
                        .padding(padding)
                        .padding(horizontal = 12.dp),
                    maxColumnWidth = 220.dp,
                ) {
                    val sessions = viewState.value.sortedBy { session -> session.startDate }

                    if (sessions.isNotEmpty()) {
                        VolumeCard(sessions)

                        CompletedSessionsCard(sessions)

                        LastSessionCard(sessions)

                        TotalVolumeCard(sessions)

                        BenchCard(sessions)

                        SquatCard(sessions)

                        DeadliftCard(sessions)
                    }
                }
            }

            is AsyncState.Failure -> {
                // Todo
            }
        }
    }
}

@Composable
private fun VolumeCard(sessions: List<Session>) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .clickable(onClick = {})
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            val dataPoints = sessions
                .map { session ->
                    PointD(session.startDate.epochSeconds.toDouble(), session.volume().toDouble())
                }

            val differences = dataPoints.zipWithNext().map { it.second.x - it.first.x }
            val cumulativeDistances = differences.scan(0.0) { acc, diff -> acc + diff }
            val transformedDistances = cumulativeDistances.map { it.pow(1.0 / 3.0) }
            val maxTransformedDistance = transformedDistances.last()
            val normalizedTransformedXValues = transformedDistances.map { it / maxTransformedDistance * (dataPoints.size - 1) }
            val normalizedDataPoints = dataPoints.mapIndexed { index, point ->
                PointD(normalizedTransformedXValues[index], point.y)
            }
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                dataPoints = normalizedDataPoints,
                yMin = normalizedDataPoints.minOfOrNull { it.y }?.minus(10)?.coerceAtLeast(0.0) ?: 0.0,
                yMax = normalizedDataPoints.maxOfOrNull { it.y }?.plus(10) ?: 0.0
            )
            Text(
                text = "Volume",
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun DeadliftCard(sessions: List<Session>) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(4.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            val dataPoints = sessions
                .mapNotNull { session ->
                    session.sessionExercises.firstOrNull { sessionExercise -> sessionExercise.routineExercise.exercise.name == "Deadlift" }?.weight?.let { it ->
                        PointD(session.startDate.epochSeconds.toDouble(), it.toDouble())
                    }
                }

            val differences = dataPoints.zipWithNext().map { it.second.x - it.first.x }
            val cumulativeDistances = differences.scan(0.0) { acc, diff -> acc + diff }
            val transformedDistances = cumulativeDistances.map { it.pow(1.0 / 3.0) }
            val maxTransformedDistance = transformedDistances.last()
            val normalizedTransformedXValues = transformedDistances.map { it / maxTransformedDistance * (dataPoints.size - 1) }
            val normalizedDataPoints = dataPoints.mapIndexed { index, point ->
                PointD(normalizedTransformedXValues[index], point.y)
            }
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                dataPoints = normalizedDataPoints,
                yMin = normalizedDataPoints.minOfOrNull { it.y }?.minus(10)?.coerceAtLeast(0.0) ?: 0.0,
                yMax = normalizedDataPoints.maxOfOrNull { it.y }?.plus(10) ?: 0.0
            )
            Text(
                text = "Deadlift",
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun SquatCard(sessions: List<Session>) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            val dataPoints = sessions
                .mapNotNull { session ->
                    session.sessionExercises.firstOrNull { sessionExercise -> sessionExercise.routineExercise.exercise.name == "Squat" }?.weight?.let { it ->
                        PointD(session.startDate.epochSeconds.toDouble(), it.toDouble())
                    }
                }

            val differences = dataPoints.zipWithNext().map { it.second.x - it.first.x }
            val cumulativeDistances = differences.scan(0.0) { acc, diff -> acc + diff }
            val transformedDistances = cumulativeDistances.map { it.pow(1.0 / 3.0) }
            val maxTransformedDistance = transformedDistances.last()
            val normalizedTransformedXValues = transformedDistances.map { it / maxTransformedDistance * (dataPoints.size - 1) }
            val normalizedDataPoints = dataPoints.mapIndexed { index, point ->
                PointD(normalizedTransformedXValues[index], point.y)
            }
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                dataPoints = normalizedDataPoints,
                yMin = normalizedDataPoints.minOfOrNull { it.y }?.minus(10)?.coerceAtLeast(0.0) ?: 0.0,
                yMax = normalizedDataPoints.maxOfOrNull { it.y }?.plus(10) ?: 0.0
            )
            Text(
                text = "Squat",
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun BenchCard(sessions: List<Session>) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {


            val dataPoints = sessions
                .mapNotNull { session ->
                    session.sessionExercises.firstOrNull { sessionExercise -> sessionExercise.routineExercise.exercise.name == "Bench Press" }?.weight?.let { it ->
                        PointD(session.startDate.epochSeconds.toDouble(), it.toDouble())
                    }
                }

            val differences = dataPoints.zipWithNext().map { it.second.x - it.first.x }
            val cumulativeDistances = differences.scan(0.0) { acc, diff -> acc + diff }
            val transformedDistances = cumulativeDistances.map { it.pow(1.0 / 3.0) }
            val maxTransformedDistance = transformedDistances.last()
            val normalizedTransformedXValues = transformedDistances.map { it / maxTransformedDistance * (dataPoints.size - 1) }
            val normalizedDataPoints = dataPoints.mapIndexed { index, point ->
                PointD(normalizedTransformedXValues[index], point.y)
            }

            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                dataPoints = normalizedDataPoints,
                yMin = normalizedDataPoints.minOfOrNull { it.y }?.minus(10)?.coerceAtLeast(0.0) ?: 0.0,
                yMax = normalizedDataPoints.maxOfOrNull { it.y }?.plus(10) ?: 0.0
            )
            Text(
                text = "Bench",
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun TotalVolumeCard(sessions: List<Session>) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = String.format("%,.0f kg", sessions.totalVolume()),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Total Volume",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
private fun LastSessionCard(sessions: List<Session>) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = sessions.lastOrNull().completionStatus(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Last Session",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
private fun CompletedSessionsCard(sessions: List<Session>) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${sessions.size}",
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Sessions completed",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
            )
        }
    }
}
