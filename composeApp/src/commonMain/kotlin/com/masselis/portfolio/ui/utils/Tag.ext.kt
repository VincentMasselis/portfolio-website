package com.masselis.portfolio.ui.utils

import com.masselis.portfolio.data.Tag

internal fun Tag.string() = when(this)  {
    Tag.Android -> "Android"
    Tag.Compose -> "Compose"
    Tag.Architecture -> "Architecture"
    Tag.Multiplatform -> "Multiplatform"
    Tag.Resume -> "CV"
}