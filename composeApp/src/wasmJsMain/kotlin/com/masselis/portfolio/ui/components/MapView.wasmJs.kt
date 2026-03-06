package com.masselis.portfolio.ui.components

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size.Companion.Unspecified
import androidx.compose.ui.unit.Density
import androidx.compose.ui.viewinterop.HtmlElementView
import kotlinx.browser.document
import org.w3c.dom.HTMLIFrameElement
import portfolio.composeapp.generated.resources.Res

@Suppress("OPT_IN_USAGE")
@JsFun("(iframe, script) => { if (iframe.contentWindow) iframe.contentWindow.eval(script); }")
private external fun evaluateJavascript(iframe: HTMLIFrameElement, script: String)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal actual fun MapView(
    locations: List<Location>,
    controller: MapController,
    shape: CornerBasedShape?,
    modifier: Modifier
) {

    val html by produceState<String?>(null, locations) {
        value = Res.readBytes("files/map.html").decodeToString()
        awaitDispose {}
    }
    val borderRadiusTopStart = remember { shape?.topStart?.toPx(Unspecified, Density(1f)) }
    val borderRadiusBottomStart = remember { shape?.bottomStart?.toPx(Unspecified, Density(1f)) }
    if (html != null) {
        HtmlElementView(
            factory = {
                (document.createElement("iframe") as HTMLIFrameElement).apply {
                    srcdoc = html!!
                    style.border = "none"
                    style.width = "100%"
                    style.height = "100%"
                    // CSS px is not a physical pixel — it's a logical pixel (also called a CSS pixel),
                    // which is device-independent. It's directly analogous to dp in Compose.
                    // Applies a border radius in top left and bottom left
                    style.borderRadius =
                        "${borderRadiusTopStart}px 0 0 ${borderRadiusBottomStart}px"
                    onload = {
                        locations.forEach { loc ->
                            evaluateJavascript(
                                this,
                                "placeMarker(${loc.lat}, ${loc.lng});"
                            )
                        }
                    }
                    controller.flyToImpl = { loc ->
                        evaluateJavascript(
                            this,
                            "map.flyTo([${loc.lat}, ${loc.lng}], ${loc.zoom});"
                        )
                    }
                }
            },
            onRelease = {
                controller.flyToImpl = null
                it.remove()
            },
            modifier = modifier,
        )
    }
}
