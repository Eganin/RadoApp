package navigation

import AuthSharedScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.viewModelFactory
import featureAuthScreenModule
import platform.Platform

class HomeScreen(private val platform: Platform) : Screen {

    @Composable
    override fun Content() {
        LaunchedEffect(key1 = Unit) {
            val viewModel = viewModelFactory { HomeViewModel() }.createViewModel()
            viewModel.test()
        }

        ScreenRegistry {
            featureAuthScreenModule()
        }

        //navigate to auth screen in the auth module
        val navigator = LocalNavigator.currentOrThrow
        val authScreen = rememberScreen(AuthSharedScreen.Auth)
        navigator.push(authScreen)
    }
}