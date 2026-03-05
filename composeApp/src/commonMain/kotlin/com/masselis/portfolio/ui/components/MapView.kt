package com.masselis.portfolio.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Immutable
internal data class Location(
    val city: String,
    val country: String,
    val lat: Double,
    val lng: Double,
    val zoom: Int,
)

@Stable
internal class MapController {
    var flyToImpl: ((Location) -> Unit)? = null

    fun flyTo(location: Location) {
        flyToImpl?.invoke(location)
    }
}

@Composable
internal fun rememberMapController() = remember { MapController() }

@Composable
internal expect fun MapView(
    locations: List<Location>,
    controller: MapController,
    modifier: Modifier = Modifier,
)

internal fun leafletHtml(locations: List<Location>): String {
    val markers = locations.mapIndexed { i, loc ->
        "var m$i = L.marker([${loc.lat}, ${loc.lng}]).addTo(map);"
    }.joinToString("\n      ")
    val fitBounds = if (locations.size > 1) {
        val markerList = locations.indices.joinToString(", ") { "m$it" }
        "\n      map.fitBounds(L.featureGroup([$markerList]).getBounds());"
    } else ""
    return """
        <!DOCTYPE html>
        <html>
        <head>
          <meta charset="utf-8"/>
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"/>
          <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
          <style>html, body, #map { height: 100%; margin: 0; padding: 0; }</style>
        </head>
        <body>
        <div id="map"></div>
        <script>
          var map = L.map('map', {scrollWheelZoom: false});
          L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
          }).addTo(map);
          $markers$fitBounds
          window.addEventListener('message', function(e) {
              if (e.data && e.data.type === 'flyTo')
                  map.flyTo([e.data.lat, e.data.lng], e.data.zoom);
          });
        </script>
        </body>
        </html>
    """.trimIndent()
}
