package com.project.weatherapp.util

import java.text.SimpleDateFormat
import java.util.Date

fun Int.toFormattedDateString(): String {
    try {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val netDate = Date(this.toLong() * 1000)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}




