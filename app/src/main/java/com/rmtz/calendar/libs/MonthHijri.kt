package com.rmtz.calendar.libs

import android.annotation.SuppressLint

@SuppressLint("NewApi")
enum class MonthHijri {
    MUHARRAM,
    SAFAR,
    RABI_AL_AWWAL,
    RABI_AL_THANI,
    JUMADA_AL_AWWAL,
    JUMADA_AL_THANI,
    RAJAB,
    SHAABAN,
    RAMADAN,
    SHAWWAL,
    DHU_AL_QIDAH,
    DHU_AL_HIJJAH;

    companion object {
        fun of(month: Int): MonthHijri {
            if (month < 1 || month > 12) {
                throw IllegalArgumentException("Invalid value for month: $month")
            }
            return values()[month - 1]
        }
    }

    val value: Int = ordinal + 1
}

