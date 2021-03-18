package com.acompany.lift.features.exercises.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.lift.theme.ThemedPreviewProvider

class ExerciseScreenPreviewProvider : PreviewParameterProvider<Colors> {
    override val values: Sequence<Colors>
        get() = ThemedPreviewProvider().values
}