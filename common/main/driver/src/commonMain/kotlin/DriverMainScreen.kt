import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import tabs.ActiveTab
import tabs.ArchiveTab
import tabs.RejectedTab

object DriverMainScreen : Screen {

    @Composable
    override fun Content() {
        TabNavigator(ActiveTab) {
            Scaffold(
                content = {
                    CurrentTab()
                },
                bottomBar = {
                    Row(modifier = Modifier.fillMaxWidth().height(96.dp)) {
                        TabNavigationItem(tab = ActiveTab)
                        TabNavigationItem(tab = ArchiveTab)
                        TabNavigationItem(tab = RejectedTab)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    Card(
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.weight(1f).padding(16.dp),
        onClick = { tabNavigator.current = tab },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            focusedElevation = 4.dp,
            pressedElevation = 6.dp
        )
    ) {
        tab.options.icon?.let { painterIcon ->
            Icon(
                painter = painterIcon,
                contentDescription = tab.options.title,
                modifier = Modifier.fillMaxSize().padding(16.dp)
            )
        }
    }
}