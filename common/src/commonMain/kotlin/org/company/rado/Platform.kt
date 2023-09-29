package org.company.rado

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform