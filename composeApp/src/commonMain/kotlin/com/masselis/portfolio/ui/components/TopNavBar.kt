package com.masselis.portfolio.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.ui.screens.About
import com.masselis.portfolio.ui.screens.Contact
import com.masselis.portfolio.ui.screens.Landing
import com.masselis.portfolio.ui.screens.Projects
import com.masselis.portfolio.ui.screens.Resume
import com.masselis.portfolio.ui.screens.Route
import com.masselis.portfolio.ui.theme.LocalWindowSizeClass
import com.masselis.portfolio.ui.theme.WindowSizeClass.Compact
import dev.chrisbanes.haze.HazeInput
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.blur.HazeBlurStyle
import dev.chrisbanes.haze.blur.HazeColorEffect
import dev.chrisbanes.haze.blur.hazeBlur
import org.jetbrains.compose.resources.stringResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.nav_about
import portfolio.composeapp.generated.resources.nav_contact
import portfolio.composeapp.generated.resources.nav_home
import portfolio.composeapp.generated.resources.nav_projects
import portfolio.composeapp.generated.resources.nav_resume

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopNavBar(
    currentRoute: Route,
    scrollBehavior: TopAppBarScrollBehavior,
    openRoute: (Route) -> Unit,
    hazeState: HazeState,
    additionalActions: @Composable RowScope.() -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val isContentUnderneath by remember(scrollBehavior.state) {
        derivedStateOf { scrollBehavior.state.overlappedFraction > 0.01f }
    }
    val blurAlpha by animateFloatAsState(
        targetValue = if (isContentUnderneath) 1f else 0f,
        label = "topBarBlurAlpha",
    )
    TopAppBar(
        modifier = modifier.hazeBlur(
            input = HazeInput.Backdrop(hazeState),
            style = HazeBlurStyle.topBar.then { alpha(blurAlpha) },
        ),
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
        ),
        navigationIcon = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.PhoneAndroid,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp),
                )
            }
        },
        title = {
            Text(
                text = "RxVincent",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        actions = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (LocalWindowSizeClass.current > Compact) {
                    NavLink(
                        stringResource(Res.string.nav_home),
                        Landing,
                        currentRoute,
                        openRoute
                    )
                    NavLink(
                        stringResource(Res.string.nav_about),
                        About,
                        currentRoute,
                        openRoute
                    )
                    NavLink(
                        stringResource(Res.string.nav_projects),
                        Projects,
                        currentRoute,
                        openRoute
                    )
                    NavLink(
                        stringResource(Res.string.nav_contact),
                        Contact,
                        currentRoute,
                        openRoute
                    )
                    NavLink(
                        stringResource(Res.string.nav_resume),
                        Resume,
                        currentRoute,
                        openRoute
                    )
                }
                additionalActions()
            }
            Spacer(Modifier.width(16.dp))
        },
    )
}

@Composable
private fun NavLink(
    label: String,
    route: Route,
    currentRoute: Route,
    onNavigate: (Route) -> Unit,
) {
    val isActive = currentRoute === route
    Text(
        text = label,
        style = MaterialTheme.typography.labelLarge,
        color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        textDecoration = if (isActive) TextDecoration.Underline else TextDecoration.None,
        modifier = Modifier.clickable { onNavigate(route) },
    )
}

private val HazeBlurStyle.Companion.topBar: HazeBlurStyle
    @Composable
    get() {
        val tint = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.7f)
        return HazeBlurStyle {
            blurRadius(20.dp)
            colorEffects(listOf(HazeColorEffect.tint(tint)))
        }
    }
