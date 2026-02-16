package com.masselis.portfolio

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.compose.rememberNavController
import com.masselis.portfolio.navigation.Route
import kotlinx.browser.window
import kotlin.js.ExperimentalWasmJsInterop

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalWasmJsInterop::class,
)
internal fun main() {
    ComposeViewport {
        val navController = rememberNavController()
        App(
            navController = navController,
            startRoute = window.location.asRoute() ?: defaultStartRoute,
            onNavHostReady = {
                var updatingFromPopState = false

                // Sync browser back/forward to NavController
                window.addEventListener("popstate") { _ ->
                    updatingFromPopState = true
                    navController.navigate(window.location.asRoute()!!) {
                        popUpTo(Route.Home)
                    }
                    updatingFromPopState = false
                }

                // Sync NavController changes to browser URL
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    if (updatingFromPopState) return@addOnDestinationChangedListener
                    window.history.pushState(null, "", destination.asRoute()!!.path)
                }
            }
        )
    }
}

