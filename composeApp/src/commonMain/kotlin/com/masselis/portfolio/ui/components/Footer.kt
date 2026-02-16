package com.masselis.portfolio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.navigation.Route
import com.masselis.portfolio.ui.theme.WindowSizeClass
import org.jetbrains.compose.resources.painterResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.ic_bluesky
import portfolio.composeapp.generated.resources.ic_linkedin

@Composable
internal fun Footer(
    currentRoute: Route,
    windowSizeClass: WindowSizeClass,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .windowInsetsPadding(WindowInsets.navigationBars),
    ) {
        val uriHandler = LocalUriHandler.current
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Column {
                Text(
                    text = "Copyright© 2026 Vincent Masselis",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = Bold,
                    color = Color.White,
                )
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        uriHandler.openUri("https://github.com/VincentMasselis/portfolio-website")
                    }
                ) {
                    Text(
                        text = "Built in Kotlin Multiplatform with Compose",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = Bold,
                        textDecoration = TextDecoration.Underline,
                        color = Color.White,
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(start = 4.dp).size(12.dp),
                    )
                }
            }
            Spacer(Modifier.weight(1f))
            Row(horizontalArrangement = spacedBy(8.dp)) {
                IconButton(onClick = { uriHandler.openUri("https://linkedin.com/in/vincentmasselis") }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_linkedin),
                        contentDescription = "LinkedIn",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp),
                    )
                }
                IconButton(onClick = { uriHandler.openUri("https://bsky.app/profile/rxvincent.masselis.com") }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_bluesky),
                        contentDescription = "Bluesky",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        }
    }
}
