package com.masselis.portfolio.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masselis.portfolio.data.PortfolioData
import com.masselis.portfolio.ui.components.Footer
import com.masselis.portfolio.ui.components.MyselfImage
import com.masselis.portfolio.ui.components.Section
import com.masselis.portfolio.ui.components.SkillBar
import com.masselis.portfolio.ui.components.TimelineItem
import com.masselis.portfolio.ui.components.copy
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass
import com.masselis.portfolio.ui.utils.CommonParcelize
import com.masselis.portfolio.ui.utils.LocalScaffoldPadding
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.screen.StaticScreen
import dev.zacsweers.metro.AppScope

@CommonParcelize
public data object About : Route, StaticScreen

@CircuitInject(About::class, AppScope::class)
@Composable
internal fun AboutScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        AboutHeroSection()
        SkillsSection()
        ExpertiseSection()
        CareerTimelineSection()
        Footer()
    }
}

@Composable
private fun AboutHeroSection() {
    val windowSizeClass = LocalWindowSizeClass.current
    Section(
        paddingValues = PaddingValues.Section.copy(top = LocalScaffoldPadding.current.calculateTopPadding()),
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        if (windowSizeClass == WindowSizeClass.Compact) {
            MyselfImage(
                Modifier.size(180.dp).clip(CircleShape).align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(24.dp))
            AboutHeroText()
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(40.dp),
            ) {
                MyselfImage(
                    modifier = Modifier.size(220.dp).clip(CircleShape)
                )
                AboutHeroText(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun AboutHeroText(
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = "À propos de\nVincent MASSELIS",
            style = MaterialTheme.typography.displaySmall,
            color = Color.White,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Senior avec 15+ ans d'expérience")
                }
                append(" je conçois des systèmes robustes fondés sur la ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Clean Architecture et MVVM/MVI")
                }
                append(". Spécialiste ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Android, iOS et Multiplatform")
                }
                append(", je construits vos projets en ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Kotlin")
                }
                append(", Kotlin Multiplatform (KMP) et Compose Multiplatform, afin unifier l'UX, l'UI et la qualité sur tous les systèmes.\n")
                append("Mon expérience porte au delà de Kotlin et Android, je possède aussi un solide bagage en ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("iOS")
                }
                append(" et une appétence naturelle à la découverte ainsi qu'à la remise en question des acquis. ")
                append("Ceci me permet de garder un ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("haut niveau d'exigence")
                }
                append(" envers moi même et les produits que je délivre.")
            },
            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 25.sp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
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