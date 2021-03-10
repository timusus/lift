package com.acompany.weightr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.*
import com.acompany.data.AppRepository
import com.acompany.weightr.features.exercises.components.ExerciseScreen
import com.acompany.weightr.features.routines.components.RoutineScreen
import com.acompany.weightr.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: AppRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repository.getRoutineExercises(listOf(1)).collect {
                Timber.i("Found ${it.size} routine exercises")
            }
        }

        setContent {
            val navController = rememberNavController()
            val screens = listOf(
                RoutineScreen(repository),
                ExerciseScreen(repository),
            )
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
            AppTheme {
                Scaffold(
                    topBar = {
                        val currentScreen = screens.firstOrNull { it.route == currentRoute }
                        TopAppBar(title = { Text(text = currentScreen?.name ?: "weightr") })
                    },
                    content = { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = screens.first().route
                        ) {
                            screens.forEach { screen ->
                                composable(
                                    route = screen.route,
                                    arguments = screen.arguments
                                ) { backStackEntry ->
                                    screen.content(paddingValues, navController, backStackEntry)
                                }
                            }
                        }
                    },
                )
            }
        }
    }
}