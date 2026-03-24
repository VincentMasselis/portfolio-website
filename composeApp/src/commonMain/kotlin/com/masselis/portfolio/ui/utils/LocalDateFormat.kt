package com.masselis.portfolio.ui.utils

import kotlinx.datetime.LocalDate

internal expect fun LocalDate.formatDayMonth(): String
/*
    This code should be used:
    format(LocalDate.Format {
        byUnicodePattern("d MMMM")
    })
    Instead of a platform specific implementation but "MMMM" is not supported by `byUnicodePattern()`
*/