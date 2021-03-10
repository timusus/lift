package com.acompany.weightr.features.exercises.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.weightr.theme.ThemedPreviewProvider
import com.acompany.data.model.Exercise
import com.acompany.data.model.RoutineExercise

class RoutineExerciseListPreviewProvider : PreviewParameterProvider<Pair<Colors, List<RoutineExercise>>> {

    override val values: Sequence<Pair<Colors, List<RoutineExercise>>>
        get() = ThemedPreviewProvider().values.map { colors ->
            colors to DummyRoutineExercises.routineExercises
        }
}