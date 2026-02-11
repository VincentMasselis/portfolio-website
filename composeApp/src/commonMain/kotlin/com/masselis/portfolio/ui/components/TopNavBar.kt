package com.masselis.portfolio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.navigation.Route
import com.masselis.portfolio.ui.theme.AccentGreen
import com.masselis.portfolio.ui.theme.DarkNavy
import com.masselis.portfolio.ui.theme.WindowSizeClass

@Composable
internal fun TopNavBar(
    currentRoute: Route,
    windowSizeClass: WindowSizeClass,
    onNavigate: (Route) -> Unit,
    onMenuClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkNavy)
            .padding(horizontal = 24.dp, vertical = 12.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterStart),
        ) {
            // Nav links - hamburger (mobile)
            if (windowSizeClass == WindowSizeClass.Compact) {
                IconButton(
                    onClick = onMenuClick,
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White,
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Default.PhoneAndroid,
                    contentDescription = null,
                    tint = AccentGreen,
                    modifier = Modifier.size(24.dp),
                )
            }
            // Branding - left
            Spacer(Modifier.width(8.dp))
            Text(
                text = "RxVincent",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }

        // Nav links - right (desktop)
        if (windowSizeClass != WindowSizeClass.Compact) {
            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                NavLink("Home", Route.Home, currentRoute, onNavigate)
                NavLink("About", Route.About, currentRoute, onNavigate)
                NavLink("Project", Route.Projects, currentRoute, onNavigate)
                NavLink("Contact", Route.Contact, currentRoute, onNavigate)
            }
        }
    }
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
        color = if (isActive) AccentGreen else Color.White,
        textDecoration = if (isActive) TextDecoration.Underline else TextDecoration.None,
        modifier = Modifier.clickable { onNavigate(route) },
    )
}
