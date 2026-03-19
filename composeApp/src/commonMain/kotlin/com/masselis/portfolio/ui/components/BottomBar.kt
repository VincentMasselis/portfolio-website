package com.masselis.portfolio.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.masselis.portfolio.ui.screens.About
import com.masselis.portfolio.ui.screens.Contact
import com.masselis.portfolio.ui.screens.Landing
import com.masselis.portfolio.ui.screens.Projects
import com.masselis.portfolio.ui.screens.Resume
import com.masselis.portfolio.ui.screens.Route
import org.jetbrains.compose.resources.stringResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.nav_about
import portfolio.composeapp.generated.resources.nav_contact
import portfolio.composeapp.generated.resources.nav_home
import portfolio.composeapp.generated.resources.nav_projects
import portfolio.composeapp.generated.resources.nav_resume

@Composable
internal fun BottomBar(
    currentRoute: Route,
    openRoute: (Route) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = modifier,
    ) {
        Item(Landing, currentRoute, openRoute, Icons.Default.Home, stringResource(Res.string.nav_home))
        Item(About, currentRoute, openRoute, Icons.Default.Face, stringResource(Res.string.nav_about))
        Item(Projects, currentRoute, openRoute, Icons.Default.Computer, stringResource(Res.string.nav_projects))
        Item(Contact, currentRoute, openRoute, Icons.Default.Mail, stringResource(Res.string.nav_contact))
        Item(Resume, currentRoute, openRoute, Icons.Default.Description, stringResource(Res.string.nav_resume))
    }
}


@Composable
private fun RowScope.Item(
    expectedRoute: Route,
    currentRoute: Route,
    openRoute: (Route) -> Unit,
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
) {
    NavigationBarItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
        },
        label = { Text(label) },
        selected = currentRoute == expectedRoute,
        onClick = { openRoute(expectedRoute) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.surfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier,
    )
}
