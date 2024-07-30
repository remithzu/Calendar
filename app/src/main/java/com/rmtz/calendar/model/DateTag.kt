package com.rmtz.calendar.model

import androidx.compose.ui.graphics.Color

@JvmInline
value class DateTag private constructor(val value: Int) {
    companion object {
        val Holiday: DateTag = DateTag(1)
        val Hijri: DateTag = DateTag(2)
        val Hindu: DateTag = DateTag(3)
        val Buddhism: DateTag = DateTag(4)
        val Chinese: DateTag = DateTag(5)
    }
}