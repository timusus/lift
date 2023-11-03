package com.acompany.lift.common

import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas

object CanvasHelper {

    fun DrawScope.drawText(text: String, point: PointF, paint: Paint) {
        drawIntoCanvas {
            val bounds = Rect()
            paint.getTextBounds(text, 0, text.length, bounds)
            it.nativeCanvas.drawText(text, point.x, point.y - bounds.exactCenterY(), paint)
        }
    }
}