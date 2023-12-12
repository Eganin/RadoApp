package navigation

import PhoneController
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import picker.LocalMediaController
import platform.Platform
import theme.AppTheme

@Composable
fun App(
    platform: Platform,
    localMediaController: LocalMediaController? = null,
    phoneController: PhoneController? = null
) {
    AppTheme(
        platform = platform,
        localMediaController = localMediaController,
        phoneController = phoneController
    ) {
        Navigator(SplashScreen)
    }
}