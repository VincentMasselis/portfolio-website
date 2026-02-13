package com.masselis.portfolio

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.rememberNavController
import com.masselis.portfolio.navigation.Route
import kotlinx.browser.window
import kotlinx.serialization.InternalSerializationApi
import org.w3c.dom.Location
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
                fun setRouteFromLocation(location: Location) {
                    location.hash
                        .substringAfter('#', "")
                        .let { hash -> Route.routes.firstOrNull { it.hash == hash } }
                        ?.also { route ->
                            navController.navigate(route) {
                                popUpTo(Route.Home)
                            }
                        }
                }

                fun setLocationFromDestination(destination: NavDestination) {
                    Route.routes
                        .firstOrNull { destination.hasRoute(it::class) }
                        ?.hash
                        ?.let { "#$it" }
                        ?.also { newHash ->
                            window.history.pushState(null, "", window.location.pathname + newHash)
                        }
                }

                // Parse initial URL fragment
                setRouteFromLocation(window.location)

                var updatingFromPopState = false
                // Sync browser back/forward to NavController
                window.addEventListener("popstate") { _ ->
                    updatingFromPopState = true
                    setRouteFromLocation(window.location)
                    updatingFromPopState = false
                }

                // Sync NavController changes to browser URL
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    if (updatingFromPopState) return@addOnDestinationChangedListener
                    setLocationFromDestination(destination)
                }
            }
        )
    }
}

private val Route.hash: String
    get() = when (this) {
        Route.Home -> ""
        Route.About -> "about"
        Route.Contact -> "contact"
        Route.Projects -> "projects"
    }

