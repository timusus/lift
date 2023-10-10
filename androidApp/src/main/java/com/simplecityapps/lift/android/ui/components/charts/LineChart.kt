package com.simplecityapps.lift.android.ui.components.charts

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acompany.lift.common.CanvasHelper.drawText
import com.simplecityapps.lift.android.ui.theme.AppTheme

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    dataPoints: List<PointD>,
    xMin: Double = dataPoints.firstOrNull()?.x ?: 0.0,
    xMax: Double = dataPoints.lastOrNull()?.x ?: 1.0,
    yMin: Double = 0.0,
    yMax: Double = dataPoints.maxByOrNull { it.y }?.y ?: 1.0
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val lineColor = MaterialTheme.colorScheme.onSurface
    Canvas(modifier = modifier) {
        if (dataPoints.isNotEmpty()) {

            val geometry = Geometry(size = size)
            val offsetGeometry = geometry.copy(startOffset = 24.dp.toPx(), bottomOffset = 16.dp.toPx())

            // X-Axis
            drawLine(
                color = lineColor,
                start = PointF(0f, 0f).translate(offsetGeometry).toOffset(),
                end = PointF(1f, 0f).translate(offsetGeometry).toOffset(),
                strokeWidth = 0.5.dp.toPx()
            )

            // Y-Axis
            drawLine(
                color = lineColor,
                start = PointF(0f, 0f).translate(offsetGeometry).toOffset(),
                end = PointF(0f, 1f).translate(offsetGeometry).toOffset(),
                strokeWidth = 0.5.dp.toPx()
            )

            // Points/Lines
            val translatedDataPoints = dataPoints.map { point ->
                val xRange = (xMax - xMin).coerceAtLeast(1.0)
                val yRange = (yMax - yMin).coerceAtLeast(1.0)
                PointF(
                    ((point.x - xMin) / xRange).toFloat(),
                    ((point.y - yMin) / yRange).toFloat()
                ).translate(offsetGeometry)
            }
            translatedDataPoints.forEach { dataPoint ->
                drawCircle(color = primaryColor, center = Offset(dataPoint.x, dataPoint.y), radius = 2.dp.toPx())
            }
            drawPath(path = translatedDataPoints.toBezierPoints().toPath(translatedDataPoints.first()), color = primaryColor, style = Stroke(1.dp.toPx()))

            // Guidelines
            val numHorizontalGuidelines = 5
            drawText(String.format("%.0f", yMin), PointF(0f, 0f).translate(offsetGeometry.copy(startOffset = 20.dp.toPx())), Paint().apply {
                textSize = 8.sp.toPx()
                color = lineColor.toArgb()
                textAlign = Paint.Align.RIGHT
            })
            for (i in 0 until numHorizontalGuidelines) {
                val yPer = (i / numHorizontalGuidelines.toFloat()) + (1f / (numHorizontalGuidelines))
                val yVal = yMin + yPer * (yMax - yMin)
                drawText(
                    text = String.format("%.0f", yVal),
                    point = PointF(0f, yPer).translate(offsetGeometry.copy(startOffset = 20.dp.toPx())),
                    paint = Paint().apply {
                        textSize = 8.sp.toPx()
                        color = lineColor.toArgb()
                        textAlign = Paint.Align.RIGHT
                    }
                )
                drawLine(
                    color = lineColor.copy(alpha = 0.4f),
                    start = PointF(0f, yPer).translate(offsetGeometry).toOffset(),
                    end = PointF(1f, yPer).translate(offsetGeometry).toOffset()
                )
            }
        }
    }
}

@Preview
@Composable
fun LineChartPreview() {
    AppTheme {
        LineChart(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .size(200.dp)
                .padding(16.dp),
            dataPoints = listOf(
                PointD(0.0, 30.0),
//                PointD(1.0, 50.0),
//                PointD(3.0, 40.0),
//                PointD(4.0, 70.0),
//                PointD(6.0, 80.0),
//                PointD(7.0, 60.0),
            ),
            yMin = 20.0,
            yMax = 100.0
        )
    }
}