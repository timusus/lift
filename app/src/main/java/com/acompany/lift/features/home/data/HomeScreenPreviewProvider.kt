package com.acompany.lift.features.home.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.theme.ThemedPreviewProvider

class HomeScreenPreviewProvider :
    PreviewParameterProvider<Pair<Colors, HomeScreenViewModel>> {
    override val values: Sequence<Pair<Colors, HomeScreenViewModel>>
        get() = ThemedPreviewProvider().values.map { colors ->
            colors to HomeScreenViewModel(DummyAppRepository())
        }
}