package com.masselis.portfolio.ui.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Returns the scaffold values required by the parent Scaffold composable.
 *
 * If no parent Scaffold was found, [LocalScaffoldPadding] returns [PaddingValues.Zero]
 */
internal val LocalScaffoldPadding = staticCompositionLocalOf { PaddingValues.Zero }
