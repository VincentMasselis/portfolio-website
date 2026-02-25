package com.masselis.portfolio.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import com.masselis.portfolio.ui.utils.PdfPrinter
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.UIKit.UIPrintInfo
import platform.UIKit.UIPrintInfoOutputType.UIPrintInfoOutputGeneral
import platform.UIKit.UIPrintInteractionController
import platform.UIKit.viewPrintFormatter
import platform.WebKit.WKWebView
import portfolio.composeapp.generated.resources.Res

@OptIn(ExperimentalForeignApi::class)
@Composable
internal actual fun ResumeWebView(
    printer: PdfPrinter,
    modifier: Modifier
) {
    var webView by remember { mutableStateOf<WKWebView?>(null) }
    UIKitView(
        factory = {
            WKWebView().apply {
                webView = this
                NSURL(string = Res.getUri("files/resume/index.html")).also { url ->
                    loadFileURL(
                        url,
                        allowingReadAccessToURL = url.URLByDeletingLastPathComponent!!
                    )
                }
            }
        },
        modifier = modifier,
    )
    SideEffect {
        printer.printer = {
            UIPrintInfo
                .printInfo()
                .apply {
                    outputType = UIPrintInfoOutputGeneral
                    jobName = "CV Vincent Masselis"
                }
                .let {
                    UIPrintInteractionController.sharedPrintController.apply {
                        printInfo = it
                        printFormatter = webView!!.viewPrintFormatter()
                    }
                }
                .presentAnimated(true, completionHandler = null)
        }
    }
}
