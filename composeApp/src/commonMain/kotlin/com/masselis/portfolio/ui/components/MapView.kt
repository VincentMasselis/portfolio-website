package com.masselis.portfolio.ui.components

import androidx.compose.foundation.shape.CornerBasedShape
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
    shape: CornerBasedShape? = null,
    modifier: Modifier = Modifier,
)
