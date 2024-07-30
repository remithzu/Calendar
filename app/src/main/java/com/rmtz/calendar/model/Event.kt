package com.rmtz.calendar.model

import java.time.LocalDate

data class Event(
    val date: LocalDate,
    val holiday: String,
    val day: Int,
    val month: Int,
    val monthName: String,
    val year: Int,
    val tag: DateTag
)