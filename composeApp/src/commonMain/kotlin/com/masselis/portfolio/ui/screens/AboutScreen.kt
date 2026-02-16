package com.masselis.portfolio.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsEsports
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
import com.masselis.portfolio.ui.components.Section
import com.masselis.portfolio.ui.components.SkillBar
import com.masselis.portfolio.ui.components.TimelineItem
import com.masselis.portfolio.ui.components.copy
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass

@Composable
internal fun AboutScreen(
    scaffoldPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AboutHeroSection(scaffoldPadding)
        SkillsSection()
        ExpertiseSection()
        CareerTimelineSection()
    }
}

@Composable
private fun AboutHeroSection(
    scaffoldPadding: PaddingValues,
) {
    val windowSizeClass = LocalWindowSizeClass.current
    Section(
        paddingValues = PaddingValues.Section.copy(top = scaffoldPadding.calculateTopPadding()),
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        if (windowSizeClass == WindowSizeClass.Compact) {
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .align(Alignment.CenterHorizontally),
            )
            Spacer(Modifier.height(24.dp))
            AboutHeroText()
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(40.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.3f)),
                )
                Column(modifier = Modifier.weight(1f)) {
                    AboutHeroText()
                }
            }
        }
    }
}

@Composable
private fun AboutHeroText() {
    Text(
        text = "\u00C0 PROPOS DE\nVINCENT MASSELIS",
        style = MaterialTheme.typography.displaySmall,
        color = Color.White,
    )
    Spacer(Modifier.height(16.dp))
    Text(
        text = "Vincent Masselis est un d\u00E9veloppeur Android Senior avec plus de 10 ans d'exp\u00E9rience, " +
                "sp\u00E9cialis\u00E9 dans l'architecture robuste (Clean Architecture, MVVM), la programmation " +
                "r\u00E9active (RxJava, Kotlin Flow) et les interfaces modernes (Jetpack Compose). Il " +
                "collabore avec des startups et des grandes entreprises pour cr\u00E9er des applications " +
                "performantes et \u00E9volutives.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
    )
}

@Composable
private fun SkillsSection() {
    val windowSizeClass = LocalWindowSizeClass.current
    Section(backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
        Text(
            text = "COMP\u00C9TENCES TECHNIQUES",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(24.dp))
        if (windowSizeClass == WindowSizeClass.Compact) {
            PortfolioData.skills.forEach { skill ->
                SkillBar(label = skill.name, progress = skill.progress)
            }
        } else {
            val leftColumn = PortfolioData.skills.filterIndexed { i, _ -> i % 2 == 0 }
            val rightColumn = PortfolioData.skills.filterIndexed { i, _ -> i % 2 == 1 }
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    leftColumn.forEach { skill ->
                        SkillBar(label = skill.name, progress = skill.progress)
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    rightColumn.forEach { skill ->
                        SkillBar(label = skill.name, progress = skill.progress)
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpertiseSection() {
    Section(backgroundColor = Color.White) {
        Text(
            text = "EXPERTISE & PASSIONS",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(24.dp))
        ExpertiseItem(Icons.Default.Coffee, "Caf\u00E9 et code propre")
        ExpertiseItem(Icons.Default.SportsEsports, "Gaming et nouvelles technologies")
        ExpertiseItem(Icons.Default.Settings, "Architecture logicielle")
    }
}

@Composable
private fun ExpertiseItem(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(28.dp),
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun CareerTimelineSection() {
    Section(backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
        Text(
            text = "POINTS FORTS DE CARRI\u00C8RE",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Forsistance in freelance and our customer services.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
        )
        Spacer(Modifier.height(24.dp))
        PortfolioData.timelineEntries.forEachIndexed { index, entry ->
            TimelineItem(
                dateRange = entry.dateRange,
                title = entry.title,
                description = entry.description,
                isLast = index == PortfolioData.timelineEntries.lastIndex,
            )
        }
    }
}
