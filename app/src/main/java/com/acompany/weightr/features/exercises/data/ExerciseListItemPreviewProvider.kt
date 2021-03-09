package com.acompany.weightr.features.exercises.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.data.model.Exercise
import com.acompany.weightr.theme.ThemedPreviewProvider

class ExerciseListItemPreviewProvider : PreviewParameterProvider<Pair<Colors, Exercise>> {

    override val values: Sequence<Pair<Colors, Exercise>>
        get() = ThemedPreviewProvider().values.map { colors ->
            colors to DummyExercises.exercises.first()
        }
}