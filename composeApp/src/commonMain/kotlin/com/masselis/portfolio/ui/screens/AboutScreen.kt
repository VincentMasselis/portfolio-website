package com.masselis.portfolio.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsMotorsports
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masselis.portfolio.data.PortfolioData
import com.masselis.portfolio.data.Tag
import com.masselis.portfolio.ui.components.Footer
import com.masselis.portfolio.ui.components.MyselfImage
import com.masselis.portfolio.ui.components.PortfolioMarkdown
import com.masselis.portfolio.ui.components.Section
import com.masselis.portfolio.ui.components.SkillBar
import com.masselis.portfolio.ui.components.TimelineItem
import com.masselis.portfolio.ui.components.VerticalScrollbar
import com.masselis.portfolio.ui.components.copy
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass.Compact
import com.masselis.portfolio.ui.utils.CommonParcelize
import com.masselis.portfolio.ui.utils.LocalScaffoldPadding
import com.masselis.portfolio.ui.utils.string
import com.mikepenz.markdown.m3.markdownColor
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.screen.StaticScreen
import dev.zacsweers.metro.AppScope
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.YearMonth
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.yearsUntil
import org.jetbrains.compose.resources.stringResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.about_expertise_coffee
import portfolio.composeapp.generated.resources.about_expertise_connected_home
import portfolio.composeapp.generated.resources.about_expertise_motorsports
import portfolio.composeapp.generated.resources.about_expertise_software_architecture
import portfolio.composeapp.generated.resources.about_expertise_title
import portfolio.composeapp.generated.resources.about_filter_all
import portfolio.composeapp.generated.resources.about_hero_body_md
import portfolio.composeapp.generated.resources.about_hero_title
import portfolio.composeapp.generated.resources.about_skills_title
import portfolio.composeapp.generated.resources.about_timeline_subtitle
import portfolio.composeapp.generated.resources.about_timeline_title
import kotlin.math.absoluteValue
import kotlin.time.Clock

@CommonParcelize
public data object About : Route, StaticScreen

@CircuitInject(About::class, AppScope::class)
@Composable
internal fun AboutScreen(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Box(modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            AboutHeroSection()
            SkillsSection()
            ExpertiseSection()
            CareerTimelineSection()
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
private fun AboutHeroSection() {
    val windowSizeClass = LocalWindowSizeClass.current
    Section(
        paddingValues = PaddingValues.Section.copy(top = LocalScaffoldPadding.current.calculateTopPadding()),
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        if (windowSizeClass == Compact) {
            MyselfImage(
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(24.dp))
            AboutHeroText()
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(40.dp),
            ) {
                MyselfImage(
                    modifier = Modifier.size(220.dp)
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
        SelectionContainer {
            Text(
                text = stringResource(Res.string.about_hero_title),
                style = MaterialTheme.typography.displaySmall,
                color = Color.White,
            )
        }
        Spacer(Modifier.height(16.dp))
        SelectionContainer {
            PortfolioMarkdown(
                text = stringResource(
                    Res.string.about_hero_body_md,
                    remember {
                        Clock.System.now()
                            .toLocalDateTime(currentSystemDefault())
                            .let { YearMonth(it.year, it.month) }
                            .yearsUntil(YearMonth(2010, 10))
                            .absoluteValue
                    }
                ),
                paragraphTypography = MaterialTheme.typography.bodyMedium.copy(lineHeight = 25.sp),
                colors = markdownColor(
                    text = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        }
    }
}

@Composable
private fun SkillsSection() {
    val windowSizeClass = LocalWindowSizeClass.current
    val selectedTags = rememberSaveable { mutableStateSetOf<Tag>() }
    Section(backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
        Text(
            text = stringResource(Res.string.about_skills_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(24.dp))
        Column {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    label = { Text(stringResource(Res.string.about_filter_all)) },
                    selected = selectedTags.isEmpty(),
                    onClick = { selectedTags.clear() }
                )
                Tag.entries.forEach { tag ->
                    FilterChip(
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                        label = { Text(tag.string()) },
                        selected = selectedTags.contains(tag),
                        onClick = {
                            if (selectedTags.contains(tag))
                                selectedTags -= tag
                            else {
                                selectedTags += tag
                            }
                        },
                    )
                }
            }
            PortfolioData.skills
                .sortedByDescending { it.level.fraction }
                .filter {
                    if (selectedTags.isEmpty()) return@filter true
                    it.tags.intersect(selectedTags).isNotEmpty().also(::println)
                }
                .iterator()
                .also { iterator ->
                    while (iterator.hasNext()) {
                        val current = iterator.next()
                        if (windowSizeClass > Compact) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(32.dp)
                            ) {
                                Box(Modifier.weight(1f)) {
                                    SkillBar(
                                        label = current.name,
                                        level = current.level,
                                    )
                                }
                                Box(Modifier.weight(1f)) {
                                    if (iterator.hasNext()) {
                                        val next = iterator.next()
                                        SkillBar(
                                            label = next.name,
                                            level = next.level,
                                        )
                                    }
                                }
                            }
                        } else {
                            SkillBar(
                                label = current.name,
                                level = current.level,
                            )
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
            text = stringResource(Res.string.about_expertise_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(24.dp))
        ExpertiseItem(Icons.Default.Coffee, stringResource(Res.string.about_expertise_coffee))
        ExpertiseItem(
            Icons.Default.SportsMotorsports,
            stringResource(Res.string.about_expertise_motorsports)
        )
        ExpertiseItem(
            Icons.Default.Settings,
            stringResource(Res.string.about_expertise_software_architecture)
        )
        ExpertiseItem(
            Icons.Default.House,
            stringResource(Res.string.about_expertise_connected_home)
        )
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
        SelectionContainer {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
private fun CareerTimelineSection() {
    Section(backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
        Text(
            text = stringResource(Res.string.about_timeline_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.about_timeline_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
        )
        Spacer(Modifier.height(24.dp))
        PortfolioData.timelineEntries.forEachIndexed { index, entry ->
            TimelineItem(
                timelineEntry = entry,
                isLast = index == PortfolioData.timelineEntries.lastIndex,
            )
        }
    }
}