package com.github.gunin_igor75.vk_application.data.mapper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun convertTime(date: Long): String {
    val currentDate = Date(date * 1000)
    val pattern = "d MMMM yyyy, HH:mm"
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    formatter.timeZone = TimeZone.getDefault()
    return formatter.format(currentDate)
}