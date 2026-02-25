package com.masselis.portfolio.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

internal enum class WindowSizeClass {
    Compact,   // < 600dp
    Medium,    // 600-1200dp
    Expanded;  // > 1200dp
}

internal val LocalWindowSizeClass = compositionLocalOf { WindowSizeClass.Expanded }

@Composable
internal fun rememberWindowSizeClass(widthDp: Int): WindowSizeClass = remember(widthDp) {
    when (widthDp) {
        in 0..599 -> WindowSizeClass.Compact
        in 600..1199 -> WindowSizeClass.Medium
        else -> WindowSizeClass.Expanded
    }
}
