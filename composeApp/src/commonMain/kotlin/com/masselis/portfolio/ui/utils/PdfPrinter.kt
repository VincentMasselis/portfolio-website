package com.masselis.portfolio.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

@Stable
internal class PdfPrinter {
    internal var printer: () -> Unit = { error("No declared printer found") }
    fun print() = printer()
}

@Composable
internal fun rememberPdfPrinter() = remember { PdfPrinter() }