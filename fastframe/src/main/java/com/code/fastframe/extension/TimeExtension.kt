package com.iqiyi.extension

fun Long.convertToTimeStamp(): String {
    if (this <= 0) return "00:00"
    val total = this / 1000

    val hour = total / 3600
    val minute = (total / 60) % 60
    val second = total % 60
    if (hour > 0) {
        return String.format("%02d:%02d:%02d", hour, minute, second)
    }
    return String.format("%02d:%02d", minute, second)
}

fun Int.convertToTimeStamp(): String {
    if (this <= 0) return "00:00"
    val total = this / 1000

    val hour = total / 3600
    val minute = (total / 60) % 60
    val second = total % 60
    if (hour > 0) {
        return String.format("%02d:%02d:%02d", hour, minute, second)
    }
    return String.format("%02d:%02d", minute, second)
}