package com.masselis.portfolio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.masselis.portfolio.data.PortfolioData
import com.masselis.portfolio.navigation.Route
import com.masselis.portfolio.ui.components.RepoCard
import com.masselis.portfolio.ui.components.Section
import com.masselis.portfolio.ui.theme.AccentGreen
import com.masselis.portfolio.ui.theme.DarkNavy
import com.masselis.portfolio.ui.theme.LightGray
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.TextWhite
import com.masselis.portfolio.ui.theme.WindowSizeClass

@Composable
fun LandingScreen(navController: NavController) {
    Column {
        HeroSection(onCtaClick = { navController.navigate(Route.Projects) })
        ProjectsPreviewSection()
        AboutPreviewSection()
        GitHubSection()
    }
}

@Composable
private fun HeroSection(onCtaClick: () -> Unit) {
    Section(backgroundColor = DarkNavy) {
        Spacer(Modifier.height(32.dp))
        Text(
            text = PortfolioData.heroTitle,
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = PortfolioData.heroSubtitle,
            style = MaterialTheme.typography.bodyLarge,
            color = TextWhite,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .border(1.dp, AccentGreen, RoundedCornerShape(24.dp))
                .clickable(onClick = onCtaClick)
                .padding(horizontal = 24.dp, vertical = 12.dp),
        ) {
            Text(
                text = "Voir mes r\u00E9alisations",
                style = MaterialTheme.typography.labelLarge,
                color = AccentGreen,
            )
        }
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun ProjectsPreviewSection() {
    val windowSizeClass = LocalWindowSizeClass.current
    Section(backgroundColor = LightGray) {
        val projects = PortfolioData.projects.take(2)
        if (windowSizeClass == WindowSizeClass.Compact) {
            projects.forEach { project ->
                ProjectPreviewCard(project.title, project.bulletPoints)
                Spacer(Modifier.height(16.dp))
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                projects.forEach { project ->
                    Box(modifier = Modifier.weight(1f)) {
                        ProjectPreviewCard(project.title, project.bulletPoints)
                    }
                }
            }
        }
    }
}

@Composable
private fun ProjectPreviewCard(title: String, bulletPoints: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White),
    ) {
        // Green accent header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(AccentGreen.copy(alpha = 0.85f)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "\uD83D\uDCF1",
                style = MaterialTheme.typography.displayLarge,
            )
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(Modifier.height(8.dp))
            bulletPoints.forEach { point ->
                Text(
                    text = "- $point",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(vertical = 2.dp),
                )
            }
        }
    }
}

@Composable
private fun AboutPreviewSection() {
    Section(backgroundColor = Color.White) {
        val windowSizeClass = LocalWindowSizeClass.current
        if (windowSizeClass == WindowSizeClass.Compact) {
            // Stacked layout
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .align(Alignment.CenterHorizontally),
            )
            Spacer(Modifier.height(24.dp))
            AboutText()
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Gray.copy(alpha = 0.3f)),
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
        text = "Vincent Masselis,\nSenior Android Dev.",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
    )
    Spacer(Modifier.height(12.dp))
    Text(
        text = "Passionn\u00E9 par la Clean Architecture et le code testable.",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
    )
    Spacer(Modifier.height(12.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(text = "\u2615", style = MaterialTheme.typography.displaySmall)
        Text(text = "\uD83C\uDFAE", style = MaterialTheme.typography.displaySmall)
    }
}

@Composable
private fun GitHubSection() {
    Section(backgroundColor = LightGray) {
        Text(
            text = "GITHUB & OPEN SOURCE",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(8.dp))
        // GitHub icon placeholder
        Text(
            text = "\uD83D\uDC31",
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(24.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(PortfolioData.repos) { repo ->
                RepoCard(
                    repoName = repo.name,
                    stars = repo.stars,
                    language = repo.language,
                )
            }
        }
    }
}
