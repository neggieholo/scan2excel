package com.dragsville.scan2excel

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform