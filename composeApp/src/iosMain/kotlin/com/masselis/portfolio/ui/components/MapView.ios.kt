package com.masselis.portfolio.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.WebKit.WKWebView

@OptIn(ExperimentalForeignApi::class)
@Composable
internal actual fun MapView(
    locations: List<Location>,
    controller: MapController,
    modifier: Modifier
) {
    UIKitView(
        factory = {
            WKWebView().apply {
                loadHTMLString(
                    leafletHtml(locations),
                    baseURL = NSURL(string = "https://openstreetmap.org"),
                )
                controller.flyToImpl = { loc ->
                    evaluateJavaScript(
                        "map.flyTo([${loc.lat}, ${loc.lng}], ${loc.zoom})",
                        completionHandler = null,
                    )
                }
            }
        },
        onRelease = { controller.flyToImpl = null },
        modifier = modifier,
    )
}
