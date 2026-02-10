package com.masselis.portfolio.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("home")
data object Home

@Serializable
@SerialName("about")
data object About

@Serializable
@SerialName("projects")
data object Projects

@Serializable
@SerialName("contact")
data object Contact
