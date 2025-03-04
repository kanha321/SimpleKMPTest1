package com.kanhaji.simplekmptest1

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform