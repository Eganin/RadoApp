package navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import platform.Platform

object SplashScreen : Screen {
    @Composable
    override fun Content() {
        ScreenRegistry {
            featureAuthScreenModule()

            mainDriverScreenModule()
            mainMechanicScreenModule()
            mainObserverScreenModule()

            featureActiveRequestsForDriverScreenModule()
        }

        //navigate to auth screen in the auth module
        val navigator = LocalNavigator.currentOrThrow
        val authScreen = rememberScreen(AuthSharedScreen.Auth)
        navigator.push(authScreen)
    }
}