package navigation

import MechanicMainScreen
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.screenModule

val mainMechanicScreenModule = screenModule{
    register<MainMechanicSharedScreen.Main> {
        MechanicMainScreen
    }
}

sealed class MainMechanicSharedScreen: ScreenProvider {

    data object Main : MainMechanicSharedScreen()
}