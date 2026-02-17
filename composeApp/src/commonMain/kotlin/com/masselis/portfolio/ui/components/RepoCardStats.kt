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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.masselis.portfolio.data.GitHubApi
import com.masselis.portfolio.di.AppGraph.Companion.RepoCardStatsViewModel
import com.masselis.portfolio.utils.CommonParcelable
import com.masselis.portfolio.utils.CommonParcelize
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal fun stats(repoName: String): RepoCardContent = { modifier ->
    RepoCardStats(repoName, modifier)
}

@Composable
internal fun RepoCardStats(
    repoName: String,
    modifier: Modifier = Modifier,
    viewModel: RepoCardStatsViewModel = viewModel(key = "RepoCardStatsViewModel_$repoName") {
        RepoCardStatsViewModel(
            repoName,
            createSavedStateHandle()
        )
    }
) {
    val state by viewModel.stateFlow.collectAsState()
    val stats = state as? RepoCardStatsViewModel.State.Stats
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
internal class RepoCardStatsViewModel(
    private val gitHubApi: GitHubApi,
    @Assisted private val repoName: String,
    @Assisted ssh: SavedStateHandle,
) : ViewModel() {

    @AssistedFactory
    interface Factory : (String, SavedStateHandle) -> RepoCardStatsViewModel

    sealed interface State : CommonParcelable {
        @CommonParcelize
        data object Loading : State

        @CommonParcelize
        data class Stats(
            val stars: Int,
            val forks: Int,
            val mainLanguage: String
        ) : State

        @CommonParcelize
        data object Error : State
    }

    private val mutableStateFlow = ssh.getMutableStateFlow<State>("STATE", State.Loading)
    val stateFlow = mutableStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            mutableStateFlow.value =
                runCatching { gitHubApi.fetchStats("VincentMasselis", repoName) }
                    .map { State.Stats(it.stargazersCount, it.forksCount, it.language ?: "Kotlin") }
                    .getOrElse { _ -> State.Error }
        }
    }
}