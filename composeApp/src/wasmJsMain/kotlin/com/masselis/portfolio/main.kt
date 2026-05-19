package com.masselis.portfolio

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import com.masselis.portfolio.ui.screens.Resume
import com.masselis.portfolio.ui.screens.Route
import com.masselis.portfolio.ui.utils.OverrideLocale
import com.masselis.portfolio.ui.utils.displayedLocale
import com.slack.circuit.foundation.navstack.rememberSaveableNavStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import kotlinx.browser.window
import kotlinx.coroutines.flow.onStart
import org.jetbrains.compose.resources.Font
import org.w3c.dom.PopStateEvent
import org.w3c.dom.events.Event
import portfolio.composeapp.generated.resources.NotoColorEmoji
import portfolio.composeapp.generated.resources.Res

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
        OverrideLocale.CompositionProvider {
            App(
                navStack = navStack,
                navigator = navigator,
                additionalActions = {
                    SingleChoiceSegmentedButtonRow {
                        SegmentedButton(
                            selected = Locale.displayedLocale == fr,
                            onClick = { OverrideLocale.set(fr) },
                            shape = RoundedCornerShape(4.dp),
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = .2f),
                                activeBorderColor = MaterialTheme.colorScheme.primary,
                            ),
                            contentPadding = PaddingValues(horizontal = 3.dp, vertical = 0.dp),
                            label = {
                                Text(
                                    "🇫🇷",
                                    fontFamily = FontFamily(Font(Res.font.NotoColorEmoji))
                                )
                            },
                        )
                        SegmentedButton(
                            selected = Locale.displayedLocale == en,
                            onClick = { OverrideLocale.set(en) },
                            shape = RoundedCornerShape(4.dp),
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = .2f),
                                activeBorderColor = MaterialTheme.colorScheme.primary,
                            ),
                            contentPadding = PaddingValues(horizontal = 3.dp, vertical = 0.dp),
                            label = {
                                Text(
                                    "🇬🇧",
                                    fontFamily = FontFamily(Font(Res.font.NotoColorEmoji))
                                )
                            },
                        )
                    }
                }
            )
        }

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
                        window.open("/resume.pdf", "_blank")
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

private val en = Locale("en")
private val fr = Locale("fr")