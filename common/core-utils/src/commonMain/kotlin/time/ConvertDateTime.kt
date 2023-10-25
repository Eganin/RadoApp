package time

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.company.rado.core.MainRes

// return pair first-answer for view, second answer for server(date;time)
fun convertDateAndHoursAndMinutesToString(
    date: Long,
    hour: Int,
    minute: Int
): Pair<String, Pair<String, String>> {
    val dateFromLong = convertDateLongToString(date = date)
    val newMinute = if (minute in 0..9) "0${minute}" else minute.toString()
    val answerForServer = Pair(first = dateFromLong, second = "$hour:$newMinute")
    val answerForView = "$dateFromLong $hour:$newMinute"
    return Pair(first = answerForView, second = answerForServer)
}

fun convertDateLongToString(date: Long): String {
    val (year, month, day) = Instant.fromEpochMilliseconds(date)
        .toLocalDateTime(TimeZone.currentSystemDefault()).date.toString().split("-")
    return "$day.$month.$year"
}

fun datetimeStringToPrettyString(dateTime: String): String {
    val (date, time) = dateTime.split(";")
    val (day, month, _) = date.split(".")

    val prettyMonth = when (month.toInt()) {
        1 -> MainRes.string.january_title
        2 -> MainRes.string.february_title
        3 -> MainRes.string.march_title
        4 -> MainRes.string.april_title
        5 -> MainRes.string.may_title
        6 -> MainRes.string.june_title
        7 -> MainRes.string.july_title
        8 -> MainRes.string.august_title
        9 -> MainRes.string.september_title
        10 -> MainRes.string.october_title
        11 -> MainRes.string.november_title
        else -> MainRes.string.december_title
    }

    return "$day $prettyMonth $time"
}