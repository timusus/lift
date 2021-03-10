package com.acompany.weightr.features.exercises.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue

import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.acompany.data.AppRepository
import com.acompany.weightr.features.Screen
import timber.log.Timber

class ExerciseScreen(repository: AppRepository) : Screen(
    route = "routines/{routineId}/exercises",
    arguments = listOf(navArgument(ARG_ROUTINE_ID) { type = NavType.LongType }),
    name = "Exercises",
    content = { paddingValues, navController, navBackStackEntry ->
        val routineExercises by repository.getRoutineExercises(listOf(navBackStackEntry.arguments!!.getLong(ARG_ROUTINE_ID))).collectAsState(initial = emptyList())
        LazyExerciseList(
            routineExercises = routineExercises,
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            onExerciseClick = { exercise ->
                Timber.d("Click $exercise")
            },
            onExerciseLongClick = { exercise ->
                Timber.d("Long click $exercise")
            }
        )
    }
) {
    companion object {
        const val ARG_ROUTINE_ID = "routineId"
    }
}