package com.masselis.portfolio.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.data.PortfolioData
import com.masselis.portfolio.data.formatDayMonth
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.todayIn
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.about_hero_myself_overlay_future_date_availability
import portfolio.composeapp.generated.resources.about_hero_myself_overlay_immediate_availability
import portfolio.composeapp.generated.resources.me
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.time.Clock

@Composable
public fun MyselfImage(
    allowRingAndPill: Boolean = true,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Image(
            painter = painterResource(Res.drawable.me),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .shadow(elevation = 8.dp, shape = CircleShape)
                .clip(CircleShape)
        )
        if (PortfolioData.availability != null && allowRingAndPill) {
            val availability = PortfolioData.availability!!
            RingAndPill(
                pillText =
                    if (Clock.System.todayIn(currentSystemDefault()) < availability)
                        stringResource(
                            Res.string.about_hero_myself_overlay_future_date_availability,
                            availability.formatDayMonth()
                        )
                    else
                        stringResource(Res.string.about_hero_myself_overlay_immediate_availability),
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.matchParentSize()
            )
        }
    }
}

@Composable
private fun RingAndPill(
    pillText: String,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val overlayTextStyle = MaterialTheme.typography
        .headlineMedium
        .copy(textAlign = TextAlign.Center)
    val measurements = textMeasurer.measure(
        text = pillText,
        style = overlayTextStyle
    )
    val infiniteTransition = rememberInfiniteTransition(label = "gradient_rotation")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle"
    )
    Canvas(modifier) {
        val radius = size.minDimension / 2
        val angleRad = angle * (PI / 180f).toFloat()
        val cos = cos(angleRad)
        val sin = sin(angleRad)
        val halfExtent = size.minDimension / 2
        val availableBrush = Brush.linearGradient(
            colors = colors,
            start = Offset(center.x - cos * halfExtent, center.y - sin * halfExtent),
            end = Offset(center.x + cos * halfExtent, center.y + sin * halfExtent)
        )

        // 1. Draw the ring (donut)
        clipPath(Path().apply {
            fillType = PathFillType.EvenOdd
            addOval(Rect(center, radius))
            addOval(Rect(center, radius - 6.dp.toPx()))
        }) {
            drawCircle(brush = availableBrush)
        }

        // 2. Draw the badge (can extend outside the ring, no subtraction)
        val topLeft = Offset(
            x = (size.width / 2) - (measurements.size.width / 2),
            y = size.height - measurements.size.height.toFloat() - 2.dp.toPx(),
        )
        clipPath(Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        offset = Offset(
                            x = topLeft.x - 4.dp.toPx(),
                            y = topLeft.y - 2.dp.toPx()
                        ),
                        size = Size(
                            width = measurements.size.width.toFloat() + 8.dp.toPx(),
                            height = measurements.size.height.toFloat() + 4.dp.toPx()
                        )
                    ),
                    cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                )
            )
        }) {
            drawRect(brush = availableBrush, size = size)
            drawText(
                textMeasurer = textMeasurer,
                text = pillText,
                style = overlayTextStyle,
                topLeft = topLeft
            )
        }
    }
}