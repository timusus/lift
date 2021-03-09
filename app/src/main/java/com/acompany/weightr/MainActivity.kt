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
import androidx.navigation.compose.*
import com.acompany.data.AppRepository
import com.acompany.weightr.features.Screen
import com.acompany.weightr.features.exerciseList
import com.acompany.weightr.features.sessionList
import com.acompany.weightr.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: AppRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val screens = listOf(Screen.Sessions, Screen.Exercises)
            val sessions by repository.getSessions().collectAsState(emptyList())
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
                            startDestination = Screen.Sessions.route
                        ) {
                            sessionList(
                                sessions = sessions,
                                modifier = Modifier.padding(paddingValues),
                                onSessionClick = { session ->
                                    navController.navigate("sessions/${session.id}/exercises")
                                }
                            )
                            exerciseList(
                                sessions = sessions,
                                modifier = Modifier.padding(paddingValues),
                                onExerciseClick = { exercise ->
                                    Timber.d("$exercise")
                                }
                            )
                        }
                    },
                )
            }
        }
    }

}