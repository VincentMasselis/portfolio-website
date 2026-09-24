package com.masselis.portfolio.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp

@Composable
internal fun MeshGradientBackground(
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
) {
    val colorScheme = MaterialTheme.colorScheme
    Spacer(
        modifier
            // Offscreen so the DstIn fade only masks this layer, not what is drawn below it
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            .drawBehind {
                drawRect(colorScheme.surfaceContainerHigh)
                // The fade stays anchored to the layout bounds while the blobs lag behind the scroll
                translate(top = scrollState.value * ParallaxFactor) {
                    listOf(
                        Blob(
                            colorScheme.primaryContainer.copy(alpha = 0.8f),
                            x = 0.18f,
                            y = 0.55f,
                            radiusX = 0.38f,
                            radiusY = 0.6f
                        ),
                        Blob(
                            colorScheme.tertiaryContainer.copy(alpha = 0.73f),
                            x = 0.52f,
                            y = 0.35f,
                            radiusX = 0.3f,
                            radiusY = 0.55f
                        ),
                        Blob(
                            lerp(colorScheme.primaryContainer, colorScheme.primary, 0.12f).copy(
                                alpha = 0.8f
                            ),
                            x = 0.82f, y = 0.65f, radiusX = 0.28f, radiusY = 0.5f,
                        ),
                        Blob(
                            colorScheme.tertiaryContainer.copy(alpha = 0.53f),
                            x = 0.65f,
                            y = 0.85f,
                            radiusX = 0.22f,
                            radiusY = 0.4f
                        ),
                    ).forEach { blob ->
                        val center = Offset(size.width * blob.x, size.height * blob.y)
                        val radius = size.width * blob.radiusX
                        scale(
                            scaleX = 1f,
                            scaleY = size.height * blob.radiusY / radius,
                            pivot = center
                        ) {
                            drawCircle(
                                brush = Brush.radialGradient(
                                    0f to blob.color,
                                    0.7f to Color.Transparent,
                                    center = center,
                                    radius = radius,
                                ),
                                radius = radius,
                                center = center,
                            )
                        }
                    }
                }
                drawRect(
                    Brush.verticalGradient(0.45f to Color.Black, 1f to Color.Transparent),
                    blendMode = BlendMode.DstIn,
                )
            },
    )
}

private const val ParallaxFactor = 0.45f

@Immutable
private data class Blob(
    val color: Color,
    val x: Float,
    val y: Float,
    val radiusX: Float,
    val radiusY: Float,
)
