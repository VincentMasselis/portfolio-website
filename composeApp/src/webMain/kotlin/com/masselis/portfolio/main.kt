package com.masselis.portfolio

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.window

@OptIn(ExperimentalComposeUiApi::class, kotlin.js.ExperimentalWasmJsInterop::class)
fun main() {
    ComposeViewport {
        App(
            onNavHostReady = { navController ->
                // Parse initial URL fragment
                val initRoute = window.location.hash.substringAfter('#', "")
                when {
                    initRoute.startsWith("about") -> navController.navigate(
                        com.masselis.portfolio.navigation.About
                    )
                    initRoute.startsWith("projects") -> navController.navigate(
                        com.masselis.portfolio.navigation.Projects
                    )
                    initRoute.startsWith("contact") -> navController.navigate(
                        com.masselis.portfolio.navigation.Contact
                    )
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
            },
        )
    }
}
