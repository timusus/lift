package com.acompany.weightr.features.routines.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.weightr.theme.ThemedPreviewProvider
import com.acompany.data.model.Routine

class RoutineListPreviewProvider : PreviewParameterProvider<Pair<Colors, List<Routine>>> {

    override val values: Sequence<Pair<Colors, List<Routine>>>
        get() = ThemedPreviewProvider().values.map { colors ->
            colors to DummyRoutines.routines
        }
}