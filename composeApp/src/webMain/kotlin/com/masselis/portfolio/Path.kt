package com.masselis.portfolio

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.masselis.portfolio.navigation.Route
import org.w3c.dom.Location

internal val Route.path: String
    get() = when (this) {
        Route.Home -> "/"
        Route.About -> "/about"
        Route.Contact -> "/contact"
        Route.Projects -> "/projects"
    }

internal fun Location.asRoute() = Route
    .routes
    .firstOrNull { it.path == pathname }

internal fun NavDestination.asRoute() = Route
    .routes
    .firstOrNull { hasRoute(it::class) }