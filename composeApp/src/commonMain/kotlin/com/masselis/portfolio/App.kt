package com.masselis.portfolio

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.masselis.portfolio.di.MainGraph
import com.masselis.portfolio.ui.components.Footer
import com.masselis.portfolio.ui.components.NavigationDrawerContent
import com.masselis.portfolio.ui.components.TopNavBar
import com.masselis.portfolio.ui.screens.Landing
import com.masselis.portfolio.ui.screens.Route
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.PortfolioTheme
import com.masselis.portfolio.ui.theme.WindowSizeClass
import com.masselis.portfolio.ui.theme.rememberWindowSizeClass
import com.masselis.portfolio.utils.LocalScaffoldPadding
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.navstack.rememberSaveableNavStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.navigation.NavStack
import kotlinx.coroutines.launch

internal val defaultStartRoute: Route = Landing

@Composable
public fun App(
    navStack: NavStack<out NavStack.Record> = rememberSaveableNavStack(defaultStartRoute),
    navigator: Navigator = rememberCircuitNavigator(navStack) {},
) {
    PortfolioTheme {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val windowSizeClass = rememberWindowSizeClass(maxWidth.value.toInt())
            CircuitCompositionLocals(MainGraph.circuit) {
                CompositionLocalProvider(LocalWindowSizeClass provides windowSizeClass) {
                    val currentRoute = navStack.currentRecord?.screen as? Route ?: defaultStartRoute
                    val drawerState = rememberDrawerState(DrawerValue.Closed)
                    val scope = rememberCoroutineScope()

                    val navigateTo: (Route) -> Unit = { route ->
                        scope.launch { drawerState.close() }
                        navigator.goTo(route)
                    }

                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        gesturesEnabled = windowSizeClass == WindowSizeClass.Compact,
                        drawerContent = {
                            ModalDrawerSheet {
                                NavigationDrawerContent(
                                    currentRoute = currentRoute,
                                    onNavigate = navigateTo,
                                )
                            }
                        },
                    ) {
                        Scaffold(
                            topBar = {
                                TopNavBar(
                                    currentRoute = currentRoute,
                                    windowSizeClass = windowSizeClass,
                                    onNavigate = navigateTo,
                                    onMenuClick = { scope.launch { drawerState.open() } },
                                )
                            },
                            content = { padding ->
                                CompositionLocalProvider(LocalScaffoldPadding provides padding) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .verticalScroll(rememberScrollState()),
                                    ) {
                                        NavigableCircuitContent(
                                            navigator = navigator,
                                            navStack = navStack,
                                            modifier = Modifier.consumeWindowInsets(WindowInsets.navigationBars)
                                        )
                                        Spacer(Modifier.weight(1f))
                                        Footer(
                                            currentRoute = currentRoute,
                                            windowSizeClass = windowSizeClass,
                                            onNavigate = navigateTo,
                                        )
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
