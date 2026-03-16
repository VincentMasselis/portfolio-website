package com.masselis.portfolio.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.data.TimelineEntry
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun TimelineItem(
    timelineEntry: TimelineEntry,
    isLast: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.Top,
    ) {
        // Date column
        Text(
            text = when (val time = timelineEntry.time) {
                is TimelineEntry.Moment -> "${time.moment.year}"
                is TimelineEntry.Range -> "${time.from.year}-${time.to.year}"
                is TimelineEntry.Pending -> "${time.moment.year}-Maintenant"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.width(80.dp).padding(top = 4.dp),
        )

        // Dot and line
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
            )
            val lineColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            if (isLast) {
                Canvas(
                    modifier = Modifier
                        .width(2.dp)
                        .fillMaxHeight()
                ) {
                    drawLine(
                        color = lineColor,
                        start = Offset(x = size.width / 2, y = 0f),
                        end = Offset(x = size.width / 2, y = size.height),
                        strokeWidth = size.width,
                        pathEffect = PathEffect.dashPathEffect(
                            // dot size, gap size
                            intervals = floatArrayOf(
                                6.dp.toPx(),
                                6.dp.toPx()
                            ),
                            phase = 0f,
                        ),
                    )

                }
            } else {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(lineColor),
                )
            }
        }

        // Content
        Column(modifier = Modifier.weight(1f).padding(bottom = 32.dp)) {
            SelectionContainer {
                Text(
                    text = stringResource(timelineEntry.title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Spacer(Modifier.height(4.dp))
            SelectionContainer {
                Text(
                    text = stringResource(timelineEntry.description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                )
            }
        }
    }
}
