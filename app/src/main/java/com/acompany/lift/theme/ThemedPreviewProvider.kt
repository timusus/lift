package com.acompany.lift.theme

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ThemedPreviewProvider : PreviewParameterProvider<Colors> {

    override val values: Sequence<Colors>
        get() = sequenceOf(lightAppColors, darkAppColors)

}