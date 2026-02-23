package com.masselis.portfolio.ui.screens

import android.annotation.SuppressLint
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.masselis.portfolio.ui.utils.PdfPrinter
import portfolio.composeapp.generated.resources.Res

@Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
@SuppressLint("SetJavaScriptEnabled")
@Composable
internal actual fun ResumeWebView(
    printer: PdfPrinter,
    modifier: Modifier,
) {
    val context = LocalContext.current
    var webView by remember { mutableStateOf<WebView?>(null) }
    Box(
        modifier = modifier,
    ) {
        Spacer(
            Modifier
                .fillMaxHeight(.5f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .align(Alignment.TopCenter)
        )
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webView = this
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    loadUrl(Res.getUri("files/resume/index.html"))
                }
            },
        )
    }
    SideEffect {
        printer.printer = {
            context.getSystemService(PrintManager::class.java).print(
                "CV Vincent Masselis",
                webView!!.createPrintDocumentAdapter("CV_Vincent_Masselis"),
                PrintAttributes.Builder()
                    .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    .build()
            )
        }
    }
}
