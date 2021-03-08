package com.acompany.weightr.features.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.data.json.Exercise
import com.acompany.weightr.theme.ThemedPreviewProvider

class ExercisePreviewProvider : PreviewParameterProvider<Pair<Colors, Exercise>> {

    override val values: Sequence<Pair<Colors, Exercise>>
        get() = ThemedPreviewProvider().values.mapIndexed { index, colors ->
            colors to Exercise(
                name = "test name",
                day = index,
                sets = index,
                reps = index,
                percentageOneRepMax = null
            )
        }

}