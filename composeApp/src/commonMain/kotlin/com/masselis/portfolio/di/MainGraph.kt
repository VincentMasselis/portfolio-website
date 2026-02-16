package com.masselis.portfolio.di

import com.slack.circuit.foundation.Circuit
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.createGraph

@DependencyGraph(
    scope = AppScope::class,
    bindingContainers = [Bindings::class]
)
internal interface MainGraph {
    val circuit: Circuit

    companion object : MainGraph by createGraph()
}