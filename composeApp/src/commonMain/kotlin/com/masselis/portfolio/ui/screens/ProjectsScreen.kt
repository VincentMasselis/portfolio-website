package com.masselis.portfolio.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.data.PortfolioData
import com.masselis.portfolio.data.image
import com.masselis.portfolio.ui.components.Footer
import com.masselis.portfolio.ui.components.ProjectCard
import com.masselis.portfolio.ui.components.Section
import com.masselis.portfolio.ui.components.copy
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ProjectsHeaderSection()
        ProjectsListSection()
        Footer()
    }
}

@Composable
private fun ProjectsHeaderSection() {
    Section(
        paddingValues = PaddingValues.Section.copy(top = LocalScaffoldPadding.current.calculateTopPadding()),
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Text(
            text = "MES R\u00C9ALISATIONS",
            style = MaterialTheme.typography.displaySmall,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "D\u00E9couvrez mes projets Android et multiplateformes.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.7f),
        )
    }
}

@Composable
private fun ProjectsListSection() {
    Section(backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
        PortfolioData.projects.forEachIndexed { index, project ->
            ProjectCard(
                title = project.title,
                description = project.description,
                bulletPoints = project.bulletPoints,
                techStack = project.techStack,
                image = project.image,
            )
            if (index < PortfolioData.projects.lastIndex) {
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}