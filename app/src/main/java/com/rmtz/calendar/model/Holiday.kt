package com.rmtz.calendar.model

import java.time.Month

data class Holiday(
    val holiday: String,
    val day: Int,
    val month: Month
)