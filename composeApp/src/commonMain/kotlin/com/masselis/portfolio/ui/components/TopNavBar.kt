package com.masselis.portfolio.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopNavBar(
    currentRoute: Route,
    openRoute: (Route) -> Unit,
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
            if (LocalWindowSizeClass.current > Compact) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    NavLink("Home", Landing, currentRoute, openRoute)
                    NavLink("À propos", About, currentRoute, openRoute)
                    NavLink("Projets", Projects, currentRoute, openRoute)
                    NavLink("Contact", Contact, currentRoute, openRoute)
                    NavLink("CV", Resume, currentRoute, openRoute)
                }
                Spacer(Modifier.width(16.dp))
            }
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
