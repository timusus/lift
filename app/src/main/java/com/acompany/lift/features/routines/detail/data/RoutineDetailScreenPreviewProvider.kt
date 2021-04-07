package com.acompany.lift.features.routines.detail.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.lift.theme.ThemedPreviewProvider

class RoutineDetailScreenPreviewProvider : PreviewParameterProvider<Colors> {
    override val values: Sequence<Colors>
        get() = ThemedPreviewProvider().values
}