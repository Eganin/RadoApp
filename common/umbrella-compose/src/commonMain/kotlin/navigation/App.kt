package navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import platform.Platform
import theme.AppTheme

@Composable
fun App(platform: Platform){
    AppTheme(platform=platform){
        Navigator(SplashScreen)
    }
}