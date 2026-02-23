package com.masselis.portfolio.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.masselis.portfolio.ui.utils.PdfPrinter

@Composable
internal actual fun ResumeWebView(
    printer: PdfPrinter, modifier: Modifier
) {
    Box(modifier = modifier) {
        Text(
            text = "Vous ne devriez pas être là",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
