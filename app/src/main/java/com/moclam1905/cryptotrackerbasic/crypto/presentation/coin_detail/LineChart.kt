package com.moclam1905.cryptotrackerbasic.crypto.presentation.coin_detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moclam1905.cryptotrackerbasic.crypto.domain.CoinPrice
import com.moclam1905.cryptotrackerbasic.ui.theme.CryptoTrackerBasicTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    dataPoints: List<DataPoint>,
    chartStyle: ChartStyle,
    visibleDataPointIndices: IntRange,
    unit: String,
    selectedDataPoint: DataPoint? = null,
    onSelectedDataPoint: (DataPoint) -> Unit = {},
    onXLabelWidthChange: (Float) -> Unit = {},
    showHelperLine: Boolean = true,
) {
    val textStyle = LocalTextStyle.current.copy(
        fontSize = chartStyle.labelFontSize
    )

    val visibleDataPoints = remember(dataPoints, visibleDataPointIndices) {
        dataPoints.slice(visibleDataPointIndices)
    }

    val maxYValue = remember(visibleDataPoints) {
        visibleDataPoints.maxOfOrNull { it.y } ?: 0f
    }

    val minYValue = remember(visibleDataPoints) {
        visibleDataPoints.minOfOrNull { it.y } ?: 0f
    }

    val measurer = rememberTextMeasurer()
    var xLabelWidth by remember {
        mutableFloatStateOf(0f)

    }
    LaunchedEffect(key1 = xLabelWidth) {
        onXLabelWidthChange(xLabelWidth)
    }

    val selectedDataPointIndex = remember(selectedDataPoint) {
        selectedDataPoint?.let { dataPoints.indexOf(it) }
    }

    var drawPoint by remember {
        mutableStateOf(listOf<DataPoint>())
    }

    var isShowingDataPoints by remember {
        mutableStateOf(
            selectedDataPoint != null
        )
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(dataPoints, xLabelWidth) {
                detectHorizontalDragGestures { change, _ ->
                    val newSelectedDataPointIndex = getSelectedDataPointIndex(
                        touchOffsetX = change.position.x,
                        triggerWith = xLabelWidth,
                        drawPoints = drawPoint
                    )

                    isShowingDataPoints =
                        (newSelectedDataPointIndex + visibleDataPointIndices.first) in visibleDataPointIndices

                    if (isShowingDataPoints) {
                        onSelectedDataPoint(dataPoints[newSelectedDataPointIndex])
                    }
                }
            }
    ) {

        val minLabelSpacingYPx = chartStyle.minYLabelSpacing.toPx()
        val verticalPaddingPx = chartStyle.verticalPadding.toPx()
        val horizontalPaddingPx = chartStyle.horizontalPadding.toPx()
        val xAxisLabelSpacingPx = chartStyle.xAxisLabelSpacing.toPx()

        val xLabelTextLayoutResult = visibleDataPoints.map {
            measurer.measure(
                text = it.xLabel,
                style = textStyle.copy(textAlign = TextAlign.Center)
            )
        }

        val maxXLabelWidth = xLabelTextLayoutResult.maxOfOrNull { it.size.width } ?: 0
        val maxXLabelHeight = xLabelTextLayoutResult.maxOfOrNull { it.size.height } ?: 0
        val maxXLabelLineCount = xLabelTextLayoutResult.maxOfOrNull { it.lineCount } ?: 0
        val xLabelLineHeight = maxXLabelHeight / maxXLabelLineCount

        val viewPortHeightPx =
            size.height - (maxXLabelHeight + 2 * verticalPaddingPx + xLabelLineHeight + xAxisLabelSpacingPx)


        // Y Labels calculation
        val labelViewPortHeightPx = viewPortHeightPx + xLabelLineHeight
        val labelCountExcludingLastLabel =
            (labelViewPortHeightPx / (xLabelLineHeight + minLabelSpacingYPx)).toInt()
        val valueIncrement = (maxYValue - minYValue) / labelCountExcludingLastLabel
        val yLabels = (0..labelCountExcludingLastLabel).map {
            ValueLabel(
                value = maxYValue - (valueIncrement * it),
                unit = unit,
            )
        }
        val yLabelTextLayoutResult = yLabels.map {
            measurer.measure(
                text = it.formattedValue(),
                style = textStyle
            )
        }

        val maxYLabelWidth = yLabelTextLayoutResult.maxOfOrNull { it.size.width } ?: 0

        val viewPortTopY = verticalPaddingPx + xLabelLineHeight + 10f
        val viewPortRightX = size.width
        val viewPortBottomY = viewPortTopY + viewPortHeightPx
        val viewPortLeftX = 2f * horizontalPaddingPx + maxYLabelWidth

        xLabelWidth = maxXLabelWidth + xAxisLabelSpacingPx
        xLabelTextLayoutResult.forEachIndexed { index, textLayoutResult ->
            val x = viewPortLeftX + xAxisLabelSpacingPx / 2f + xLabelWidth * index
            val color =
                if (index == selectedDataPointIndex) chartStyle.selectedColor else chartStyle.unselectedColor
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(
                    x = x,
                    y = viewPortBottomY + xAxisLabelSpacingPx
                ),
                color = color
            )

            if (showHelperLine) {
                drawLine(
                    color = color,
                    start = Offset(
                        x = x + textLayoutResult.size.width.toFloat() / 2f,
                        y = viewPortTopY
                    ),
                    end = Offset(
                        x = x + textLayoutResult.size.width.toFloat() / 2f,
                        y = viewPortBottomY
                    ),
                    strokeWidth = if (index == selectedDataPointIndex)
                        chartStyle.helperLineThicknessPx * 2f else chartStyle.helperLineThicknessPx
                )
            }

            if (selectedDataPointIndex == index) {
                val valueLabel = ValueLabel(
                    value = visibleDataPoints[index].y,
                    unit = unit
                )

                val valueResult = measurer.measure(
                    text = valueLabel.formattedValue(),
                    style = textStyle.copy(
                        color = chartStyle.selectedColor
                    ),
                    maxLines = 1
                )

                val textPositionX = if (selectedDataPointIndex == visibleDataPointIndices.last) {
                    x - valueResult.size.width
                } else {
                    x - valueResult.size.width / 2f
                } + textLayoutResult.size.width / 2f

                val isTextInVisibleRange =
                    (size.width - textPositionX).roundToInt() in 0..size.width.roundToInt()

                if (isTextInVisibleRange) {
                    drawText(
                        textLayoutResult = valueResult,
                        topLeft = Offset(
                            x = textPositionX,
                            y = viewPortTopY - valueResult.size.height - 10f
                        )
                    )
                }
            }
        }

        val heightRequiredForLabels = xLabelLineHeight * (labelCountExcludingLastLabel + 1)
        val remainingHeight = labelViewPortHeightPx - heightRequiredForLabels
        val betweenLabelSpacing = remainingHeight / labelCountExcludingLastLabel

        yLabelTextLayoutResult.forEachIndexed { index, textLayoutResult ->
            val x = horizontalPaddingPx + maxYLabelWidth - textLayoutResult.size.width.toFloat()
            val y =
                viewPortTopY + index * (xLabelLineHeight + betweenLabelSpacing) - xLabelLineHeight / 2
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(
                    x = x,
                    y = y
                ),
                color = chartStyle.unselectedColor
            )

            if (showHelperLine) {
                drawLine(
                    color = chartStyle.unselectedColor,
                    start = Offset(
                        x = viewPortLeftX,
                        y = y + textLayoutResult.size.height.toFloat() / 2f
                    ),
                    end = Offset(
                        x = viewPortRightX,
                        y = y + textLayoutResult.size.height.toFloat() / 2f
                    ),
                    strokeWidth = chartStyle.helperLineThicknessPx
                )
            }
        }

        drawPoint = visibleDataPointIndices.map {
            val x =
                viewPortLeftX + (it - visibleDataPointIndices.first) * xLabelWidth + xLabelWidth / 2f
            val ratio = (dataPoints[it].y - minYValue) / (maxYValue - minYValue)
            val y = viewPortBottomY - ratio * viewPortHeightPx
            DataPoint(
                x = x,
                y = y,
                xLabel = dataPoints[it].xLabel
            )
        }

        val conPoints1 = mutableListOf<DataPoint>()
        val conPoints2 = mutableListOf<DataPoint>()

        for (i in 1..<drawPoint.size) {
            val p0 = drawPoint[i - 1]
            val p1 = drawPoint[i]
            val x = (p1.x + p0.x) / 2f
            val y1 = p0.y
            val y2 = p1.y

            conPoints1.add(DataPoint(x, y1, ""))
            conPoints2.add(DataPoint(x, y2, ""))
        }

        val linePath = Path().apply {
            if (drawPoint.isNotEmpty()) {
                moveTo(drawPoint.first().x, drawPoint.first().y)

                for (i in 1 until drawPoint.size) {
                    cubicTo(
                        x1 = conPoints1[i - 1].x,
                        y1 = conPoints1[i - 1].y,
                        x2 = conPoints2[i - 1].x,
                        y2 = conPoints2[i - 1].y,
                        x3 = drawPoint[i].x,
                        y3 = drawPoint[i].y
                    )
                }
            }
        }

        drawPath(
            path = linePath,
            color = chartStyle.chartLineColor,
            style = Stroke(
                width = 5f,
                cap = StrokeCap.Round
            )
        )

        drawPoint.forEachIndexed { index, point ->
            if (isShowingDataPoints) {
                val circleOffset = Offset(
                    x = point.x,
                    y = point.y
                )
                drawCircle(
                    color = chartStyle.selectedColor,
                    center = circleOffset,
                    radius = 10f
                )

                if (selectedDataPointIndex == index) {
                    drawCircle(
                        color = Color.White,
                        center = circleOffset,
                        radius = 16f
                    )

                    drawCircle(
                        color = chartStyle.selectedColor,
                        center = circleOffset,
                        radius = 16f,
                        style = Stroke(width = 3f)
                    )
                }
            }
        }
    }
}

