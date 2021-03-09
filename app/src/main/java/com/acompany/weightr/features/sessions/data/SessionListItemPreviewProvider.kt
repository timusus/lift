package com.acompany.weightr.features.sessions.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.data.model.Session
import com.acompany.weightr.theme.ThemedPreviewProvider

class SessionListItemPreviewProvider : PreviewParameterProvider<Pair<Colors, Session>> {

    override val values: Sequence<Pair<Colors, Session>>
        get() = ThemedPreviewProvider().values.map { colors ->
            colors to DummySessions.sessions.first()
        }
}