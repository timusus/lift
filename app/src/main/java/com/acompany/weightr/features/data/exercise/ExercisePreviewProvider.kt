package com.acompany.weightr.features.data.exercise

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.data.model.Exercise
import com.acompany.weightr.theme.ThemedPreviewProvider

class ExercisePreviewProvider : PreviewParameterProvider<Pair<Colors, Exercise>> {

    override val values: Sequence<Pair<Colors, Exercise>>
        get() = ThemedPreviewProvider().values.mapIndexed { index, colors ->
            colors to Exercise(
                name = "Squat",
                sessionId = 1,
                sets = index,
                reps = index,
                percentageOneRepMax = null
            )
        }

}