package com.masselis.portfolio.ui.components

import android.annotation.SuppressLint
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import portfolio.composeapp.generated.resources.Res

@Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
@SuppressLint("SetJavaScriptEnabled")
@Composable
internal actual fun MapView(
    locations: List<Location>,
    controller: MapController,
    shape: CornerBasedShape?,
    modifier: Modifier
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        locations.forEach { loc ->
                            evaluateJavascript("placeMarker(${loc.lat}, ${loc.lng});", null)
                        }
                    }
                }
                webChromeClient = WebChromeClient()
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                loadUrl(Res.getUri("files/map.html"))
                controller.flyToImpl = { loc ->
                    evaluateJavascript("map.flyTo([${loc.lat}, ${loc.lng}], ${loc.zoom})", null)
                }
            }
        },
        onRelease = { controller.flyToImpl = null },
        modifier = modifier,
    )
}
