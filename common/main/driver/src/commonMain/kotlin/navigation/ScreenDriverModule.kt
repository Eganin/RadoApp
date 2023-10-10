package navigation

import DriverMainScreen
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.screenModule

val mainDriverScreenModule = screenModule{
    register<MainDriverSharedScreen.Main> {
        DriverMainScreen
    }
}

sealed class MainDriverSharedScreen: ScreenProvider {

    data object Main : MainDriverSharedScreen()
}