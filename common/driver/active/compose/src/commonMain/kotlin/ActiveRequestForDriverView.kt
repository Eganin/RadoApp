import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import theme.Theme
import widgets.active.CalendarView

@Composable
fun ActiveRequestsForDriverView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().background(color = Theme.colors.primaryBackground).padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CalendarView(
            submitInfo = { date ->

            },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
    }
}