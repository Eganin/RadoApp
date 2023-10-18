package platform

import androidx.compose.runtime.staticCompositionLocalOf

enum class Platform{
    Andorid,Ios,Desktop,Web
}
expect class PlatformConfiguration{
    val appname: String
    val platform: Platform
}

val LocalPlatform= staticCompositionLocalOf<Platform> { error("No default platform") }