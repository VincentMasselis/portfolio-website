package com.masselis.portfolio

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.compose.rememberNavController
import com.masselis.portfolio.navigation.About
import com.masselis.portfolio.navigation.Contact
import com.masselis.portfolio.navigation.Projects
import kotlinx.browser.window

@OptIn(ExperimentalComposeUiApi::class, kotlin.js.ExperimentalWasmJsInterop::class)
fun main() {
    ComposeViewport {
        val navController = rememberNavController()
        // Parse initial URL fragment
        LaunchedEffect(navController) {
            val initRoute = window.location.hash.substringAfter('#', "")
            when {
                initRoute.startsWith("about") -> navController.navigate(About)
                initRoute.startsWith("projects") -> navController.navigate(Projects)
                initRoute.startsWith("contact") -> navController.navigate(Contact)
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
        App(navController)
    }
}
