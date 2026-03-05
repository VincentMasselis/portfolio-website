package com.masselis.portfolio.ui.components

import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size.Companion.Unspecified
import androidx.compose.ui.unit.Density
import androidx.compose.ui.viewinterop.HtmlElementView
import kotlinx.browser.document
import org.w3c.dom.HTMLIFrameElement

@Suppress("OPT_IN_USAGE")
@JsFun("(html) => URL.createObjectURL(new Blob([html], {type: 'text/html'}))")
private external fun createBlobUrl(html: String): String

@Suppress("OPT_IN_USAGE")
@JsFun("(url) => URL.revokeObjectURL(url)")
private external fun revokeBlobUrl(url: String)

@Suppress("OPT_IN_USAGE")
@JsFun("(iframe, lat, lng, zoom) => iframe.contentWindow.postMessage({type:'flyTo', lat:lat, lng:lng, zoom:zoom}, '*')")
private external fun postFlyTo(iframe: HTMLIFrameElement, lat: Double, lng: Double, zoom: Int)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal actual fun MapView(
    locations: List<Location>,
    controller: MapController,
    modifier: Modifier
) {
    val blobUrl = remember(locations) { createBlobUrl(leafletHtml(locations)) }
    // ShapeDefaults.Medium is the default value used by "Card()"s in Compose
    val borderRadius = remember { ShapeDefaults.Medium.topStart.toPx(Unspecified, Density(1f)) }
    HtmlElementView(
        factory = {
            (document.createElement("iframe") as HTMLIFrameElement).apply {
                src = blobUrl
                style.border = "none"
                style.width = "100%"
                style.height = "100%"
                // CSS px is not a physical pixel — it's a logical pixel (also called a CSS pixel),
                // which is device-independent. It's directly analogous to dp in Compose.
                // Applies a border radius in top left and bottom left
                style.borderRadius = "${borderRadius}px 0 0 ${borderRadius}px"
                controller.flyToImpl = { loc ->
                    postFlyTo(this, loc.lat, loc.lng, loc.zoom)
                }
            }
        },
        onRelease = {
            controller.flyToImpl = null
            it.remove()
        },
        modifier = modifier,
    )
    DisposableEffect(blobUrl) {
        onDispose { revokeBlobUrl(blobUrl) }
    }
}
