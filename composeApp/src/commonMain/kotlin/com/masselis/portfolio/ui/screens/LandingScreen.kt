package com.masselis.portfolio.ui.screens

import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.Phonelink
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.data.PortfolioData
import com.masselis.portfolio.data.Project
import com.masselis.portfolio.ui.components.Footer
import com.masselis.portfolio.ui.components.MeshGradientBackground
import com.masselis.portfolio.ui.components.MyselfImage
import com.masselis.portfolio.ui.components.PortfolioMarkdown
import com.masselis.portfolio.ui.components.ProjectCard
import com.masselis.portfolio.ui.components.RepoCard
import com.masselis.portfolio.ui.components.RepoCardPortfolioLabel
import com.masselis.portfolio.ui.components.RepoCardStats
import com.masselis.portfolio.ui.components.Section
import com.masselis.portfolio.ui.components.VerticalScrollbar
import com.masselis.portfolio.ui.components.copy
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass.Compact
import com.masselis.portfolio.ui.utils.CommonParcelize
import com.masselis.portfolio.ui.utils.LocalScaffoldPadding
import com.mikepenz.markdown.model.markdownInlineContent
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeInput
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.glass.GlassStyle
import dev.chrisbanes.haze.glass.hazeGlass
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.ic_github
import portfolio.composeapp.generated.resources.landing_about_name
import portfolio.composeapp.generated.resources.landing_about_section
import portfolio.composeapp.generated.resources.landing_about_tagline
import portfolio.composeapp.generated.resources.landing_github_profile
import portfolio.composeapp.generated.resources.landing_hero_subtitle
import portfolio.composeapp.generated.resources.landing_hero_title_md
import portfolio.composeapp.generated.resources.landing_oss_title
import portfolio.composeapp.generated.resources.landing_see_more


@CommonParcelize
public data object Landing : Route {
    public data class State(
        val onShowProjects: () -> Unit,
    ) : CircuitUiState
}

@AssistedInject
public class LandingPresenter(
    @Assisted private val screen: Landing,
    @Assisted private val navigator: Navigator,
) : Presenter<Landing.State> {

    @CircuitInject(Landing::class, AppScope::class)
    @AssistedFactory
    public interface Factory {
        public fun create(screen: Landing, navigator: Navigator): LandingPresenter
    }

    @Composable
    override fun present(): Landing.State = Landing.State(
        onShowProjects = { navigator.goTo(Projects) }
    )
}

