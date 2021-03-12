package com.acompany.lift

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.*
import com.acompany.lift.data.AppRepository
import com.acompany.lift.features.NavDestination
import com.acompany.lift.features.exercises.components.ExerciseScreen
import com.acompany.lift.features.routines.components.RoutineScreen
import com.acompany.lift.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: AppRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val allRoutines = repository.getAllRoutines().collectAsState(initial = emptyList())
            val destinations = listOf(
                NavDestination.RoutineNavDestination,
                NavDestination.ExerciseNavDestination {
                    val routineId = navBackStackEntry?.arguments!!.getLong(NavDestination.ExerciseNavDestination.ARG_ROUTINE_ID)
                    allRoutines.value.firstOrNull { routine -> routine.id == routineId }?.name ?: "Lift"
                })
            AppTheme {
                Scaffold(
                    topBar = {
                        val currentScreen = destinations.firstOrNull { it.route == navBackStackEntry?.arguments?.getString(KEY_ROUTE) }
                        TopAppBar(title = { Text(text = currentScreen?.name?.invoke() ?: "Lift") })
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
                                ) { backStackEntry ->
                                    when (screen) {
                                        is NavDestination.RoutineNavDestination -> {
                                            RoutineScreen(paddingValues = paddingValues, navController = navController, repository = repository)
                                        }
                                        is NavDestination.ExerciseNavDestination -> {
                                            val routineId = backStackEntry.arguments!!.getLong(NavDestination.ExerciseNavDestination.ARG_ROUTINE_ID)
                                            ExerciseScreen(
                                                routineId = routineId,
                                                repository = repository
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    },
                )
            }
        }
    }
}