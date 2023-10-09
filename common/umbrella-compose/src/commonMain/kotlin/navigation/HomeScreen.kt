package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.viewModelFactory
import theme.AppTheme

object HomeScreen : Screen {

    @Composable
    override fun Content() {
        AppTheme {
            LaunchedEffect(key1 = Unit) {
                val viewModel = viewModelFactory { HomeViewModel() }.createViewModel()
                viewModel.test()
            }
//            val navigator = LocalNavigator.currentOrThrow
//            navigator.push(SplashScreen)
        }
    }
}