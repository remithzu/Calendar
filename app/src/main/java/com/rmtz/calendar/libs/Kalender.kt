package com.rmtz.calendar.libs

import android.annotation.SuppressLint
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale

class Kalender {
    companion object {
        val epochTime = "1970-01-01 07:00:00"

        @SuppressLint("NewApi")
        fun getToday(): LocalDate {
            return LocalDate.now()
        }

        @SuppressLint("NewApi")
        fun getDayOfJawa(date: LocalDate?): String {
            val offMessages = arrayOf("wage", "kliwon", "legi", "pahing", "pon")
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val epochDateTime = LocalDate.parse(epochTime, formatter)
            val daysSinceEpoch = ChronoUnit.DAYS.between(epochDateTime, date)
            val messageIndex = (daysSinceEpoch % offMessages.size).toInt()
            return offMessages[messageIndex]
        }

        @SuppressLint("NewApi")
        fun getDatesOfMonth(year: Int, month: java.time.Month): List<Int> {
            return (1..month.length(LocalDate.of(year, month, 1).isLeapYear)).toList()
        }

        @SuppressLint("NewApi")
        fun getDayInFirstMonth(year: Int, month: java.time.Month): DayOfWeek {
            return LocalDate.of(year, month, 1).dayOfWeek
        }

        @SuppressLint("NewApi")
        fun getDaysInWeeks(): List<String> {
            return DayOfWeek.entries.map { it.getDisplayName(TextStyle.SHORT, Locale.getDefault()) }
        }
    }
}