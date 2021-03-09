package com.acompany.weightr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.acompany.data.AppRepository
import com.acompany.weightr.features.components.exercise.LazyExerciseList
import com.acompany.weightr.features.components.session.LazySessionList
import com.acompany.weightr.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: AppRepository

    sealed class Screen(val route: String, val name: String) {
        object Sessions : Screen("sessions", "Sessions")
        object Exercises : Screen("sessions/{sessionId}/exercises", "Exercises")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val sessions by repository.getSessions().collectAsState(emptyList())
            val navController = rememberNavController()
            val screens = listOf(Screen.Sessions, Screen.Exercises)

            AppTheme {
                Scaffold(
                    topBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                        TopAppBar(title = { Text(text = screens.firstOrNull { screen -> screen.route == currentRoute }?.name ?: "weightr") })
                    },
                    content = { paddingValues ->
                        NavHost(navController, startDestination = Screen.Sessions.route) {
                            composable(Screen.Sessions.route) {
                                LazySessionList(
                                    sessions = sessions,
                                    modifier = Modifier.padding(paddingValues)
                                ) { session ->
                                    navController.navigate("sessions/${session.id}/exercises")
                                }
                            }
                            composable(
                                route = Screen.Exercises.route,
                                arguments = listOf(navArgument("sessionId") { type = NavType.IntType })
                            ) { backStackEntry ->
                                LazyExerciseList(
                                    exercises = sessions.filter { session -> session.id == backStackEntry.arguments!!.getInt("sessionId") }.flatMap { it.exercises },
                                    modifier = Modifier.padding(paddingValues)
                                ) { session ->
                                    Timber.d("$session")
                                }
                            }
                        }
                    },
                )
            }
        }
    }
}