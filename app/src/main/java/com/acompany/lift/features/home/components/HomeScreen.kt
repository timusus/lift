package com.acompany.lift.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acompany.lift.common.SessionHelper.totalVolume
import com.acompany.lift.common.SessionHelper.volume
import com.acompany.lift.common.components.charts.LineChart
import com.acompany.lift.common.components.charts.PointD
import com.acompany.lift.features.home.data.HomeScreenPreviewProvider
import com.acompany.lift.features.home.data.HomeScreenViewModel
import com.acompany.lift.features.home.data.ScreenState
import com.acompany.lift.features.main.components.LiftBottomNavigation
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.features.main.data.NavDestination
import org.ocpsoft.prettytime.PrettyTime

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier,
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {

    val screenState: ScreenState by viewModel.screenState.collectAsState()

    HomeScreen(
        modifier = modifier,
        screenState = screenState,
        currentRoute = currentRoute,
        onNavigate = onNavigate
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    screenState: ScreenState,
    currentRoute: String?,
    onNavigate: (String) -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Home") },
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            ) {
                when (screenState) {
                    is ScreenState.Loading -> {

                    }
                    is ScreenState.Ready -> {
                        if (screenState.sessions.isNotEmpty()) {

                            Column(
                                modifier = modifier
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Text(modifier = modifier, text = "${screenState.sessions.size} sessions completed")
                                Spacer(modifier = modifier.size(8.dp))
                                Text(modifier = modifier, text = "First session ${PrettyTime().format(screenState.sessions.first().endDate)}")
                                Spacer(modifier = modifier.size(8.dp))
                                Text(modifier = modifier, text = "Last session ${PrettyTime().format(screenState.sessions.last().endDate)}")
                                Spacer(modifier = modifier.size(8.dp))

                                Text(modifier = modifier, text = String.format("Total volume: %,.0f kg", screenState.sessions.totalVolume()))
                                Spacer(modifier = modifier.size(8.dp))

                                Row {
                                    Card(
                                        shape = CutCornerShape(topStart = 8.dp, topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp),
                                        backgroundColor = MaterialTheme.colors.surface,
                                        modifier = modifier
                                            .clickable(onClick = {})
                                            .weight(1f)
                                    ) {
                                        Column(modifier = modifier.padding(16.dp)) {
                                            LineChart(
                                                modifier = modifier
                                                    .fillMaxWidth()
                                                    .height(120.dp),
                                                dataPoints = screenState.sessions.map { PointD(it.startDate.time.toDouble(), it.volume().toDouble()) },
                                                yMin = screenState.sessions.map { it.volume() }.minOrNull()?.toDouble()?.minus(500.0)?.coerceAtLeast(0.0) ?: 0.0
                                            )
                                            Text(modifier = modifier.align(Alignment.CenterHorizontally), text = "Volume", fontSize = 12.sp)
                                        }
                                    }

                                    Spacer(modifier = modifier.size(8.dp))

                                    Card(
                                        shape = CutCornerShape(topStart = 8.dp, topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp),
                                        backgroundColor = MaterialTheme.colors.surface,
                                        modifier = modifier
                                            .clickable(onClick = {})
                                            .weight(1f)
                                    ) {
                                        Column(modifier = modifier.padding(16.dp)) {
                                            LineChart(
                                                modifier = modifier
                                                    .fillMaxWidth()
                                                    .height(120.dp),
                                                dataPoints = screenState.sessions
                                                    .mapNotNull { session ->
                                                        session.exercises.firstOrNull { it.routineExercise.exercise.id == 5L }?.weight?.let {
                                                            Pair(session.startDate, it)
                                                        }
                                                    }
                                                    .map { PointD(it.first.time.toDouble(), it.second.toDouble()) }
                                            )
                                            Text(modifier = modifier.align(Alignment.CenterHorizontally), text = "Bench", fontSize = 12.sp)
                                        }
                                    }
                                }

                                Spacer(modifier = modifier.size(8.dp))

                                Row {
                                    Card(
                                        shape = CutCornerShape(topStart = 8.dp, topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp),
                                        backgroundColor = MaterialTheme.colors.surface,
                                        modifier = modifier
                                            .clickable(onClick = {})
                                            .weight(1f)
                                    ) {
                                        Column(modifier = modifier.padding(16.dp)) {
                                            LineChart(
                                                modifier = modifier
                                                    .fillMaxWidth()
                                                    .height(120.dp),
                                                dataPoints = screenState.sessions
                                                    .mapNotNull { session ->
                                                        session.exercises.firstOrNull { it.routineExercise.exercise.id == 1L }?.weight?.let {
                                                            Pair(session.startDate, it)
                                                        }
                                                    }
                                                    .map { PointD(it.first.time.toDouble(), it.second.toDouble()) }
                                            )
                                            Text(modifier = modifier.align(Alignment.CenterHorizontally), text = "Squat", fontSize = 12.sp)
                                        }
                                    }

                                    Spacer(modifier = modifier.size(8.dp))

                                    Card(
                                        shape = CutCornerShape(topStart = 8.dp, topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp),
                                        backgroundColor = MaterialTheme.colors.surface,
                                        modifier = modifier
                                            .clickable(onClick = {})
                                            .weight(1f)

                                    ) {
                                        Column(modifier = modifier.padding(16.dp)) {
                                            LineChart(
                                                modifier = modifier
                                                    .fillMaxWidth()
                                                    .height(120.dp),
                                                dataPoints = screenState.sessions
                                                    .mapNotNull { session ->
                                                        session.exercises.firstOrNull { it.routineExercise.exercise.id == 2L }?.weight?.let {
                                                            Pair(session.startDate, it)
                                                        }
                                                    }
                                                    .map { PointD(it.first.time.toDouble(), it.second.toDouble()) }

                                            )
                                            Text(modifier = modifier.align(Alignment.CenterHorizontally), text = "Deadlift", fontSize = 12.sp)
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
                onNavigate(item.route)
            }
        }
    )
}

@Preview
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(HomeScreenPreviewProvider::class) preview: Pair<Colors, HomeScreenViewModel>
) {
    MaterialTheme(colors = preview.first) {
        HomeScreen(
            screenState = ScreenState.Ready(DummyAppRepository.sessions),
            currentRoute = NavDestination.HomeNavDestination.route
        )
    }
}