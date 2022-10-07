package com.simplecityapps.shuttle3

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform