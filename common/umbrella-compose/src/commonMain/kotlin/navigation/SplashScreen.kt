package navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import platform.Platform

class SplashScreen(private val platform: Platform) : Screen {
    @Composable
    override fun Content() {
        //navigate to home screen in the current module
        val navigator = LocalNavigator.currentOrThrow
        navigator.push(HomeScreen(platform=platform))
    }

}