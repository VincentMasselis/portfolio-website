package com.masselis.portfolio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun Section(
    backgroundColor: Color,
    paddingValues: PaddingValues = PaddingValues.Section,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor),
        contentAlignment = Alignment.TopCenter,
    ) {
        val layoutDirection = LocalLayoutDirection.current
        Column(
            modifier = Modifier
                .widthIn(max = SectionMaxWidth)
                .fillMaxWidth()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = paddingValues.calculateStartPadding(layoutDirection),
                    end = paddingValues.calculateEndPadding(layoutDirection),
                    bottom = paddingValues.calculateBottomPadding(),
                ),
            content = content,
        )
    }
}

internal val SectionMaxWidth = 1200.dp

internal val PaddingValues.Companion.Section
    get() = PaddingValues(horizontal = 32.dp, vertical = 48.dp)

@Composable
internal fun PaddingValues.copy(
    start: Dp = calculateStartPadding(LocalLayoutDirection.current),
    top: Dp = calculateTopPadding(),
    end: Dp = calculateEndPadding(LocalLayoutDirection.current),
    bottom: Dp = calculateBottomPadding(),
): PaddingValues = PaddingValues(start = start, top = top, end = end, bottom = bottom)
