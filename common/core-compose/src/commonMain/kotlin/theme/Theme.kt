package theme

import LocalMediaControllerProvider
import LocalPhoneControllerProvider
import PhoneController
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
fun AppTheme(
    platform: Platform,
    localMediaController: LocalMediaController?,
    phoneController: PhoneController?,
    content: @Composable () -> Unit
) {
    if (localMediaController != null && phoneController!= null) {
        CompositionLocalProvider(
            LocalColorProvider provides lightPalette,
            LocalPlatform provides platform,
            LocalMediaControllerProvider provides localMediaController,
            LocalPhoneControllerProvider provides phoneController,
            content = content
        )
    } else {
        CompositionLocalProvider(
            LocalColorProvider provides lightPalette,
            LocalPlatform provides platform,
            content = content
        )
    }
}