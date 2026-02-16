package com.masselis.portfolio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ForkRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.CommonParcelize
import com.masselis.portfolio.data.GitHubApi
import com.masselis.portfolio.ui.components.RepoCardStatsScreen.State
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import dev.zacsweers.metro.Assisted
import org.jetbrains.compose.resources.painterResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.ic_github
import com.slack.circuit.runtime.presenter.Presenter as CircuitPresenter

@Composable
internal fun RepoCard(
    name: String,
    content: RepoCardContent,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(240.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_github),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = MaterialTheme.colorScheme.onSecondary,
            )
            Text(
                text = "Github",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.8f),
            )
        }
        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
        content(Modifier.weight(1f))
    }
}

@CommonParcelize
internal data class RepoCardStatsScreen(
    val repoName: String
) : Screen {

    sealed interface State : CircuitUiState {
        data object Loading : State

        data class Stats(
            val stars: Int,
            val forks: Int,
            val mainLanguage: String
        ) : State

        data object Error : State
    }

    internal class Presenter(
        private val gitHubApi: GitHubApi,
        @Assisted private val screen: RepoCardStatsScreen,
    ) : CircuitPresenter<State> {

        class Factory(private val gitHubApi: GitHubApi) : CircuitPresenter.Factory {
            override fun create(screen: Screen, navigator: Navigator, context: CircuitContext) =
                when (screen) {
                    is RepoCardStatsScreen -> Presenter(gitHubApi, screen)
                    else -> null
                }
        }

        @Composable
        override fun present(): State {
            val state by produceState<State>(State.Loading) {
                value = runCatching {
                    gitHubApi.fetchStats("VincentMasselis", screen.repoName).let {
                        State.Stats(
                            it.stargazersCount,
                            it.forksCount,
                            it.language ?: "Kotlin"
                        )
                    }
                }.getOrElse { _ -> State.Error }
            }
            return state
        }
    }
}

@Composable
internal fun RepoCardStats(
    state: State,
    modifier: Modifier = Modifier,
) {
    val stats = state as? State.Stats
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = Color.White,
        )
        if (stats != null)
            Text(
                text = "${stats.stars}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
            )
        Spacer(Modifier.width(4.dp))
        Icon(
            imageVector = Icons.Default.ForkRight,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = Color.White,
        )
        if (stats != null)
            Text(
                text = "${stats.forks}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
            )
        Spacer(Modifier.width(8.dp))
        if (stats != null)
            Text(
                text = stats.mainLanguage,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.8f),
            )
    }
}

internal typealias RepoCardContent = @Composable ColumnScope.(Modifier) -> Unit

internal fun stats(repoName: String): RepoCardContent = { modifier ->
    CircuitContent(RepoCardStatsScreen(repoName), modifier)
}

internal fun label(string: String): RepoCardContent = { modifier ->
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier,
    ) {
        Text(
            text = string,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.8f),
        )
    }
}
