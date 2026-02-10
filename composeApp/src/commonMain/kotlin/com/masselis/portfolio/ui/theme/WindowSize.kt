package com.masselis.portfolio.ui.theme

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

enum class WindowSizeClass {
    Compact,   // < 600dp
    Medium,    // 600-1200dp
    Expanded,  // > 1200dp
}

val LocalWindowSizeClass = compositionLocalOf { WindowSizeClass.Expanded }

@Composable
fun rememberWindowSizeClass(widthDp: Int): WindowSizeClass = remember(widthDp) {
    when {
        widthDp < 600 -> WindowSizeClass.Compact
        widthDp <= 1200 -> WindowSizeClass.Medium
        else -> WindowSizeClass.Expanded
    }
}
