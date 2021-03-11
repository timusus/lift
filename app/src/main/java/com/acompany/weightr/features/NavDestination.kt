package com.acompany.weightr.features

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class NavDestination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val name: String
) {

    class ExerciseNavDestination : NavDestination(
        route = "routines/{routineId}/exercises",
        arguments = listOf(navArgument(ARG_ROUTINE_ID) { type = NavType.LongType }),
        name = "Exercises"
    ) {
        companion object {
            const val ARG_ROUTINE_ID = "routineId"
        }
    }

    object RoutineNavDestination : NavDestination(
        route = "routines",
        name = "Routines"
    )
}