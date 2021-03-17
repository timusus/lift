package com.acompany.lift.features.sessions.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.lift.theme.ThemedPreviewProvider

class SessionScreenPreviewProvider : PreviewParameterProvider<Colors> {

    override val values: Sequence<Colors>
        get() = ThemedPreviewProvider().values
}