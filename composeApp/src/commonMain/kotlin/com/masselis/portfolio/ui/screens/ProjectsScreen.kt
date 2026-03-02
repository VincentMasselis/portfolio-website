package com.masselis.portfolio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan.Companion.FullLine
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.data.PortfolioData
import com.masselis.portfolio.ui.components.Footer
import com.masselis.portfolio.ui.components.ProjectCard
import com.masselis.portfolio.ui.components.Section
import com.masselis.portfolio.ui.components.copy
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass.Compact
import com.masselis.portfolio.ui.utils.CommonParcelize
import com.masselis.portfolio.ui.utils.LocalScaffoldPadding
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.screen.StaticScreen
import dev.zacsweers.metro.AppScope

@CommonParcelize
public data object Projects : Route, StaticScreen

@CircuitInject(Projects::class, AppScope::class)
@Composable
internal fun ProjectsScreen(
    modifier: Modifier = Modifier
) {
    val windowSizeClass = LocalWindowSizeClass.current
    val layoutDirection = LocalLayoutDirection.current
    val leftPadding = PaddingValues.Section.calculateLeftPadding(layoutDirection)
    val rightPadding = PaddingValues.Section.calculateRightPadding(layoutDirection)
    // Full-line items (header/footer) must escape the grid's contentPadding inset so they
    // render at full screen width, matching their own internal Section padding.
    val fullLineModifier = Modifier.layout { measurable, constraints ->
        val leftPx = leftPadding.roundToPx()
        val rightPx = rightPadding.roundToPx()
        val placeable = measurable.measure(
            constraints.copy(maxWidth = constraints.maxWidth + leftPx + rightPx)
        )
        layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(if (windowSizeClass == Compact) 1 else 2),
        contentPadding = PaddingValues(start = leftPadding, end = rightPadding),
        horizontalArrangement = Arrangement.spacedBy((leftPadding + rightPadding) / 2),
        verticalItemSpacing = 24.dp,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        item(span = FullLine) {
            Box(fullLineModifier) { ProjectsHeaderSection() }
        }
        itemsIndexed(
            items = PortfolioData.projects,
            itemContent = { _, project ->
                ProjectCard(project = project)
            },
        )
        item(span = FullLine) {
            Box(fullLineModifier) { Footer() }
        }
    }
}

@Composable
private fun ProjectsHeaderSection() {
    Section(
        paddingValues = PaddingValues.Section.copy(top = LocalScaffoldPadding.current.calculateTopPadding()),
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Text(
            text = "MES RÉALISATIONS",
            style = MaterialTheme.typography.displaySmall,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Découvrez tous mes projets",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.7f),
        )
    }
}