package com.masselis.portfolio.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.masselis.portfolio.ui.utils.PdfPrinter

@Composable
internal expect fun ResumeWebView(printer: PdfPrinter, modifier: Modifier = Modifier)
