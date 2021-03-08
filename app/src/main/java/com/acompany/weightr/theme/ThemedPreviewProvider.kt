package com.acompany.weightr.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ThemedPreviewProvider : PreviewParameterProvider<Colors> {

    override val values: Sequence<Colors>
        get() = sequenceOf(lightColors(), darkColors())

}