package com.masselis.portfolio.ui.components

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView

@Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
@SuppressLint("SetJavaScriptEnabled")
@Composable
internal actual fun MapView(
    locations: List<Location>,
    controller: MapController,
    modifier: Modifier
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadDataWithBaseURL(
                    "https://openstreetmap.org",
                    leafletHtml(locations),
                    "text/html",
                    "UTF-8",
                    null,
                )
                controller.flyToImpl = { loc ->
                    evaluateJavascript("map.flyTo([${loc.lat}, ${loc.lng}], ${loc.zoom})", null)
                }
            }
        },
        onRelease = { controller.flyToImpl = null },
        modifier = modifier.background(Color.Red),
    )
}
