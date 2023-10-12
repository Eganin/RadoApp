package widgets.active

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.aakira.napier.log
import kotlinx.coroutines.launch
import theme.Theme
import time.convertDateTime
import time.convertDayMonthYearToDateAndTimeToDateAnswer

@Composable
fun CalendarView(
    submitInfo: (String) -> Unit,
    modifier: Modifier = Modifier,
    animationDuration: Int = 100,
    scaleDown: Float = 0.9f
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

    var counterDays by remember {
        mutableStateOf(0)
    }

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
            ROWS_COUNT_IN_CALENDAR.forEach { row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    COLUMNS_COUNT_IN_CALENDAR.forEach { column ->
                        counterDays++
                        if (counterDays <= daysInMonth) {
                            DayCells(
                                text = counterDays.toString(),
                                animationDuration = animationDuration,
                                scaleDown = scaleDown
                            ) { day ->
                                submitInfo.invoke(
                                    convertDayMonthYearToDateAndTimeToDateAnswer(
                                        year = year,
                                        month = month,
                                        day = day
                                    ).also {
                                        log(tag = TAG) { it }
                                    }
                                )
                            }
                        } else {
                            DayCells(
                                animationDuration = animationDuration,
                                scaleDown = scaleDown
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.DayCells(
    text: String = "",
    animationDuration: Int = 100,
    scaleDown: Float = 0.9f,
    onDayClick: (Int) -> Unit = {}
) {
    val interactionSource = MutableInteractionSource()

    val coroutineScope = rememberCoroutineScope()

    val scale = remember {
        Animatable(1f)
    }

    Card(
        modifier = Modifier.scale(scale = scale.value).aspectRatio(1f).weight(1f).padding(2.dp)
            .clickable(interactionSource = interactionSource, indication = null) {
                coroutineScope.launch {
                    scale.animateTo(
                        scaleDown,
                        animationSpec = tween(animationDuration),
                    )
                    scale.animateTo(
                        1f,
                        animationSpec = tween(animationDuration),
                    )
                }
                if (text.isNotEmpty()) onDayClick.invoke(text.toInt())
            },
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            focusedElevation = 4.dp,
            pressedElevation = 6.dp
        ),
        border = BorderStroke(2.dp, Theme.colors.highlightColor)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            color = Theme.colors.primaryTextColor,
            fontSize = 16.sp,
            modifier = Modifier.padding(4.dp),
            textAlign = TextAlign.Center
        )
    }
}

private val ROWS_COUNT_IN_CALENDAR = (1..5)
private val COLUMNS_COUNT_IN_CALENDAR = (1..7)
private const val TAG = "CalendarView"