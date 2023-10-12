package widgets.active

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.Theme
import time.convertDateTime

@Composable
fun NewCalendarView(
    modifier: Modifier = Modifier,
    onDayClick: (Int) -> Unit,
    strokeWidth: Float = 15f
) {
    val datetime by remember {
        mutableStateOf(convertDateTime())
    }

    val month by remember {
        mutableStateOf(datetime.second)
    }

    val year by remember {
        mutableStateOf(datetime.first)
    }

    val daysInMonth by remember {
        mutableStateOf(datetime.third)
    }

    val calendarInput by remember {
        mutableStateOf(createCalendarList(daysInMonth = daysInMonth))
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "$month $year",
            fontWeight = FontWeight.SemiBold,
            color = Theme.colors.primaryTextColor,
            fontSize = 30.sp
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            (1..5).forEach { row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    (1..7).forEach { column ->
                        DayCell()
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.DayCell() {
    Card(
        modifier = Modifier.aspectRatio(1f).weight(1f).padding(2.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            focusedElevation = 4.dp,
            pressedElevation = 6.dp
        ),
        border = BorderStroke(2.dp, Theme.colors.highlightColor)
    ) {
        Text(
            text = "1",
            fontWeight = FontWeight.SemiBold,
            color = Theme.colors.primaryTextColor,
            fontSize = 16.sp,
            modifier = Modifier.padding(4.dp),
            textAlign = TextAlign.Center
        )
    }
}