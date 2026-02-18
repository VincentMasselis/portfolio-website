package com.masselis.portfolio

import com.masselis.portfolio.ui.screens.About
import com.masselis.portfolio.ui.screens.Contact
import com.masselis.portfolio.ui.screens.Landing
import com.masselis.portfolio.ui.screens.Projects
import com.masselis.portfolio.ui.screens.Route
import org.w3c.dom.Location

internal val Route.path: String
    get() = when (this) {
        Landing -> "/"
        About -> "/about"
        Contact -> "/contact"
        Projects -> "/projects"
    }

private val routeList = listOf(Landing, About, Contact, Projects)

internal fun Location.asRoute() = routeList.firstOrNull { it.path == pathname }
