package com.masselis.portfolio

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
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
        val navigator = rememberCircuitNavigator(navStack) {}

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
                    0 -> Unit // Nothing to do
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
                    window.history.replaceState(
                        0.toJsNumber(),
                        "",
                        window.location.pathname
                    )
                }
                .collect { record ->
                    val path = (record.screen as Route).path
                    if (path != window.location.pathname) {
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
