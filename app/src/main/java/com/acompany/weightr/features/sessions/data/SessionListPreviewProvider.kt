package com.acompany.weightr.features.sessions.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.data.model.Session
import com.acompany.weightr.theme.ThemedPreviewProvider

class SessionListPreviewProvider : PreviewParameterProvider<Pair<Colors, List<Session>>> {

    override val values: Sequence<Pair<Colors, List<Session>>>
        get() = ThemedPreviewProvider().values.map { colors ->
            colors to DummySessions.sessions
        }
}