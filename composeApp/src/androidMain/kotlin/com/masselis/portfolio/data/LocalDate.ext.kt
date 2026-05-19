package com.masselis.portfolio.data

import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import java.time.format.DateTimeFormatter
import java.time.LocalDate as JavaLocalDate
import java.util.Locale as JavaLocale

internal actual fun LocalDate.formatDayMonth(): String = JavaLocalDate
    .of(year, month.number, day)
    .format(df)

private val df
    get() = DateTimeFormatter.ofPattern("d MMMM", JavaLocale.getDefault())
