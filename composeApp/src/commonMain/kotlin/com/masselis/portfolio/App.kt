package com.masselis.portfolio

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.masselis.portfolio.data.PortfolioTheme
import com.masselis.portfolio.di.MainGraph
import com.masselis.portfolio.ui.components.BottomBar
import com.masselis.portfolio.ui.components.TopNavBar
import com.masselis.portfolio.ui.screens.Landing
import com.masselis.portfolio.ui.screens.Route
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass.Compact
import com.masselis.portfolio.ui.theme.rememberWindowSizeClass
import com.masselis.portfolio.ui.utils.LocalScaffoldPadding
import com.masselis.portfolio.ui.utils.isStackableMainNav
import com.slack.circuit.backstack.NavDecoration
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.navstack.rememberSaveableNavStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.InternalCircuitApi
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.navigation.NavArgument
import com.slack.circuit.runtime.navigation.NavStack
import com.slack.circuit.runtime.navigation.NavStackList

internal val defaultStartRoute: Route = Landing

@OptIn(InternalCircuitApi::class, ExperimentalMaterial3Api::class)
@Composable
public fun App(
    navStack: NavStack<out NavStack.Record> = rememberSaveableNavStack(defaultStartRoute),
    navigator: Navigator = rememberCircuitNavigator(
        navStack = navStack,
        enableBackHandler = true,
        onRootPop = {}
    ),
    additionalActions: @Composable RowScope.() -> Unit = {},
) {
    PortfolioTheme {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val windowSizeClass = rememberWindowSizeClass(maxWidth.value.toInt())
            CircuitCompositionLocals(MainGraph.circuit) {
                CompositionLocalProvider(LocalWindowSizeClass provides windowSizeClass) {
                    val currentRoute = navStack.currentRecord!!.screen as Route
                    val openRoute: (Route) -> Unit = { route ->
                        if (isStackableMainNav().not()) {
                            navigator.resetRoot(Landing)
                        }
                        navigator.goTo(route)
                    }

                    // Keyed on the route so a screen never inherits the previous screen's scroll
                    // offset and gets recomputed each time the screen changes
                    // Filled arguments are the default values for `rememberTopAppBarState`
                    val topBarState = remember(currentRoute) { TopAppBarState(-Float.MIN_VALUE, 0f, 0f) }
                    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)
                    Scaffold(
                        topBar = {
                            TopNavBar(
                                currentRoute = currentRoute,
                                scrollBehavior = topBarScrollBehavior,
                                openRoute = openRoute,
                                containerColor = if (currentRoute == Landing) Color.Transparent
                                else MaterialTheme.colorScheme.surfaceContainerHigh,
                                additionalActions = additionalActions,
                            )
                        },
                        content = { padding ->
                            CompositionLocalProvider(LocalScaffoldPadding provides padding) {
                                NavigableCircuitContent(
                                    navigator = navigator,
                                    navStack = navStack,
                                    modifier = Modifier.run {
                                        if (windowSizeClass == Compact) {
                                            padding(bottom = padding.calculateBottomPadding())
                                        } else {
                                            consumeWindowInsets(WindowInsets.navigationBars)
                                        }
                                    },
                                    decoration = FadeNavDecoration(),
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
                        bottomBar = {
                            if (windowSizeClass == Compact) {
                                BottomBar(
                                    currentRoute = currentRoute,
                                    openRoute = openRoute
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

private class FadeNavDecoration : NavDecoration {
    @Composable
    override fun <T : NavArgument> DecoratedContent(
        args: NavStackList<T>,
        modifier: Modifier,
        content: @Composable (T) -> Unit,
    ) {
        AnimatedContent(
            targetState = args.active,
            modifier = modifier,
            transitionSpec = {
                fadeIn(tween(300)) togetherWith fadeOut(snap(delayMillis = 300))
            },
        ) { record ->
            content(record)
        }
    }
}
