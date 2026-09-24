package com.masselis.portfolio.ui.utils

import androidx.compose.runtime.staticCompositionLocalOf
import dev.chrisbanes.haze.HazeState

/**
 * Screens that scroll under a frosted top bar register their content as a haze source of this state.
 */
internal val LocalTopBarHazeState = staticCompositionLocalOf<HazeState> { error("Missing HazeState instance") }
