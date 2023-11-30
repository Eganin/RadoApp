package tabs

import RejectRequestsScreenForDriver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FeatherIcons
import compose.icons.feathericons.X

object RejectedTabForDriver : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Rejected"
            val icon = rememberVectorPainter(FeatherIcons.X)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(RejectRequestsScreenForDriver)
    }
}