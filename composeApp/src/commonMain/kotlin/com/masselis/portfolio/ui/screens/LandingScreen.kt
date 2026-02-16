package com.masselis.portfolio.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.Phonelink
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.masselis.portfolio.ui.components.MyselfImage
import com.masselis.portfolio.ui.components.ProjectPreviewCard
import com.masselis.portfolio.ui.components.RepoCard
import com.masselis.portfolio.ui.components.Section
import com.masselis.portfolio.ui.components.copy
import com.masselis.portfolio.ui.components.label
import com.masselis.portfolio.ui.components.stats
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.cubeinstore
import portfolio.composeapp.generated.resources.kadiska

@Composable
internal fun LandingScreen(
    navController: NavController,
    scaffoldPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        HeroSection(
            scaffoldPadding = scaffoldPadding,
            showProjects = { navController.navigate(Route.Projects) }
        )
        ProjectsPreviewSection(onSeeMore = { navController.navigate(Route.Projects) })
        AboutPreviewSection()
        OSSSection()
    }
}

@Composable
private fun HeroSection(
    scaffoldPadding: PaddingValues,
    showProjects: () -> Unit,
) {
    Section(
        paddingValues = PaddingValues.Section.copy(top = scaffoldPadding.calculateTopPadding()),
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Spacer(Modifier.height(32.dp))
        Text(
            text = buildAnnotatedString {
                append("DÉVELOPPEUR ")
                append(
                    AnnotatedString(
                        "ANDROID\nKOTLIN",
                        SpanStyle(color = MaterialTheme.colorScheme.primary)
                    )
                )
                append(" MULTIPLATFORM\nSOFTWARE ")
                append(
                    AnnotatedString(
                        "ARCHITECT",
                        SpanStyle(color = MaterialTheme.colorScheme.primary)
                    )
                )
            },
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Architecte • Expert • Passionné",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun ProjectsPreviewSection(onSeeMore: () -> Unit) {
    val windowSizeClass = LocalWindowSizeClass.current
    Section(
        backgroundColor = Color.Transparent,
        paddingValues = PaddingValues.Section.copy(top = 0.dp),
        modifier = Modifier.layout { measurable, constraints ->
            val placeable = measurable.measure(constraints)
            val overlapPx = 32.dp.roundToPx()
            layout(placeable.width, placeable.height - overlapPx) {
                placeable.place(0, -overlapPx)
            }
        }
    ) {
        if (windowSizeClass == WindowSizeClass.Compact) {
            CubeInStore()
            Spacer(Modifier.height(16.dp))
            Kadiska()
            Spacer(Modifier.height(16.dp))
            SeeMore(onClick = onSeeMore)
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Box(Modifier.weight(1f)) { CubeInStore() }
                Box(Modifier.weight(1f)) { Kadiska() }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(0.3f)
                ) { SeeMore(onClick = onSeeMore) }
            }
        }
    }
}

@Composable
private fun CubeInStore(
    modifier: Modifier = Modifier
) {
    ProjectPreviewCard(
        "Decathlon CubeInStore",
        listOf(
            "100k utilisateurs mensuels",
            "+600k lignes de code",
            "+20 développeurs",
            "+50 pays"
        ),
        Res.drawable.cubeinstore
    )
}

@Composable
private fun Kadiska(
    modifier: Modifier = Modifier
) {
    ProjectPreviewCard(
        "Kadiska Android",
        listOf(
            "B2B",
            "Analyse réseau low-level",
            "Services en arrière-plan complexes",
            "Fort enjeux business"
        ),
        Res.drawable.kadiska
    )
}

@Composable
private fun SeeMore(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(24.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Voir tous mes projets",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun AboutPreviewSection() {
    val windowSizeClass = LocalWindowSizeClass.current
    Section(backgroundColor = Color.White) {
        if (windowSizeClass == WindowSizeClass.Compact) {
            MyselfImage(
                modifier = Modifier
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .size(180.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Spacer(Modifier.height(24.dp))
            AboutText()
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                MyselfImage(
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(16.dp),
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .size(220.dp)
                )
                Column(modifier = Modifier.weight(1f)) {
                    AboutText()
                }
            }
        }
    }
}

@Composable
private fun AboutText() {
    Text(
        text = "\u00C0 PROPOS",
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onBackground,
    )
    Spacer(Modifier.height(8.dp))
    Text(
        text = "Vincent Masselis,\nDéveloppeur Senior",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
    )
    Spacer(Modifier.height(12.dp))
    Text(
        text = "Je convertis le café en code depuis 2010",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
    )
    Spacer(Modifier.height(12.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Icon(
            Icons.Default.LocalCafe,
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Icon(
            Icons.Default.Code,
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Icon(
            Icons.Default.Phonelink,
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            tint = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun OSSSection() {
    Section(backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
        Text(
            text = "OPEN SOURCE",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(12.dp))
        Icon(
            Icons.Default.Code,
            contentDescription = null,
            modifier = Modifier.size(48.dp).align(Alignment.CenterHorizontally),
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(20.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            item {
                RepoCard(
                    "TPMS-advanced",
                    stats("TPMS-advanced")
                )
            }
            item {
                RepoCard(
                    "RxBluetoothKotlin",
                    stats("RxBluetoothKotlin")
                )
            }
            item {
                RepoCard(
                    "portfolio-website",
                    label("Le code du site que vous visitez actuellement")
                )
            }
        }
    }
}
