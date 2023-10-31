package org.company.rado.utils

fun String.toDatetime(time: String): String {
    return if (this.isNotEmpty()) {
        "$this;$time"
    } else {
        time
    }
}