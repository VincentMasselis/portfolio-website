package com.masselis.portfolio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.masselis.portfolio.data.PortfolioData
import com.masselis.portfolio.ui.components.Section
import com.masselis.portfolio.ui.layout.PortfolioScaffold
import com.masselis.portfolio.ui.theme.DarkNavy
import com.masselis.portfolio.ui.theme.LightGray
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass

@Composable
fun ContactScreen(navController: NavController) {
    PortfolioScaffold(navController) {
        ContactHeaderSection()
        ContactGridSection()
        LocationSection()
    }
}

@Composable
private fun ContactHeaderSection() {
    Section(backgroundColor = DarkNavy) {
        Text(
            text = "Restons en Contact",
            style = MaterialTheme.typography.displaySmall,
            color = Color.White,
        )
    }
}

@Composable
private fun ContactGridSection() {
    val windowSizeClass = LocalWindowSizeClass.current
    Section(backgroundColor = LightGray) {
        val contacts = PortfolioData.contacts
        val icons = listOf(
            Icons.Default.Person,    // LinkedIn
            Icons.Default.Star,      // GitHub
            Icons.Default.Share,     // Bluesky
            Icons.Default.Email,     // Email
        )

        if (windowSizeClass == WindowSizeClass.Compact) {
            contacts.forEachIndexed { index, contact ->
                ContactGridCard(
                    icon = icons[index],
                    url = contact.url,
                    label = contact.label,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(16.dp))
            }
        } else {
            // 2x2 grid
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    ContactGridCard(icons[0], contacts[0].url, contacts[0].label, Modifier.fillMaxWidth())
                    ContactGridCard(icons[2], contacts[2].url, contacts[2].label, Modifier.fillMaxWidth())
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    ContactGridCard(icons[1], contacts[1].url, contacts[1].label, Modifier.fillMaxWidth())
                    ContactGridCard(icons[3], contacts[3].url, contacts[3].label, Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
private fun ContactGridCard(
    icon: ImageVector,
    url: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .border(1.dp, DarkNavy.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(64.dp),
            tint = DarkNavy,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = url,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
        )
    }
}

@Composable
private fun LocationSection() {
    val windowSizeClass = LocalWindowSizeClass.current
    Section(backgroundColor = Color.White) {
        if (windowSizeClass == WindowSizeClass.Compact) {
            // Map placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "\uD83D\uDDFA\uFE0F Lille",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                )
            }
            Spacer(Modifier.height(16.dp))
            LocationCard(Modifier.fillMaxWidth())
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                // Map placeholder
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Gray.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "\uD83D\uDDFA\uFE0F Lille",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    )
                }
                LocationCard(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun LocationCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .border(1.dp, DarkNavy.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            modifier = Modifier.size(64.dp),
            tint = DarkNavy,
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "Lille, France",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Localisation",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
        )
    }
}
