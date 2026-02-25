package com.masselis.portfolio.data

import org.jetbrains.compose.resources.DrawableResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.compose_multiplatform

internal val Project.image: DrawableResource
    get() = when (title) {
        "FITNESS COACH APP" -> Res.drawable.compose_multiplatform
        "CLIENT : LOGISTICS SOLUTIONS" -> Res.drawable.compose_multiplatform
        "SMART HOME CONTROLLER" -> Res.drawable.compose_multiplatform
        else -> Res.drawable.compose_multiplatform
    }
