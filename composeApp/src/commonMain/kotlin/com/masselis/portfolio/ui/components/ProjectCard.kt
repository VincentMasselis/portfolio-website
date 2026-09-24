package com.masselis.portfolio.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.data.PortfolioData
import com.masselis.portfolio.data.PortfolioTheme
import com.masselis.portfolio.data.Project
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass.Compact
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeInput
import dev.chrisbanes.haze.glass.GlassStyle
import dev.chrisbanes.haze.glass.hazeGlass
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.projects_featured_badge
import portfolio.composeapp.generated.resources.projects_see_details

@OptIn(ExperimentalHazeApi::class)
@Composable
internal fun ProjectCard(
    project: Project,
    isFeatured: Boolean,
    modifier: Modifier = Modifier,
) {
    val windowSizeClass = LocalWindowSizeClass.current
    var showDetails by rememberSaveable { mutableStateOf(false) }
    val hazeState = rememberHazeState()
    val interactionSource = remember { MutableInteractionSource() }
    val glassModifier = Modifier.hazeGlass(
        input = HazeInput.Backdrop(hazeState),
        style = GlassStyle.projectCard.then { shape(RoundedCornerShape(if (isFeatured) 16.dp else 12.dp)) },
        interactionSource = interactionSource,
    )
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(if (isFeatured) 20.dp else 16.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = { showDetails = true },
            ),
    ) {
        Image(
            painter = painterResource(project.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .hazeSource(hazeState),
        )
        if (isFeatured) {
            FeaturedCaptionItem(
                project = project,
                modifier = Modifier
                    .run {
                        if (windowSizeClass == Compact) {
                            align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(12.dp)
                        } else {
                            align(Alignment.CenterEnd)
                                .fillMaxHeight()
                                .widthIn(max = 440.dp)
                                .fillMaxWidth()
                                .padding(24.dp)
                        }
                    }
                    .then(glassModifier)
                    .padding(horizontal = 24.dp, vertical = 20.dp),
            )
        } else {
            CaptionItem(
                project = project,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .then(glassModifier)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
            )
        }
    }
    if (showDetails) {
        ProjectDetailDialog(
            project = project,
            onDismissRequest = { showDetails = false },
        )
    }
}

@Composable
private fun CaptionItem(
    project: Project,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        TitleItem(project, isFeatured = false)
        Spacer(Modifier.height(6.dp))
        Text(
            text = stringResource(project.bulletPoints.first()),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(Modifier.height(10.dp))
        SkillTagsItem(project)
    }
}

@Composable
private fun FeaturedCaptionItem(
    project: Project,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(Res.string.projects_featured_badge),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), CircleShape)
                .padding(horizontal = 10.dp, vertical = 3.dp),
        )
        Spacer(Modifier.height(12.dp))
        TitleItem(project, isFeatured = true)
        Spacer(Modifier.height(12.dp))
        Text(
            text = project.bulletPoints
                .take(3)
                .map { stringResource(it) }
                .joinToString(separator = "\n") { "• $it" },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(14.dp))
        SkillTagsItem(project)
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.projects_see_details),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun TitleItem(
    project: Project,
    isFeatured: Boolean,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        ProjectLogo(project)
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(project.title),
            style = if (isFeatured) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun SkillTagsItem(project: Project) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        project.skills.take(3).forEach { skill ->
            Badge(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.border(
                    1.dp,
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    CircleShape
                )
            ) {
                Text(
                    text = skill.name,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(4.dp, 2.dp)
                )
            }
        }
    }
}

/**
 * Photos are often bright, so the caption is dimmed to keep its text readable
 */
@OptIn(ExperimentalHazeApi::class)
private val GlassStyle.Companion.projectCard: GlassStyle
    @Composable
    get() {
        val backgroundColor = MaterialTheme.colorScheme.surface
        val tint = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
        return regular.then {
            // Regular's white point lift is calibrated for light appearance and washes out a dark theme
            whitePoint(0f)
            backgroundColor(backgroundColor)
            tint(tint)
        }
    }

@Preview(widthDp = 380, heightDp = 320)
@Composable
private fun ProjectCardPreview() {
    PortfolioTheme {
        ProjectCard(
            project = PortfolioData.projectChapters.first().others.first(),
            isFeatured = false,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(widthDp = 1136, heightDp = 400)
@Composable
private fun FeaturedProjectCardPreview() {
    PortfolioTheme {
        ProjectCard(
            project = PortfolioData.projectChapters.first().featured,
            isFeatured = true,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(widthDp = 360, heightDp = 420)
@Composable
private fun CompactFeaturedProjectCardPreview() {
    PortfolioTheme {
        CompositionLocalProvider(LocalWindowSizeClass provides Compact) {
            ProjectCard(
                project = PortfolioData.projectChapters.first().featured,
                isFeatured = true,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

