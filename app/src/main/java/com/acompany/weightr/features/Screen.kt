package com.acompany.weightr.features

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.acompany.data.model.Exercise
import com.acompany.data.model.Session
import com.acompany.weightr.features.exercises.components.LazyExerciseList
import com.acompany.weightr.features.sessions.components.LazySessionList

sealed class Screen(val route: String, val name: String) {
    object Sessions : Screen("sessions", "Sessions")
    object Exercises : Screen("sessions/{sessionId}/exercises", "Exercises")
}

fun NavGraphBuilder.sessionList(
    sessions: List<Session>,
    modifier: Modifier = Modifier,
    onSessionClick: (Session) -> Unit
) {
    composable(Screen.Sessions.route) {
        LazySessionList(
            sessions = sessions,
            modifier = modifier,
            onSessionClick = onSessionClick
        )
    }
}

fun NavGraphBuilder.exerciseList(
    sessions: List<Session>,
    modifier: Modifier = Modifier,
    onExerciseClick: (Exercise) -> Unit
) {
    composable(
        route = Screen.Exercises.route,
        arguments = listOf(navArgument("sessionId") { type = NavType.IntType })
    ) { backStackEntry ->
        val session = sessions.filter { session ->
            session.id == backStackEntry.arguments!!.getInt("sessionId")
        }
        val exercises = session.flatMap { it.exercises }
        LazyExerciseList(
            exercises = exercises,
            modifier = modifier,
            onExerciseClick = onExerciseClick
        )
    }
}