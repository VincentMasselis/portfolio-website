package com.masselis.portfolio.ui.screens

import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

public sealed interface Route {
    @Serializable
    public data object Home : Route

    @Serializable
    public data object About : Route

    @Serializable
    public data object Projects : Route

    @Serializable
    public data object Contact : Route

    public companion object {
        public val routes: List<Route> = listOf(Home, About, Projects, Contact)
        public val classes: List<KClass<out Route>> = routes.map { it::class }
    }
}