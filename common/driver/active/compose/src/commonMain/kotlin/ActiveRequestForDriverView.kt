import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import theme.Theme
import widgets.active.CalendarView

@Composable
fun ActiveRequestsForDriverView(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .background(Theme.colors.primaryBackground),
        contentAlignment = Alignment.TopCenter
    ) {
        CalendarView(
            onDayClick = { day ->

            },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .aspectRatio(1.3f)
        )
    }
}