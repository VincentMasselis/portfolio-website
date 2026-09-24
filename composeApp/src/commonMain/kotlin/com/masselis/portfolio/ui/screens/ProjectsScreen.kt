package com.masselis.portfolio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.data.PortfolioData
import com.masselis.portfolio.data.ProjectChapter
import com.masselis.portfolio.data.TimelineEntry
import com.masselis.portfolio.ui.components.Footer
import com.masselis.portfolio.ui.components.ProjectCard
import com.masselis.portfolio.ui.components.Section
import com.masselis.portfolio.ui.components.copy
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass.Compact
import com.masselis.portfolio.ui.theme.WindowSizeClass.Expanded
import com.masselis.portfolio.ui.theme.WindowSizeClass.Medium
import com.masselis.portfolio.ui.utils.CommonParcelize
import com.masselis.portfolio.ui.utils.LocalScaffoldPadding
import com.masselis.portfolio.ui.utils.LocalTopBarHazeState
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.screen.StaticScreen
import dev.chrisbanes.haze.hazeSource
import dev.zacsweers.metro.AppScope
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.projects_chapter_count
import portfolio.composeapp.generated.resources.projects_header_subtitle
import portfolio.composeapp.generated.resources.projects_header_title
import portfolio.composeapp.generated.resources.timeline_now

@CommonParcelize
public data object Projects : Route, StaticScreen

@CircuitInject(Projects::class, AppScope::class)
@Composable
internal fun ProjectsScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .hazeSource(LocalTopBarHazeState.current)
    ) {
        item { Spacer(Modifier.height(44.dp)) }
        item { ProjectsHeaderSection() }
        items(PortfolioData.projectChapters) { ProjectChapterSection(it) }
        item { Footer() }
    }
}

@Composable
private fun ProjectsHeaderSection() {
    Section(
        paddingValues = PaddingValues.Section.copy(top = LocalScaffoldPadding.current.calculateTopPadding()),
        backgroundColor = Color.Transparent,
    ) {
        Text(
            text = stringResource(Res.string.projects_header_title),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.projects_header_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun ProjectChapterSection(chapter: ProjectChapter) {
    val windowSizeClass = LocalWindowSizeClass.current
    Section(
        backgroundColor = Color.Transparent,
        paddingValues = PaddingValues.Section.copy(top = 0.dp),
    ) {
        ChapterHeaderItem(chapter)
        Spacer(Modifier.height(20.dp))
        when {
            windowSizeClass == Compact -> {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    ProjectCard(
                        project = chapter.featured,
                        isFeatured = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(420.dp),
                    )
                    chapter.others.forEach { project ->
                        ProjectCard(
                            project = project,
                            isFeatured = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                        )
                    }
                }
            }

            chapter.others.size == 1 -> {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                ) {
                    ProjectCard(
                        project = chapter.featured,
                        isFeatured = true,
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight(),
                    )
                    ProjectCard(
                        project = chapter.others.single(),
                        isFeatured = false,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    )
                }
            }

            else -> {
                val columns = when (windowSizeClass) {
                    Medium -> 2
                    Expanded -> 3
                }.coerceAtMost(chapter.others.size)
                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    ProjectCard(
                        project = chapter.featured,
                        isFeatured = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                    )
                    chapter.others
                        .chunked(columns)
                        .forEach { row ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(24.dp),
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                row.forEach { project ->
                                    ProjectCard(
                                        project = project,
                                        isFeatured = false,
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(320.dp),
                                    )
                                }
                                // Keeps the cards of an incomplete last row at the same width as the others
                                val missingCells = columns - row.size
                                if (missingCells > 0) Spacer(Modifier.weight(missingCells.toFloat()))
                            }
                        }
                }
            }
        }
    }
}

@Composable
private fun ChapterHeaderItem(chapter: ProjectChapter) {
    val projectCount = chapter.others.size + 1
    Column {
        Text(
            text = when (val time = chapter.period) {
                is TimelineEntry.Moment -> "${time.moment.year}"
                is TimelineEntry.Range -> "${time.from.year}-${time.to.year}"
                is TimelineEntry.Pending -> "${time.moment.year}-${stringResource(Res.string.timeline_now)}"
            },
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = stringResource(chapter.title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "${stringResource(chapter.subtitle)} · ${
                pluralStringResource(
                    Res.plurals.projects_chapter_count,
                    projectCount,
                    projectCount
                )
            }",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
