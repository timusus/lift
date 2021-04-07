package com.acompany.lift.common.components.charts

import android.graphics.PointF
import androidx.compose.ui.graphics.Path

data class BezierPoints(val startPoint: PointF, val endpoint: PointF, val control1: PointF, val control2: PointF)

fun List<BezierPoints>.toPath(start: PointF): Path {
    return Path().apply {
        moveTo(start.x, start.y)
        forEach { bezierPoint ->
            cubicTo(
                bezierPoint.control1.x,
                bezierPoint.control1.y,
                bezierPoint.control2.x,
                bezierPoint.control2.y,
                bezierPoint.endpoint.x,
                bezierPoint.endpoint.y
            )
        }
    }
}

fun List<PointF>.toBezierPoints(): List<BezierPoints> {
    return mapIndexedNotNull { index, endPoint ->
        if (index == 0) {
            null
        } else {
            val startPoint = this[index - 1]
            BezierPoints(
                startPoint = this[index - 1],
                endpoint = endPoint,
                control1 = PointF((startPoint.x + endPoint.x) / 2, startPoint.y),
                control2 = PointF((startPoint.x + endPoint.x) / 2, endPoint.y)
            )
        }
    }
}