package navigation

import ActiveRequestsForDriverScreen
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.screenModule

val featureActiveRequestsForDriverScreenModule = screenModule {
    register<ActiveRequestsForDriverSharedScreen.ActiveRequests> {
        ActiveRequestsForDriverScreen
    }
}

sealed class ActiveRequestsForDriverSharedScreen: ScreenProvider{
    data object ActiveRequests: ActiveRequestsForDriverSharedScreen()
}