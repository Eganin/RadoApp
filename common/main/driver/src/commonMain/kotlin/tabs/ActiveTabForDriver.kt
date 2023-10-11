package tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FeatherIcons
import compose.icons.feathericons.Check
import navigation.ActiveRequestsForDriverSharedScreen

object ActiveTabForDriver : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Active"
            val icon = rememberVectorPainter(FeatherIcons.Check)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val activeRequestForDriverScreen = rememberScreen(ActiveRequestsForDriverSharedScreen.ActiveRequests)
        navigator.push(activeRequestForDriverScreen)
    }
}