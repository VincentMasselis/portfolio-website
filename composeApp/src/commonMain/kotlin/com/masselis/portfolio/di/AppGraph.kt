package com.masselis.portfolio.di

import com.masselis.portfolio.ui.components.RepoCardStatsViewModel
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.createGraph

@DependencyGraph(
    scope = AppScope::class,
    bindingContainers = [Bindings::class]
)
internal interface AppGraph {

    val RepoCardStatsViewModel: RepoCardStatsViewModel.Factory

    companion object : AppGraph by createGraph()
}