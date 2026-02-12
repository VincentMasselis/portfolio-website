package com.masselis.portfolio.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.data.PortfolioData
import com.masselis.portfolio.ui.components.ProjectCard
import com.masselis.portfolio.ui.components.Section
import com.masselis.portfolio.ui.components.copy
import com.masselis.portfolio.ui.theme.DarkNavy
import com.masselis.portfolio.ui.theme.LightGray

@Composable
internal fun ProjectsScreen(
    scaffoldPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ProjectsHeaderSection(scaffoldPadding)
        ProjectsListSection()
    }
}

@Composable
private fun ProjectsHeaderSection(
    scaffoldPadding: PaddingValues,
) {
    Section(
        paddingValues = PaddingValues.Section.copy(top = scaffoldPadding.calculateTopPadding()),
        backgroundColor = DarkNavy,
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
    Section(backgroundColor = LightGray) {
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
