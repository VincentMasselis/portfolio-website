package com.masselis.portfolio.di

import com.masselis.portfolio.data.GitHubApi
import com.masselis.portfolio.ui.components.RepoCardStats
import com.masselis.portfolio.ui.components.RepoCardStatsScreen
import com.slack.circuit.foundation.Circuit
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@Suppress("unused")
@BindingContainer
internal object CircuitBinding {

    @Provides
    private fun repoCardStatsScreenFactory(gitHubApi: GitHubApi): RepoCardStatsScreen.Presenter.Factory =
        RepoCardStatsScreen.Presenter.Factory(gitHubApi)

    @SingleIn(AppScope::class)
    @Provides
    private fun circuit(
        repoCardStatsScreenPresenterFactory: RepoCardStatsScreen.Presenter.Factory
    ): Circuit = Circuit.Builder()
        .addPresenterFactory(repoCardStatsScreenPresenterFactory)
        .addUi<RepoCardStatsScreen, RepoCardStatsScreen.State> { state, modifier ->
            RepoCardStats(
                state,
                modifier
            )
        }
        .build()
}