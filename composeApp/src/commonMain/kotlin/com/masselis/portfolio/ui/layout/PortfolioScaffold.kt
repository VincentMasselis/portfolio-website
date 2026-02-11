package com.masselis.portfolio.ui.layout

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.masselis.portfolio.navigation.About
import com.masselis.portfolio.navigation.Contact
import com.masselis.portfolio.navigation.Home
import com.masselis.portfolio.navigation.Projects
import com.masselis.portfolio.ui.components.Footer
import com.masselis.portfolio.ui.components.NavigationDrawerContent
import com.masselis.portfolio.ui.components.TopNavBar
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass
import com.masselis.portfolio.ui.theme.rememberWindowSizeClass
import kotlinx.coroutines.launch

@Composable
fun PortfolioScaffold(
    navController: NavController,
    content: @Composable ColumnScope.() -> Unit,
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val windowSizeClass = rememberWindowSizeClass(maxWidth.value.toInt())
        CompositionLocalProvider(LocalWindowSizeClass provides windowSizeClass) {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route.orEmpty()
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            val navigateTo: (String) -> Unit = { route ->
                scope.launch { drawerState.close() }
                when (route) {
                    "home" -> navController.navigate(Home) {
                        popUpTo(Home) { inclusive = true }
                    }
                    "about" -> navController.navigate(About) {
                        popUpTo(Home)
                    }
                    "projects" -> navController.navigate(Projects) {
                        popUpTo(Home)
                    }
                    "contact" -> navController.navigate(Contact) {
                        popUpTo(Home)
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
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                        ) {
                            content()
                            Footer(
                                currentRoute = currentRoute,
                                windowSizeClass = windowSizeClass,
                                onNavigate = navigateTo,
                            )
                        }
                    }
                )
            }
        }
    }
}