private fun getSelectedDataPointIndex(
    touchOffsetX: Float,
    triggerWith: Float,
    drawPoints: List<DataPoint>
): Int {
    val triggerRangeLeft = touchOffsetX - triggerWith / 2f
    val triggerRangeRight = touchOffsetX + triggerWith / 2f

    return drawPoints.indexOfFirst {
        it.x in triggerRangeLeft..triggerRangeRight
    }


}

@Preview
@Composable
private fun LineChartPreview() {
    CryptoTrackerBasicTheme {
        val coinHistoryRandomized = remember {
            (1..20).map {
                CoinPrice(
                    priceUsd = Random.nextFloat() * 1000.0,
                    dateTime = ZonedDateTime.now().plusHours(it.toLong())
                )
            }
        }

        val style = ChartStyle(
            chartLineColor = Color.Black,
            unselectedColor = Color.Gray,
            selectedColor = Color.Black,
            helperLineThicknessPx = 1f,
            axisLineThicknessPx = 5f,
            labelFontSize = 14.sp,
            minYLabelSpacing = 20.dp,
            verticalPadding = 8.dp,
            horizontalPadding = 8.dp,
            xAxisLabelSpacing = 8.dp
        )

        val dataPoints = remember {
            coinHistoryRandomized.map {
                DataPoint(
                    x = it.dateTime.hour.toFloat(),
                    y = it.priceUsd.toFloat(),
                    xLabel = DateTimeFormatter.ofPattern("ha\nm/d").format(it.dateTime)
                )
            }
        }

        LineChart(
            dataPoints = dataPoints,
            chartStyle = style,
            visibleDataPointIndices = dataPoints.indices,
            unit = "$",
            showHelperLine = true,
            modifier = Modifier
                .width(700.dp)
                .height(300.dp)
                .background(Color.White),
            selectedDataPoint = dataPoints[1]
        )
    }

}