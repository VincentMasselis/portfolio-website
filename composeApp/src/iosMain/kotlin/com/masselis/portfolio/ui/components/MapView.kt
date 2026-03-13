package com.masselis.portfolio.ui.components

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSURL
import platform.WebKit.WKUserScript
import platform.WebKit.WKUserScriptInjectionTime.WKUserScriptInjectionTimeAtDocumentEnd
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import portfolio.composeapp.generated.resources.Res

@OptIn(ExperimentalForeignApi::class)
@Composable
internal actual fun MapView(
    locations: List<Location>,
    controller: MapController,
    shape: CornerBasedShape?,
    modifier: Modifier
) {
    UIKitView(
        factory = {
            WKWebViewConfiguration()
                .apply {
                    userContentController.addUserScript(
                        WKUserScript(
                            source = locations.joinToString("\n") { loc ->
                                "placeMarker(${loc.lat}, ${loc.lng});"
                            },
                            injectionTime = WKUserScriptInjectionTimeAtDocumentEnd,
                            forMainFrameOnly = true,
                        )
                    )
                }
                .let { config ->
                    WKWebView(
                        frame = CGRectMake(0.0, 0.0, 0.0, 0.0),
                        configuration = config
                    ).apply {
                        NSURL(string = Res.getUri("files/map.html")).also { url ->
                            loadFileURL(
                                url,
                                allowingReadAccessToURL = url.URLByDeletingLastPathComponent!!
                            )
                        }
                        controller.flyToImpl = { loc ->
                            evaluateJavaScript(
                                "map.flyTo([${loc.lat}, ${loc.lng}], ${loc.zoom})",
                                completionHandler = null,
                            )
                        }
                    }
                }
        },
        onRelease = { controller.flyToImpl = null },
        modifier = modifier,
    )
}
