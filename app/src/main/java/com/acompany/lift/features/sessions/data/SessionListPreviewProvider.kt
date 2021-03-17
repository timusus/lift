package com.acompany.lift.features.sessions.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.lift.data.model.Session
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.theme.ThemedPreviewProvider

class SessionListPreviewProvider :
    PreviewParameterProvider<Pair<Colors, List<Session>>> {

    override val values: Sequence<Pair<Colors, List<Session>>>
        get() = ThemedPreviewProvider().values.map { colors ->
            colors to DummyAppRepository.sessions
        }
}