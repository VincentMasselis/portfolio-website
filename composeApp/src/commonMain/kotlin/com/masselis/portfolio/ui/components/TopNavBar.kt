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
import com.masselis.portfolio.ui.theme.AccentGreen
import com.masselis.portfolio.ui.theme.DarkNavy
import com.masselis.portfolio.ui.theme.WindowSizeClass

@Composable
fun TopNavBar(
    currentRoute: String,
    windowSizeClass: WindowSizeClass,
    onNavigate: (String) -> Unit,
    onMenuClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkNavy)
            .padding(horizontal = 24.dp, vertical = 12.dp),
    ) {
        // Branding - left
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterStart),
        ) {
            Icon(
                imageVector = Icons.Default.PhoneAndroid,
                contentDescription = null,
                tint = AccentGreen,
                modifier = Modifier.size(24.dp),
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "RxVincent",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }

        // Nav links - right (desktop) or hamburger (mobile)
        if (windowSizeClass == WindowSizeClass.Compact) {
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White,
                )
            }
        } else {
            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                NavLink("Home", "home", currentRoute, onNavigate)
                NavLink("About", "about", currentRoute, onNavigate)
                NavLink("Project", "projects", currentRoute, onNavigate)
                NavLink("Contact", "contact", currentRoute, onNavigate)
            }
        }
    }
}

@Composable
private fun NavLink(
    label: String,
    route: String,
    currentRoute: String,
    onNavigate: (String) -> Unit,
) {
    val isActive = currentRoute.startsWith(route)
    Text(
        text = label,
        style = MaterialTheme.typography.labelLarge,
        color = if (isActive) AccentGreen else Color.White,
        textDecoration = if (isActive) TextDecoration.Underline else TextDecoration.None,
        modifier = Modifier.clickable { onNavigate(route) },
    )
}
