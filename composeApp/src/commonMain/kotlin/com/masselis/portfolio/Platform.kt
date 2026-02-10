package com.masselis.portfolio

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform