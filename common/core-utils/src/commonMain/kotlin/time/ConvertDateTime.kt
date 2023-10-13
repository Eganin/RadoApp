package time

import kotlinx.datetime.*
import org.company.rado.core.MainRes

// returns date as a string
fun convertDayMonthYearToDateAndTimeToDateAnswer(year: Int, month: String, day: Int): String {
    val monthAsNumber = when (month) {
        MainRes.string.january_title -> 1
        MainRes.string.february_title -> 2
        MainRes.string.march_title -> 3
        MainRes.string.april_title -> 4
        MainRes.string.may_title -> 5
        MainRes.string.june_title -> 6
        MainRes.string.july_title -> 7
        MainRes.string.august_title -> 8
        MainRes.string.september_title -> 9
        MainRes.string.october_title -> 10
        MainRes.string.november_title -> 11
        MainRes.string.december_title -> 12
    }

    return "${day}.${monthAsNumber}.${year}"
}

//Returns year, month, count days in a month
fun convertDateTime(): Triple<Int, String, Int> {
    val currentMoment: Instant = Clock.System.now()
    val datetimeInSystemZone: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())

    val year = datetimeInSystemZone.year
    val month = when (datetimeInSystemZone.month.name) {
        "JANUARY" -> MainRes.string.january_title
        "FEBRUARY" -> MainRes.string.february_title
        "MARCH" -> MainRes.string.march_title
        "APRIL" -> MainRes.string.april_title
        "MAY" -> MainRes.string.may_title
        "JUNE" -> MainRes.string.june_title
        "JULY" -> MainRes.string.july_title
        "AUGUST" -> MainRes.string.august_title
        "SEPTEMBER" -> MainRes.string.september_title
        "OCTOBER" -> MainRes.string.october_title
        "NOVEMBER" -> MainRes.string.november_title
        else -> MainRes.string.december_title
    }
    val countDaysInMonth = findCountDaysInMonth(x = datetimeInSystemZone.monthNumber)

    return Triple(first = year, second = month, third = countDaysInMonth)
}

private fun findCountDaysInMonth(x: Int): Int {
    return 28 + ((x + (x / 8)) % 2) + (2 % x) + (2 * (1 / x))
}