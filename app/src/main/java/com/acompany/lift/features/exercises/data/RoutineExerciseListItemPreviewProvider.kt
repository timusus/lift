package com.acompany.lift.features.exercises.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.theme.ThemedPreviewProvider

class RoutineExerciseListItemPreviewProvider : PreviewParameterProvider<Pair<Colors, RoutineExercise>> {

    override val values: Sequence<Pair<Colors, RoutineExercise>>
        get() = ThemedPreviewProvider().values.map { colors ->
            colors to DummyRoutineExercises.routineExercises.first()
        }
}