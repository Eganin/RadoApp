package theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import platform.LocalPlatform
import platform.Platform

object Theme {
    val colors: RadoColors
        @Composable
        get() = LocalColorProvider.current
}

@Composable
fun AppTheme(platform: Platform, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalColorProvider provides lightPalette,
        LocalPlatform provides platform,
        content = content
    )
}