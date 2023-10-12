import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.Theme
import widgets.active.CalendarInput
import widgets.active.CalendarView
import widgets.active.createCalendarList

@Composable
fun ActiveRequestsForDriverView(modifier: Modifier = Modifier) {
    val calendarInputList by remember {
        mutableStateOf(createCalendarList())
    }
    var clickedCalendarElem by remember {
        mutableStateOf<CalendarInput?>(null)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.primaryBackground),
        contentAlignment = Alignment.TopCenter
    ) {
        CalendarView(
            calendarInput = calendarInputList,
            onDayClick = { day ->
                clickedCalendarElem = calendarInputList.first { it.day == day }
            },
            month = "September",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .aspectRatio(1.3f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(Alignment.Center)
        ) {
            clickedCalendarElem?.toDos?.forEach {
                Text(
                    text = if (it.contains("Day")) it else "- $it",
                    color = Theme.colors.primaryTextColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = if (it.contains("Day")) 25.sp else 18.sp
                )
            }
        }
    }
}