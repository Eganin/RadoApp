package time

import kotlinx.datetime.*

// returns date as a string
fun convertDayMonthYearToDateAndTimeToDateAnswer(year: Int, month: String, day: Int): String {
    val monthAsNumber = when (month) {
        "Январь" -> "01"
        "Февраль" -> "02"
        "Март" -> "03"
        "Апрель" -> "04"
        "Май" -> "05"
        "Июнь" -> "06"
        "Июль" -> "07"
        "Август" -> "08"
        "Сентябрь" -> "09"
        "Октябрь" -> "10"
        "Ноябрь" -> "11"
        else -> "12"
    }

    return "${day}.${monthAsNumber}.${year}"
}

//Returns year, month, count days in a month
fun convertDateTime(): Triple<Int, String, Int> {
    val currentMoment: Instant = Clock.System.now()
    val datetimeInSystemZone: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())

    val year = datetimeInSystemZone.year
    val month = when (datetimeInSystemZone.month.name) {
        "JANUARY" -> "Январь"
        "FEBRUARY" -> "Февраль"
        "MARCH" -> "Март"
        "APRIL" -> "Апрель"
        "MAY" -> "Май"
        "JUNE" -> "Июнь"
        "JULY" -> "Июль"
        "AUGUST" -> "Август"
        "SEPTEMBER" -> "Сентябрь"
        "OCTOBER" -> "Октябрь"
        "NOVEMBER" -> "Ноябрь"
        "DECEMBER" -> "Декабрь"
        else -> ""
    }
    val countDaysInMonth = findCountDaysInMonth(x = datetimeInSystemZone.monthNumber)

    return Triple(first = year, second = month, third = countDaysInMonth)
}

private fun findCountDaysInMonth(x: Int): Int {
    return 28 + ((x + (x / 8)) % 2) + (2 % x) + (2 * (1 / x))
}