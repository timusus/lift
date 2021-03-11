package com.acompany.weightr.features.exercises.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.data.model.RoutineExercise
import com.acompany.weightr.theme.ThemedPreviewProvider

class RoutineExerciseListItemPreviewProvider : PreviewParameterProvider<Pair<Colors, RoutineExercise>> {

    override val values: Sequence<Pair<Colors, RoutineExercise>>
        get() = ThemedPreviewProvider().values.map { colors ->
            colors to DummyRoutineExercises.routineExercises.first()
        }
}