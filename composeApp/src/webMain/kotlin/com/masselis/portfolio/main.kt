package com.masselis.portfolio

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.compose.rememberNavController
import com.masselis.portfolio.navigation.Route
import kotlinx.browser.window

@OptIn(ExperimentalComposeUiApi::class, kotlin.js.ExperimentalWasmJsInterop::class)
internal fun main() {
    ComposeViewport {
        val navController = rememberNavController()
        App(
            navController = navController,
            onNavHostReady = {
                // Parse initial URL fragment
                val initRoute = window.location.hash.substringAfter('#', "")
                when {
                    initRoute.startsWith("about") -> navController.navigate(Route.About)
                    initRoute.startsWith("projects") -> navController.navigate(Route.Projects)
                    initRoute.startsWith("contact") -> navController.navigate(Route.Contact)
                }
                // Update browser URL when navigation changes
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    val route = destination.route.orEmpty()
                    val hash = when {
                        route.startsWith("home") -> ""
                        route.startsWith("about") -> "#about"
                        route.startsWith("projects") -> "#projects"
                        route.startsWith("contact") -> "#contact"
                        else -> ""
                    }
                    val basePath = window.location.pathname
                    window.history.replaceState(null, "", basePath + hash)
                }
            }
        )
    }
}
