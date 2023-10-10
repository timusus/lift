package com.simplecityapps.lift.android.ui.components.charts

import android.graphics.PointF
import androidx.compose.ui.geometry.Size

data class Geometry(
    val size: Size,
    val startOffset: Float = 0f,
    val endOffset: Float = 0f,
    val topOffset: Float = 0f,
    val bottomOffset: Float = 0f
)

fun PointF.translate(
    geometry: Geometry
): PointF {
    return PointF(
        geometry.startOffset + (x * (geometry.size.width - geometry.startOffset - geometry.endOffset)),
        (1 - y) * (geometry.size.height - geometry.topOffset - geometry.bottomOffset)
    )
}