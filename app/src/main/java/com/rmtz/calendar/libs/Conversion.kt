package com.rmtz.calendar.libs

import androidx.compose.ui.text.toLowerCase
import java.util.Locale

fun String.toHari(): String {
    return when (this.toLowerCase(Locale.ENGLISH)) {
        "monday" -> "Senin"
        "tuesday" -> "Selasa"
        "wednesday" -> "Rabu"
        "thursday" -> "Kamis"
        "friday" -> "Jumat"
        "saturday" -> "Sabtu"
        "sunday" -> "Minggu"
        "mon" -> "Sen"
        "tue" -> "Sel"
        "wed" -> "Rab"
        "thu" -> "Kam"
        "fri" -> "Jum"
        "sat" -> "Sab"
        "sun" -> "Min"
        else -> ""
    }
}

fun String.toBulan(): String {
    return when (this.toLowerCase(Locale.ENGLISH)) {
        "january","jan" -> "Januari"
        "february","feb" -> "Februari"
        "march","mar" -> "Maret"
        "april","apr" -> "April"
        "may" -> "Mei"
        "june","jun" -> "Juni"
        "july","jul" -> "Juli"
        "august","aug" -> "Agustus"
        "september","sep" -> "September"
        "october","oct" -> "Oktober"
        "november","nov" -> "November"
        "december","dec" -> "Desember"
        else -> ""
    }
}