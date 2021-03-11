package com.acompany.weightr.features.routines.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.data.model.Routine
import com.acompany.weightr.theme.ThemedPreviewProvider

class RoutineListItemPreviewProvider : PreviewParameterProvider<Pair<Colors, Routine>> {

    override val values: Sequence<Pair<Colors, Routine>>
        get() = ThemedPreviewProvider().values.map { colors ->
            colors to DummyRoutines.routines.first()
        }
}