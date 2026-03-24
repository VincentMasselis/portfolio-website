package com.masselis.portfolio.ui.utils

import androidx.compose.ui.text.intl.Locale
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("(year, month, day, locale) => new Date(year, month - 1, day).toLocaleDateString(locale, { day: 'numeric', month: 'long' })")
private external fun formatDateJs(year: Int, month: Int, day: Int, locale: String): String

internal actual fun LocalDate.formatDayMonth(): String = formatDateJs(
    year,
    month.number,
    day,
    Locale.displayedLocale.toLanguageTag()
)
