package com.simplecityapps.lift.android.ui.components.charts

import android.graphics.PointF
import androidx.compose.ui.geometry.Offset

data class PointD(val x: Double, val y: Double)

fun PointF.toOffset(): Offset {
    return Offset(
        x = x,
        y = y
    )
}