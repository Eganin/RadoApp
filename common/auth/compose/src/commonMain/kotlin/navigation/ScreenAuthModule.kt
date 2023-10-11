package navigation

import AuthScreen
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.screenModule

val featureAuthScreenModule = screenModule {
    register<AuthSharedScreen.Auth> {
        AuthScreen
    }
}

sealed class AuthSharedScreen: ScreenProvider{
    data object Auth : AuthSharedScreen()
}