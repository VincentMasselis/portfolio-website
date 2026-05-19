package com.masselis.portfolio.ui.screens

import android.net.Uri
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.os.ParcelFileDescriptor.MODE_READ_ONLY
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService
import androidx.pdf.PdfDocument
import androidx.pdf.SandboxedPdfLoader
import androidx.pdf.compose.PdfViewer
import androidx.pdf.compose.PdfViewerState
import com.masselis.portfolio.ui.utils.PdfPrinter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import portfolio.composeapp.generated.resources.Res
import java.io.File
import java.io.FileOutputStream

@Composable
internal actual fun ResumeWebView(
    printer: PdfPrinter,
    modifier: Modifier,
) {
    val context = LocalContext.current
    var pdfBytes by rememberSaveable { mutableStateOf<ByteArray?>(null) }
    var pdfDocument by remember { mutableStateOf<PdfDocument?>(null) }
    val state = remember { PdfViewerState() }

    LaunchedEffect(Unit) {
        val bytes = withContext(Dispatchers.IO) { Res.readBytes("files/resume.pdf") }
        pdfBytes = bytes
        pdfDocument = withContext(Dispatchers.IO) {
            File(context.cacheDir, "resume.pdf")
                .apply { writeBytes(bytes) }
                .let { it to ParcelFileDescriptor.open(it, MODE_READ_ONLY) }
                .let { (file, fd) ->
                    fd.use { SandboxedPdfLoader(context).openDocument(Uri.fromFile(file), fd) }
                }
        }
    }

    DisposableEffect(Unit) {
        onDispose { pdfDocument?.close() }
    }

    SideEffect {
        printer.printer = printAction@{
            val bytes = pdfBytes ?: return@printAction
            context.getSystemService<PrintManager>()!!.print(
                "CV Vincent Masselis",
                PdfPrintAdapter(bytes),
                PrintAttributes.Builder()
                    .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    .build()
            )
        }
    }

    PdfViewer(
        pdfDocument = pdfDocument,
        state = state,
        modifier = modifier,
    )
}

private class PdfPrintAdapter(private val bytes: ByteArray) : PrintDocumentAdapter() {
    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes?,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback?,
        extras: Bundle?,
    ) {
        if (cancellationSignal?.isCanceled == true) { callback?.onLayoutCancelled(); return }
        callback?.onLayoutFinished(
            PrintDocumentInfo.Builder("CV_Vincent_Masselis.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .build(),
            true
        )
    }

    override fun onWrite(
        pages: Array<out PageRange>?,
        destination: ParcelFileDescriptor?,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback?,
    ) {
        try {
            FileOutputStream(destination!!.fileDescriptor).use { it.write(bytes) }
            callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
        } catch (e: Exception) {
            callback?.onWriteFailed(e.message)
        }
    }
}
