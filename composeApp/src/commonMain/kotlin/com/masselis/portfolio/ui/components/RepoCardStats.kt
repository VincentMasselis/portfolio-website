package com.masselis.portfolio.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ForkRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.data.GitHubApi
import com.masselis.portfolio.ui.components.RepoCardStatsScreen.State
import com.masselis.portfolio.utils.CommonParcelable
import com.masselis.portfolio.utils.CommonParcelize
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

internal fun stats(repoName: String): RepoCardContent = { modifier ->
    CircuitContent(RepoCardStatsScreen(repoName), modifier)
}

@CommonParcelize
public data class RepoCardStatsScreen(
    val repoName: String
) : Screen {

    public sealed interface State : CircuitUiState, CommonParcelable {
        @CommonParcelize
        public data object Loading : State

        @CommonParcelize
        public data class Stats(
            val stars: Int,
            val forks: Int,
            val mainLanguage: String
        ) : State

        @CommonParcelize
        public data object Error : State
    }
}

@CircuitInject(RepoCardStatsScreen::class, AppScope::class)
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

@AssistedInject
public class RepoCardStatsPresenter(
    private val gitHubApi: GitHubApi,
    @Assisted private val screen: RepoCardStatsScreen,
) : Presenter<State> {

    @CircuitInject(RepoCardStatsScreen::class, AppScope::class)
    @AssistedFactory
    public interface Factory {
        public fun create(screen: RepoCardStatsScreen): RepoCardStatsPresenter
    }

    @Composable
    override fun present(): State {
        var state: State by rememberSaveable { mutableStateOf(State.Loading) }
        LaunchedEffect(screen.repoName) {
            if (state is State.Loading)
                state = runCatching { gitHubApi.fetchStats("VincentMasselis", screen.repoName) }
                    .map { State.Stats(it.stargazersCount, it.forksCount, it.language ?: "Kotlin") }
                    .getOrElse { _ -> State.Error }
        }
        return state
    }
}
