package tabs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import compose.icons.FeatherIcons
import compose.icons.feathericons.Clock
import theme.Theme

object ArchiveTabForObserver : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Archive"
            val icon = rememberVectorPainter(FeatherIcons.Clock)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Text(text = "Archive request for Observer", color = Theme.colors.primaryTextColor)
    }
}