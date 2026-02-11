package com.masselis.portfolio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.navigation.Route
import com.masselis.portfolio.ui.theme.AccentGreen
import com.masselis.portfolio.ui.theme.DarkNavy
import com.masselis.portfolio.ui.theme.WindowSizeClass

@Composable
internal fun Footer(
    currentRoute: Route,
    windowSizeClass: WindowSizeClass,
    bottomPadding: Dp,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DarkNavy)
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Box {
            // Branding - left
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterStart),
            ) {
                Icon(
                    imageVector = Icons.Default.PhoneAndroid,
                    contentDescription = null,
                    tint = AccentGreen,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "RxVincent",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }

            // Nav links - right (hide on compact)
            if (windowSizeClass != WindowSizeClass.Compact) {
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FooterNavLink("Home", Route.Home, currentRoute, onNavigate)
                    FooterNavLink("About", Route.About, currentRoute, onNavigate)
                    FooterNavLink("Project", Route.Projects, currentRoute, onNavigate)
                    FooterNavLink("Contact", Route.Contact, currentRoute, onNavigate)
                }
            }
        }
        Spacer(Modifier.fillMaxWidth().height(bottomPadding))
    }
}

@Composable
private fun FooterNavLink(
    label: String,
    route: Route,
    currentRoute: Route,
    onNavigate: (Route) -> Unit,
) {
    val isActive = currentRoute === route
    Text(
        text = label,
        style = MaterialTheme.typography.labelMedium,
        color = if (isActive) AccentGreen else Color.White,
        textDecoration = if (isActive) TextDecoration.Underline else TextDecoration.None,
        modifier = Modifier.clickable { onNavigate(route) },
    )
}
