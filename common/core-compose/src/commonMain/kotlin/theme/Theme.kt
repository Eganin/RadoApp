package theme

import LocalMediaControllerProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import picker.LocalMediaController
import platform.LocalPlatform
import platform.Platform

object Theme {
    val colors: RadoColors
        @Composable
        get() = LocalColorProvider.current
}

@Composable
fun AppTheme(platform: Platform, localMediaController: LocalMediaController?, content: @Composable () -> Unit) {
    if(localMediaController != null){
        CompositionLocalProvider(
            LocalColorProvider provides lightPalette,
            LocalPlatform provides platform,
            LocalMediaControllerProvider provides localMediaController,
            content = content
        )
    }else{
        CompositionLocalProvider(
            LocalColorProvider provides lightPalette,
            LocalPlatform provides platform,
            content = content
        )
    }
}