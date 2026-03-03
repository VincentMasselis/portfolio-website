package com.masselis.portfolio.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal expect fun VerticalScrollbar(scrollState: ScrollState, modifier: Modifier = Modifier)
