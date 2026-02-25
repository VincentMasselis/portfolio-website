package com.masselis.portfolio

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.masselis.portfolio.ui.screens.Resume
import com.masselis.portfolio.ui.screens.Route
import com.slack.circuit.foundation.navstack.rememberSaveableNavStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import kotlinx.browser.window
import kotlinx.coroutines.flow.onStart
import org.w3c.dom.PopStateEvent
import org.w3c.dom.events.Event
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsNumber
import kotlin.js.toInt
import kotlin.js.toJsNumber

@OptIn(ExperimentalComposeUiApi::class, ExperimentalWasmJsInterop::class)
internal fun main() {
    ComposeViewport {
        val startRoute = rememberSaveable { window.location.asRoute() ?: defaultStartRoute }
        val navStack = rememberSaveableNavStack(startRoute)
        val navigator = rememberCircuitNavigator(
            navStack = navStack,
            enableBackHandler = false,
            onRootPop = {},
        )

        App(navStack = navStack, navigator = navigator)

        // Sync browser back/forward to navigator
        DisposableEffect(Unit) {
            val listener: (Event) -> Unit = { event ->
                val targetIndex = ((event as PopStateEvent).state as JsNumber).toInt()
                val currentIndex = navStack
                    .snapshot()!!
                    .reversed()
                    .indexOfFirst { it.key == navStack.currentRecord!!.key }
                when (val delta = targetIndex - currentIndex) {
                    in Int.MIN_VALUE..-1 -> repeat(-delta) { navigator.backward() }
                    in 1..Int.MAX_VALUE -> repeat(delta) { navigator.forward() }
                    0 -> Unit // Nothing to do, history state and currentRecord are synchronized
                    else -> error("Unreachable case")
                }
            }
            window.addEventListener("popstate", listener)
            onDispose { window.removeEventListener("popstate", listener) }
        }

        // Sync navigator changes to browser URL
        LaunchedEffect(Unit) {
            snapshotFlow { navStack.currentRecord!! }
                .onStart {
                    // Saves the number 0 for the first history entry
                    window.history.replaceState(
                        0.toJsNumber(),
                        "",
                        window.location.pathname
                    )
                }
                .collect { record ->
                    val path = (record.screen as Route).path
                    if (path == Resume.path) {
                        // On Android and iOS, an embedded WebView is used to render the page but
                        // this kind of Composable doesn't exist in Compose for Web. Instead of
                        // displaying this route, a new tab is opened directly with the content from
                        // resume.js. By doing this, the web browser renders by itself the page.
                        window.open("/resume", "_blank")
                        navigator.backward()
                        return@collect
                    }
                    if (path != window.location.pathname) {
                        // Each time a screen is pushed through the navStack, the history is updated
                        // to match this new screen
                        window.history.pushState(
                            navStack
                                .snapshot()!!
                                .reversed()
                                .indexOfFirst { it.key == record.key }
                                .toJsNumber(),
                            "",
                            path
                        )
                    }
                }
        }
    }
}
