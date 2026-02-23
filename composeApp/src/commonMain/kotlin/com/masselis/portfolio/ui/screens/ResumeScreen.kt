package com.masselis.portfolio.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.masselis.portfolio.ui.components.Section
import com.masselis.portfolio.ui.utils.CommonParcelize
import com.masselis.portfolio.ui.utils.LocalScaffoldPadding
import com.masselis.portfolio.ui.utils.rememberPdfPrinter
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.screen.StaticScreen
import dev.zacsweers.metro.AppScope

@CommonParcelize
public data object Resume : Route, StaticScreen

@CircuitInject(Resume::class, AppScope::class)
@Composable
internal fun ResumeScreen(
    modifier: Modifier = Modifier,
) {
    val pdfPrinter = rememberPdfPrinter()
    Box(
        modifier
            .fillMaxSize()
            .padding(top = LocalScaffoldPadding.current.calculateTopPadding())
    ) {
        ResumeWebView(
            printer = pdfPrinter,
            modifier = Modifier.matchParentSize()
        )
        FloatingActionButton(
            onClick = pdfPrinter::print,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(PaddingValues.Section)
        ) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = null,
            )
        }
    }
}
