package com.masselis.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.masselis.portfolio.navigation.About
import com.masselis.portfolio.navigation.Contact
import com.masselis.portfolio.navigation.Home
import com.masselis.portfolio.navigation.Projects
import com.masselis.portfolio.ui.screens.AboutScreen
import com.masselis.portfolio.ui.screens.ContactScreen
import com.masselis.portfolio.ui.screens.LandingScreen
import com.masselis.portfolio.ui.screens.ProjectsScreen
import com.masselis.portfolio.ui.theme.PortfolioTheme

@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    PortfolioTheme {
        NavHost(navController, startDestination = Home) {
            composable<Home> { LandingScreen(navController) }
            composable<About> { AboutScreen(navController) }
            composable<Projects> { ProjectsScreen(navController) }
            composable<Contact> { ContactScreen(navController) }
        }
    }
}
