import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import tabs.ActiveTabForObserver
import tabs.ArchiveTabForObserver
import tabs.RejectedTabForObserver
import theme.Theme
import widgets.main.TabNavigationItem

object ObserverMainScreen : Screen {

    @Composable
    override fun Content() {
        TabNavigator(ActiveTabForObserver) {
            Scaffold(
                content = {
                    CurrentTab()
                },
                bottomBar = {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(96.dp).background(color = Theme.colors.bottomBarColor)
                    ) {
                        TabNavigationItem(tab = ActiveTabForObserver)
                        TabNavigationItem(tab = ArchiveTabForObserver)
                        TabNavigationItem(tab = RejectedTabForObserver)
                    }
                }
            )
        }
    }
}