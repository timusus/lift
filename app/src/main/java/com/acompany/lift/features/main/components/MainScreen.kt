package com.acompany.lift.features.main.components

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.acompany.lift.data.model.Routine
import com.acompany.lift.features.exercises.components.ExerciseMenuItem
import com.acompany.lift.features.exercises.components.ExerciseScreen
import com.acompany.lift.features.exercises.components.ExerciseScreenViewModel
import com.acompany.lift.features.navigation.NavDestination
import com.acompany.lift.features.routines.components.RoutineScreen
import com.acompany.lift.features.routines.components.RoutineScreenViewModel
import com.acompany.lift.theme.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    val selectedRoutine: MutableStateFlow<Routine?> = MutableStateFlow(null as Routine?)
    val startDate: MutableStateFlow<Date?> = MutableStateFlow(null)
    val elapsedTimeMillis: MutableStateFlow<Long?> = MutableStateFlow(null as Long?)

    @OptIn(ObsoleteCoroutinesApi::class)
    private val ticker = ticker(1000)

    init {
        viewModelScope.launch {
            for (event in ticker) {
                startDate.value?.let { startDate ->
                    elapsedTimeMillis.emit(Date().time - startDate.time)
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel(),
    routineScreenViewModel: RoutineScreenViewModel = viewModel(),
    exerciseScreenViewModel: ExerciseScreenViewModel = viewModel(),
) {
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val selectedRoutine = mainViewModel.selectedRoutine.collectAsState()

    val destinations = listOf(
        NavDestination.RoutineNavDestination,
        NavDestination.ExerciseNavDestination {
            selectedRoutine.value?.name ?: "Lift"
        }
    )

    AppTheme {
        Scaffold(
            topBar = {
                val currentNavigationDestination = destinations.firstOrNull { it.route == navBackStackEntry?.arguments?.getString(KEY_ROUTE) }
                TopAppBar(
                    title = { Text(text = currentNavigationDestination?.name?.invoke() ?: "Lift") },
                    actions = {
                        if (currentNavigationDestination is NavDestination.ExerciseNavDestination) {
                            val elapsedTime = mainViewModel.elapsedTimeMillis.collectAsState()
                            ExerciseMenuItem(
                                elapsedTime = elapsedTime.value
                            ) {
                                scope.launch {
                                    mainViewModel.startDate.emit(Date())
                                }
                            }
                        }
                    }
                )
            },
            content = { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = destinations.first().route
                ) {
                    destinations.forEach { screen ->
                        composable(
                            route = screen.route,
                            arguments = screen.arguments
                        ) {
                            when (screen) {
                                is NavDestination.RoutineNavDestination -> {
                                    RoutineScreen(
                                        viewModel = routineScreenViewModel,
                                        paddingValues = paddingValues,
                                        onRoutineSelected = { routine ->
                                            navController.navigate("routines/${routine.id}/exercises")
                                            scope.launch {
                                                mainViewModel.selectedRoutine.emit(routine)
                                            }
                                        }
                                    )
                                }
                                is NavDestination.ExerciseNavDestination -> {
                                    selectedRoutine.value?.let { routine ->
                                        ExerciseScreen(
                                            viewModel = exerciseScreenViewModel,
                                            routine = routine
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
        )
    }
}