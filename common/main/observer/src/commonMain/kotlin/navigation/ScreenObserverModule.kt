package navigation

import ObserverMainScreen
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.screenModule

val mainObserverScreenModule = screenModule{
    register<MainObserverSharedScreen.Main> {
        ObserverMainScreen
    }
}

sealed class MainObserverSharedScreen: ScreenProvider {

    data object Main : MainObserverSharedScreen()
}