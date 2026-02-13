package com.masselis.portfolio.navigation

import kotlinx.serialization.Serializable

internal sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data object About : Route

    @Serializable
    data object Projects : Route

    @Serializable
    data object Contact : Route

    companion object {
        val routes: List<Route> = listOf(Home, About, Projects, Contact)
        val classes = routes.map { it::class }
    }
}