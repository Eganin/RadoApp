import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import theme.Theme
import widgets.active.CalendarView
import widgets.active.NewCalendarView

@Composable
fun ActiveRequestsForDriverView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().background(color = Theme.colors.primaryBackground).padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CalendarView(
            onDayClick = { day ->

            },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .aspectRatio(1.3f)
        )
        NewCalendarView(
            onDayClick = { day ->

            },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                //.aspectRatio(1.3f)
        )
    }
}