package com.masselis.portfolio.data

import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

internal actual fun LocalDate.formatDayMonth(): String = NSCalendar
    .currentCalendar
    .dateFromComponents(NSDateComponents().apply {
        day = this@formatDayMonth.day.toLong()
        month = this@formatDayMonth.month.number.toLong()
        year = this@formatDayMonth.year.toLong()
    })!!
    .let(df::stringFromDate)

private val df
    get() = NSDateFormatter().apply {
        dateFormat = "d MMMM"
        locale = NSLocale.currentLocale
    }
