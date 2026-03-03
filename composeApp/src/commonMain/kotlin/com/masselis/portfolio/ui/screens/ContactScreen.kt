package com.masselis.portfolio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.masselis.portfolio.data.PortfolioData
import com.masselis.portfolio.ui.components.Footer
import com.masselis.portfolio.ui.components.Section
import com.masselis.portfolio.ui.components.VerticalScrollbar
import com.masselis.portfolio.ui.components.copy
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass
import com.masselis.portfolio.ui.utils.CommonParcelize
import com.masselis.portfolio.ui.utils.LocalScaffoldPadding
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.screen.StaticScreen
import dev.zacsweers.metro.AppScope

@CommonParcelize
public data object Contact : Route, StaticScreen

@CircuitInject(Contact::class, AppScope::class)
@Composable
internal fun ContactScreen(
    modifier: Modifier = Modifier
) {

    val scrollState = rememberScrollState()
    Box(modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            ContactHeaderSection()
            ContactGridSection()
            LocationSection()
            Footer()
        }
        VerticalScrollbar(
            scrollState = scrollState,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(),
        )
    }
}

@Composable
private fun ContactHeaderSection() {
    Section(
        paddingValues = PaddingValues.Section.copy(top = LocalScaffoldPadding.current.calculateTopPadding()),
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
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
    Section(
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    ContactGridCard(
                        icons[0],
                        contacts[0].url,
                        contacts[0].label,
                        Modifier.fillMaxWidth()
                    )
                    ContactGridCard(
                        icons[2],
                        contacts[2].url,
                        contacts[2].label,
                        Modifier.fillMaxWidth()
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    ContactGridCard(
                        icons[1],
                        contacts[1].url,
                        contacts[1].label,
                        Modifier.fillMaxWidth()
                    )
                    ContactGridCard(
                        icons[3],
                        contacts[3].url,
                        contacts[3].label,
                        Modifier.fillMaxWidth()
                    )
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
            .border(
                1.dp,
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                RoundedCornerShape(12.dp)
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primaryContainer,
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        if (windowSizeClass == WindowSizeClass.Compact) {
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
            .border(
                1.dp,
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                RoundedCornerShape(12.dp)
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primaryContainer,
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