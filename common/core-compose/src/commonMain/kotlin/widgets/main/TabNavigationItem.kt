package widgets.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.TabNavigationItem(tab: Tab) {
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