package com.example.rikis.footballmatchscheduleapp.utils


import android.view.View
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

private fun formatDate(date: String, format: String): String {
    var result = ""
    val old = SimpleDateFormat("yyyy-MM-dd")

    try {
        val oldDate = old.parse(date)
        val newFormat = SimpleDateFormat(format)

        result = newFormat.format(oldDate)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return result
}

fun getStrDate(date: String?): String {
    return formatDate(date.toString(), "dd/MM/yy")
}

fun getStrTime(date: String?): String {
    return formatDate(date.toString(), "HH:mm")
}

fun getShortDate(date: String?): String {
    return formatDate(date.toString(), "dd MMMM yyyy")
}

fun getLongDate(date: String?): String {
    return formatDate(date.toString(), "EEE, dd MMM yyyy")
}

fun toGMTFormat(date: String, time: String): Date? {
    val formatter = SimpleDateFormat("dd/MM/yy HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val dateTime = "$date $time"
    return formatter.parse(dateTime)
}

fun ellipsize(input: String?, maxLength: Int): String? {
    val ellip = "..."
    return if (input == null || input.length <= maxLength
            || input.length < ellip.length) {
        input
    } else input.substring(0, maxLength - ellip.length) + ellip
}