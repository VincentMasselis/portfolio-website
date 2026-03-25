package com.masselis.portfolio.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import com.masselis.portfolio.ui.utils.PdfPrinter
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.PDFKit.PDFDocument
import platform.PDFKit.PDFView
import platform.UIKit.UIPrintInfo
import platform.UIKit.UIPrintInfoOutputType.UIPrintInfoOutputGeneral
import platform.UIKit.UIPrintInteractionController
import portfolio.composeapp.generated.resources.Res

@OptIn(ExperimentalForeignApi::class)
@Composable
internal actual fun ResumeWebView(
    printer: PdfPrinter,
    modifier: Modifier,
) {
    val pdfUrl = remember { NSURL(string = Res.getUri("files/resume.pdf")) }

    UIKitView(
        factory = {
            PDFView().apply {
                document = PDFDocument(uRL = pdfUrl)
                autoScales = true
            }
        },
        modifier = modifier,
    )

    SideEffect {
        printer.printer = {
            UIPrintInfo.printInfo()
                .apply {
                    outputType = UIPrintInfoOutputGeneral
                    jobName = "CV Vincent Masselis"
                }
                .let {
                    UIPrintInteractionController.sharedPrintController.apply {
                        printInfo = it
                        printingItems = listOf(pdfUrl)
                    }
                }
                .presentAnimated(true, completionHandler = null)
        }
    }
}