@CircuitInject(Landing::class, AppScope::class)
@Composable
internal fun LandingScreen(
    state: Landing.State,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    val hazeState = rememberHazeState()
    Box(modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box {
                MeshGradientBackground(
                    scrollState = scrollState,
                    modifier = Modifier
                        .matchParentSize()
                        .hazeSource(hazeState),
                )
                Column {
                    HeroSection(showProjects = state.onShowProjects)
                    ProjectsPreviewSection(hazeState = hazeState, onSeeMore = state.onShowProjects)
                }
            }
            AboutPreviewSection()
            OSSSection()
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
private fun HeroSection(
    showProjects: () -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition("blockCursor")
    val showBlockCursor by infiniteTransition.animateValue(
        initialValue = false,
        targetValue = true,
        typeConverter = TwoWayConverter(
            convertToVector = { AnimationVector1D(if (it) 1f else 0f) },
            convertFromVector = { it.value >= 0.5f }
        ),
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                false at 0
                false at 499
                true at 500
                true at 999
            },
            repeatMode = RepeatMode.Restart,
        )
    )
    Section(
        paddingValues = PaddingValues.Section.copy(top = LocalScaffoldPadding.current.calculateTopPadding()),
        backgroundColor = Color.Transparent,
    ) {
        Spacer(Modifier.height(32.dp))
        val cursorId = "cursor"
        val style = MaterialTheme.typography.displayMedium
        SelectionContainer {
            PortfolioMarkdown(
                text = stringResource(Res.string.landing_hero_title_md, "[inline]($cursorId)"),
                paragraphTypography = style.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                ),
                inlineContent = markdownInlineContent(
                    mapOf(
                        cursorId to InlineTextContent(
                            placeholder = Placeholder(
                                width = style.fontSize * 0.6f,
                                height = style.fontSize,
                                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
                            ),
                        ) {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(style.fontSize.value.dp)
                                    .background(
                                        if (showBlockCursor) MaterialTheme.colorScheme.primary
                                        else Color.Transparent
                                    )
                            )
                        }
                    )
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.landing_hero_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun ProjectsPreviewSection(
    hazeState: HazeState,
    onSeeMore: () -> Unit,
) {
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
        if (windowSizeClass == Compact) {
            ProjectGlassItem(
                project = remember { PortfolioData.projects.first() },
                hazeState = hazeState,
            )
            Spacer(Modifier.height(16.dp))
            ProjectGlassItem(
                project = remember { PortfolioData.projects[3] },
                hazeState = hazeState,
            )
            Spacer(Modifier.height(16.dp))
            SeeMore(hazeState = hazeState, onClick = onSeeMore)
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Box(Modifier.weight(1f)) {
                    ProjectGlassItem(
                        project = remember { PortfolioData.projects.first() },
                        hazeState = hazeState,
                    )
                }
                Box(Modifier.weight(1f)) {
                    ProjectGlassItem(
                        project = remember { PortfolioData.projects[3] },
                        hazeState = hazeState,
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(0.3f)
                ) { SeeMore(hazeState = hazeState, onClick = onSeeMore) }
            }
        }
    }
}

@OptIn(ExperimentalHazeApi::class)
@Composable
private fun ProjectGlassItem(
    project: Project,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(12.dp)
    ProjectCard(
        project = project,
        containerColor = Color.Transparent,
        shape = shape,
        // Card's hover elevation redraws its surface layer over the glass; glass has its own hover response
        elevation = CardDefaults.cardElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        interactionSource = interactionSource,
        modifier = modifier.hazeGlass(
            input = HazeInput.Backdrop(hazeState),
            style = GlassStyle.clear.then { shape(shape) },
            interactionSource = interactionSource,
        ),
    )
}

@OptIn(ExperimentalHazeApi::class)
@Composable
private fun SeeMore(
    hazeState: HazeState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(12.dp)
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .hazeGlass(
                input = HazeInput.Backdrop(hazeState),
                 style = GlassStyle.clear.then { shape(shape) },
                interactionSource = interactionSource,
            )
            .clip(shape)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = onClick,
            )
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
                text = stringResource(Res.string.landing_see_more),
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
    Section(backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh) {
        if (windowSizeClass == Compact) {
            MyselfImage(
                modifier = Modifier
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
                    modifier = Modifier.size(220.dp)
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
        text = stringResource(Res.string.landing_about_section),
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onBackground,
    )
    Spacer(Modifier.height(8.dp))
    SelectionContainer {
        Text(
            text = stringResource(Res.string.landing_about_name),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = Bold,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
    Spacer(Modifier.height(12.dp))
    SelectionContainer {
        Text(
            text = stringResource(Res.string.landing_about_tagline),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
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
    val layoutDirection = LocalLayoutDirection.current
    Section(
        backgroundColor = MaterialTheme.colorScheme.surface,
        paddingValues = PaddingValues.Section.copy(start = 0.dp, end = 0.dp)
    ) {
        Text(
            text = stringResource(Res.string.landing_oss_title),
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
            horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth(),
        ) {
            item {
                Spacer(Modifier.width(PaddingValues.Section.calculateStartPadding(layoutDirection)))
            }
            item {
                RepoCard(
                    "TPMS-advanced",
                    { RepoCardStats("TPMS-advanced") }
                )
            }
            item {
                RepoCard(
                    "RxBluetoothKotlin",
                    { RepoCardStats("RxBluetoothKotlin") }
                )
            }
            item {
                RepoCard(
                    "portfolio-website",
                    { RepoCardPortfolioLabel() }
                )
            }
            item {
                val uriHandler = LocalUriHandler.current
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(240.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable(onClick = { uriHandler.openUri("https://github.com/VincentMasselis") })
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp),
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_github),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = stringResource(Res.string.landing_github_profile),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            item {
                Spacer(Modifier.width(PaddingValues.Section.calculateEndPadding(layoutDirection)))
            }
        }
    }
}

