package tabs

import ActiveRequestsScreenForObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FeatherIcons
import compose.icons.feathericons.Check

object ActiveTabForObserver : Tab {

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
        Navigator(ActiveRequestsScreenForObserver)
    }
}