package com.masselis.portfolio.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal sealed interface Route {
    @Serializable
    @SerialName("home")
    data object Home : Route

    @Serializable
    @SerialName("about")
    data object About : Route

    @Serializable
    @SerialName("projects")
    data object Projects : Route

    @Serializable
    @SerialName("contact")
    data object Contact : Route

    companion object {
        val routes = listOf(Home, About, Projects, Contact)
        val classes = routes.map { it::class }
    }
}