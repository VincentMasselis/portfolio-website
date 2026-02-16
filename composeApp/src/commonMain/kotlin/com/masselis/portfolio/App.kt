package com.masselis.portfolio

import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.masselis.portfolio.navigation.Route
import com.masselis.portfolio.ui.components.Footer
import com.masselis.portfolio.ui.components.NavigationDrawerContent
import com.masselis.portfolio.ui.components.TopNavBar
import com.masselis.portfolio.ui.screens.AboutScreen
import com.masselis.portfolio.ui.screens.ContactScreen
import com.masselis.portfolio.ui.screens.LandingScreen
import com.masselis.portfolio.ui.screens.ProjectsScreen
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.PortfolioTheme
import com.masselis.portfolio.ui.theme.WindowSizeClass
import com.masselis.portfolio.ui.theme.rememberWindowSizeClass
import kotlinx.coroutines.launch

internal val defaultStartRoute: Route = Route.Home

@Composable
public fun App(
    navController: NavHostController = rememberNavController(),
    startRoute: Route = defaultStartRoute,
    onNavHostReady: () -> Unit = {},
) {
    PortfolioTheme {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val windowSizeClass = rememberWindowSizeClass(maxWidth.value.toInt())
            CompositionLocalProvider(LocalWindowSizeClass provides windowSizeClass) {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = Route
                    .classes
                    .firstOrNull { clazz -> backStackEntry?.destination?.hasRoute(clazz) ?: false }
                    ?.let { clazz -> backStackEntry?.toRoute(clazz) }
                    ?: startRoute
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                val navigateTo: (Route) -> Unit = { route ->
                    scope.launch { drawerState.close() }
                    when (route) {
                        Route.Home -> navController.navigate(Route.Home) {
                            popUpTo(Route.Home) { inclusive = true }
                        }

                        Route.About -> navController.navigate(Route.About) {
                            popUpTo(Route.Home)
                        }

                        Route.Contact -> navController.navigate(Route.Contact) {
                            popUpTo(Route.Home)
                        }

                        Route.Projects -> navController.navigate(Route.Projects) {
                            popUpTo(Route.Home)
                        }
                    }
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
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                            ) {
                                NavHost(
                                    navController,
                                    startDestination = startRoute,
                                    enterTransition = { fadeIn(tween(300)) },
                                    exitTransition = { fadeOut(snap(delayMillis = 300)) },
                                    popEnterTransition = { fadeIn(tween(300)) },
                                    popExitTransition = { fadeOut(snap(delayMillis = 300)) },
                                    modifier = Modifier.consumeWindowInsets(WindowInsets.navigationBars)
                                ) {
                                    composable<Route.Home> {
                                        LandingScreen(navController, padding)
                                    }
                                    composable<Route.About> { AboutScreen(padding) }
                                    composable<Route.Projects> { ProjectsScreen(padding) }
                                    composable<Route.Contact> { ContactScreen(padding) }
                                }
                                Spacer(Modifier.weight(1f))
                                Footer(
                                    currentRoute = currentRoute,
                                    windowSizeClass = windowSizeClass,
                                    onNavigate = navigateTo,
                                )
                                LaunchedEffect(navController) {
                                    onNavHostReady()
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
