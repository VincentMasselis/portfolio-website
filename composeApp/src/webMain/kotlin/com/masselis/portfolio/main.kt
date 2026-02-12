package com.masselis.portfolio

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.rememberNavController
import com.masselis.portfolio.navigation.Route
import kotlinx.browser.window
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import kotlinx.serialization.serializerOrNull
import org.w3c.dom.events.Event
import kotlin.js.ExperimentalWasmJsInterop

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalWasmJsInterop::class,
    InternalSerializationApi::class
)
internal fun main() {
    ComposeViewport {
        val navController = rememberNavController()
        App(
            navController = navController,
            onNavHostReady = {
                // Parse initial URL fragment
                val initHash = window.location.hash.substringAfter('#', "")
                Route.routes
                    .firstOrNull { it::class.serializer().descriptor.serialName == initHash }
                    ?.also { route ->
                        navController.navigate(route) {
                            popUpTo(Route.Home)
                        }
                    }

                var updatingFromPopState = false

                // Sync browser back/forward to NavController
                window.addEventListener("popstate", { _: Event ->
                    val currentHash = window.location.hash.substringAfter('#', "")
                    updatingFromPopState = true
                    Route.routes
                        .firstOrNull { it::class.serializer().descriptor.serialName == currentHash }
                        ?.also { route ->
                            navController.navigate(route) {
                                popUpTo(Route.Home)
                            }
                        }
                    updatingFromPopState = false
                })

                // Sync NavController changes to browser URL
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    if (updatingFromPopState) return@addOnDestinationChangedListener
                    val newHash = Route.classes
                        .firstOrNull { destination.hasRoute(it) }
                        ?.serializerOrNull()
                        ?.descriptor
                        ?.serialName
                        ?.let { "#$it" }
                    val basePath = window.location.pathname
                    window.history.pushState(null, "", basePath + newHash)
                }
            }
        )
    }
}