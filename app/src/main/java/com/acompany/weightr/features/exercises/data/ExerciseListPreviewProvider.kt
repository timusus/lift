package com.acompany.weightr.features.exercises.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.data.model.Exercise
import com.acompany.weightr.theme.ThemedPreviewProvider

class ExerciseListPreviewProvider : PreviewParameterProvider<Pair<Colors, List<Exercise>>> {

    override val values: Sequence<Pair<Colors, List<Exercise>>>
        get() = ThemedPreviewProvider().values.map { colors ->
            colors to DummyExercises.exercises
        }
}