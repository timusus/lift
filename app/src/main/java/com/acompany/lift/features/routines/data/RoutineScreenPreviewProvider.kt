package com.acompany.lift.features.routines.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.features.routines.components.RoutineScreenViewModel
import com.acompany.lift.theme.ThemedPreviewProvider

class RoutineScreenPreviewProvider : PreviewParameterProvider<Pair<Colors, RoutineScreenViewModel>> {
    override val values: Sequence<Pair<Colors, RoutineScreenViewModel>>
        get() = ThemedPreviewProvider().values.map { colors ->
            colors to RoutineScreenViewModel(DummyAppRepository())
        }
}