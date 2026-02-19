package com.masselis.portfolio.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.ForkRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.masselis.portfolio.data.GitHubApi
import com.masselis.portfolio.ui.components.RepoCardStatsScreen.State
import com.masselis.portfolio.ui.utils.CommonIgnoredOnParcel
import com.masselis.portfolio.ui.utils.CommonParcelable
import com.masselis.portfolio.ui.utils.CommonParcelize
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

@Composable
internal fun RepoCardStats(
    repoName: String,
    modifier: Modifier = Modifier,
) {
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
            val mainLanguage: String,
            @CommonIgnoredOnParcel val eventSink: (Event) -> Unit = { error("") }
        ) : State {
            public sealed interface Event : CircuitUiState {
                public data object OpenInWebBrowser : Event
            }
        }

        @CommonParcelize
        public data class Error(
            @CommonIgnoredOnParcel val eventSink: (Event) -> Unit = { error("") }
        ) : State {
            public sealed interface Event : CircuitUiState {
                public data object Retry : Event
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@CircuitInject(RepoCardStatsScreen::class, AppScope::class)
@Composable
internal fun RepoCardStats(
    state: State,
    modifier: Modifier = Modifier,
) {
    when (state) {
        State.Loading -> Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onSecondary,
                strokeWidth = 3.dp,
                strokeCap = StrokeCap.Round,
                modifier = Modifier.size(24.dp)
            )
        }

        is State.Error -> Button(
            content = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Echec de chargement",
                    style = MaterialTheme.typography.labelMedium,
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSecondary,
            ),
            onClick = { state.eventSink(State.Error.Event.Retry) },
            modifier = modifier,
        )

        is State.Stats -> {
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
                Text(
                    text = "${state.stars}",
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
                Text(
                    text = "${state.forks}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = state.mainLanguage,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.8f),
                )
                Spacer(Modifier.weight(1f))
                IconButton(
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            contentDescription = null
                        )
                    },
                    onClick = {
                        state.eventSink(State.Stats.Event.OpenInWebBrowser)
                    }
                )
            }
        }
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
        val uriHandler = LocalUriHandler.current
        var state: State by rememberSaveable { mutableStateOf(State.Loading) }

        fun handleErrorEvent(event: State.Error.Event) {
            when (event) {
                State.Error.Event.Retry -> state = State.Loading
            }
        }

        fun handleStatsEvent(event: State.Stats.Event) {
            when (event) {
                State.Stats.Event.OpenInWebBrowser -> {
                    uriHandler.openUri("https://github.com/VincentMasselis/${screen.repoName}")
                }
            }
        }

        LaunchedEffect(state) {
            state = when (val state = state) {
                is State.Error -> state.copy(eventSink = ::handleErrorEvent)

                is State.Stats -> state.copy(eventSink = ::handleStatsEvent)

                State.Loading ->
                    runCatching { gitHubApi.fetchStats("VincentMasselis", screen.repoName) }
                        .map {
                            State.Stats(
                                it.stargazersCount,
                                it.forksCount,
                                it.language ?: "Kotlin",
                                eventSink = ::handleStatsEvent
                            )
                        }
                        .getOrElse { _ -> State.Error(::handleErrorEvent) }
            }
        }
        return state
    }
}
