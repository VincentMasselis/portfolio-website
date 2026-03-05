package com.masselis.portfolio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.ui.components.Footer
import com.masselis.portfolio.ui.components.Section
import com.masselis.portfolio.ui.components.SectionMaxWidth
import com.masselis.portfolio.ui.components.copy
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass.Compact
import com.masselis.portfolio.ui.utils.CommonParcelize
import com.masselis.portfolio.ui.utils.LocalScaffoldPadding
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.screen.StaticScreen
import dev.zacsweers.metro.AppScope
import org.jetbrains.compose.resources.vectorResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.ic_bluesky
import portfolio.composeapp.generated.resources.ic_github
import portfolio.composeapp.generated.resources.ic_linkedin

@CommonParcelize
public data object Contact : Route, StaticScreen

@CircuitInject(Contact::class, AppScope::class)
@Composable
internal fun ContactScreen(
    modifier: Modifier = Modifier
) {
    val windowSizeClass = LocalWindowSizeClass.current
    val layoutDirection = LocalLayoutDirection.current
    val uriHandler = LocalUriHandler.current
    val leftPadding = PaddingValues.Section.calculateLeftPadding(layoutDirection)
    val rightPadding = PaddingValues.Section.calculateRightPadding(layoutDirection)
    val horizontalArrangement = Arrangement.spacedBy((leftPadding + rightPadding) / 2)
    val lazyGridState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(if (windowSizeClass == Compact) 1 else 2),
        state = lazyGridState,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        item(span = { GridItemSpan(if (windowSizeClass == Compact) 1 else 2) }) {
            ContactHeaderSection()
        }
        item {
            ContactGridCard(
                lane = 0,
                icon = vectorResource(Res.drawable.ic_linkedin),
                url = "linkedin.com/in/vincentmasselis",
                label = "Réseau professionel",
                onClick = { uriHandler.openUri("https://linkedin.com/in/vincentmasselis") },
                horizontalArrangement = horizontalArrangement,
                leftPadding = leftPadding,
                rightPadding = rightPadding,
            )
        }
        item {
            ContactGridCard(
                lane = 1,
                icon = vectorResource(Res.drawable.ic_github),
                url = "github.com/VincentMasselis",
                label = "Projets open-source",
                onClick = { uriHandler.openUri("https://github.com/VincentMasselis") },
                horizontalArrangement = horizontalArrangement,
                leftPadding = leftPadding,
                rightPadding = rightPadding,
            )
        }
        item {
            ContactGridCard(
                lane = 0,
                icon = vectorResource(Res.drawable.ic_bluesky),
                url = "bsky.app/profile/rxvincent.masselis.com",
                label = "Réseau social",
                onClick = { uriHandler.openUri("https://bsky.app/profile/rxvincent.masselis.com") },
                horizontalArrangement = horizontalArrangement,
                leftPadding = leftPadding,
                rightPadding = rightPadding,
            )
        }
        item {
            ContactGridCard(
                lane = 1,
                icon = Icons.Default.Mail,
                url = "vincent.masselis@gmail.com",
                label = "Contact mail",
                onClick = { uriHandler.openUri("mailto:vincent.masselis@gmail.com") },
                horizontalArrangement = horizontalArrangement,
                leftPadding = leftPadding,
                rightPadding = rightPadding,
            )
        }
        item(span = { GridItemSpan(if (windowSizeClass == Compact) 1 else 2) }) {
            Footer()
        }
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
private fun ContactGridCard(
    lane: Int,
    icon: ImageVector,
    url: String,
    label: String,
    onClick: () -> Unit,
    horizontalArrangement: Arrangement.HorizontalOrVertical,
    leftPadding: Dp,
    rightPadding: Dp,
    modifier: Modifier = Modifier,
) {
    val windowSizeClass = LocalWindowSizeClass.current
    val lane = if (windowSizeClass > Compact) lane else null
    Box(modifier) {
        Card(
            onClick = onClick,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .align(
                    when (lane) {
                        0 -> Alignment.CenterEnd
                        1 -> Alignment.CenterStart
                        null -> Alignment.Center
                        else -> error("Cannot handle more than 2 lanes")
                    }
                )
                .widthIn(max = (SectionMaxWidth - horizontalArrangement.spacing) / 2)
                .run {
                    when (lane) {
                        0 -> padding(start = leftPadding)
                        1 -> padding(end = rightPadding)
                        null -> padding(start = leftPadding, end = rightPadding)
                        else -> this
                    }
                }
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    modifier = Modifier.size(96.dp),
                    tint = MaterialTheme.colorScheme.primaryContainer,
                )
                Column {
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
        }
    }
}

@Composable
private fun LocationSection() {
    val windowSizeClass = LocalWindowSizeClass.current
    Column(
        modifier = Modifier.fillMaxWidth().background(Color.White),
    ) {
        if (windowSizeClass == WindowSizeClass.Compact) {
            Box(
                modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "\uD83D\uDDFA\uFE0F Lille",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                )
            }
            Spacer(Modifier.height(12.dp))
            LocationCard(Modifier.fillMaxWidth())
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Box(
                    modifier = Modifier.weight(1f).height(200.dp).clip(RoundedCornerShape(12.dp))
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
        modifier = modifier.border(
            1.dp,
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
            RoundedCornerShape(12.dp)
        ).padding(24.dp),
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