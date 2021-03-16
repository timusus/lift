package com.acompany.lift.features.routines.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.lift.data.model.Routine
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.theme.ThemedPreviewProvider

class RoutineListPreviewProvider : PreviewParameterProvider<Pair<Colors, List<Routine>>> {

    override val values: Sequence<Pair<Colors, List<Routine>>>
        get() = ThemedPreviewProvider().values.map { colors ->
            colors to DummyAppRepository.routines
        }
}