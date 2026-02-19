package com.masselis.portfolio.ui.utils

/**
 * When the bottom bar is displayed on phone, its common to not stack every
 * [com.masselis.portfolio.ui.screens.Route] displayed during the navigation, the latest is
 * displayed and the previous in the stack is always the Root screen. In the other hand, when
 * running on web, it's expected to save each element of the stack and never clean it.
 */
internal expect fun isStackableMainNav(): Boolean