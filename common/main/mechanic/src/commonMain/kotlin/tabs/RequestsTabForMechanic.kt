package tabs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FeatherIcons
import compose.icons.feathericons.List
import theme.Theme

object RequestsTabForMechanic : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Rejected"
            val icon = rememberVectorPainter(FeatherIcons.List)

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
        Text(text = "Unconfirmed requests for Mechanic", color = Theme.colors.primaryTextColor)
    }
}