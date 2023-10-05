package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

object Theme {
    val colors: RadoColors
        @Composable
        get() = LocalColorProvider.current
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalColorProvider provides if (!isSystemInDarkTheme()) lightPalette else darkPalette,
        content = content
    )
}