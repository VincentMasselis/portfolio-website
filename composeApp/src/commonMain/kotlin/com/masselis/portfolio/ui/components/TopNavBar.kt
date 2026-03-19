package com.masselis.portfolio.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.ui.screens.About
import com.masselis.portfolio.ui.screens.Contact
import com.masselis.portfolio.ui.screens.Landing
import com.masselis.portfolio.ui.screens.Projects
import com.masselis.portfolio.ui.screens.Resume
import com.masselis.portfolio.ui.screens.Route
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass.Compact
import org.jetbrains.compose.resources.stringResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.nav_about
import portfolio.composeapp.generated.resources.nav_contact
import portfolio.composeapp.generated.resources.nav_home
import portfolio.composeapp.generated.resources.nav_projects
import portfolio.composeapp.generated.resources.nav_resume

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopNavBar(
    currentRoute: Route,
    openRoute: (Route) -> Unit,
    additionalActions: @Composable RowScope.() -> Unit = {},
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        navigationIcon = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.PhoneAndroid,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp),
                )
            }
        },
        title = {
            Text(
                text = "RxVincent",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        },
        actions = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (LocalWindowSizeClass.current > Compact) {
                    NavLink(
                        stringResource(Res.string.nav_home),
                        Landing,
                        currentRoute,
                        openRoute
                    )
                    NavLink(
                        stringResource(Res.string.nav_about),
                        About,
                        currentRoute,
                        openRoute
                    )
                    NavLink(
                        stringResource(Res.string.nav_projects),
                        Projects,
                        currentRoute,
                        openRoute
                    )
                    NavLink(
                        stringResource(Res.string.nav_contact),
                        Contact,
                        currentRoute,
                        openRoute
                    )
                    NavLink(
                        stringResource(Res.string.nav_resume),
                        Resume,
                        currentRoute,
                        openRoute
                    )
                }
                additionalActions()
            }
            Spacer(Modifier.width(16.dp))
        },
    )
}

@Composable
private fun NavLink(
    label: String,
    route: Route,
    currentRoute: Route,
    onNavigate: (Route) -> Unit,
) {
    val isActive = currentRoute === route
    Text(
        text = label,
        style = MaterialTheme.typography.labelLarge,
        color = if (isActive) MaterialTheme.colorScheme.primary else Color.White,
        textDecoration = if (isActive) TextDecoration.Underline else TextDecoration.None,
        modifier = Modifier.clickable { onNavigate(route) },
    )
}
