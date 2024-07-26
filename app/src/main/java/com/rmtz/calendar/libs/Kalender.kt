package com.rmtz.calendar.libs

import android.annotation.SuppressLint
import com.rmtz.calendar.model.HijriDate
import com.rmtz.calendar.model.Event
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.chrono.HijrahDate
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
        fun getDatesOfMonth(year: Year, month: Month): List<Int> {
            return (1..month.length(LocalDate.of(year.value, month, 1).isLeapYear)).toList()
        }

        @SuppressLint("NewApi")
        fun getDayInFirstMonth(year: Year, month: java.time.Month): DayOfWeek {
            return LocalDate.of(year.value, month, 1).dayOfWeek
        }

        @SuppressLint("NewApi")
        fun getDaysInWeeks(): List<String> {
            return DayOfWeek.entries.map { it.getDisplayName(TextStyle.SHORT, Locale.getDefault()) }
        }

        @SuppressLint("NewApi")
        fun previousMonth(month: Month): Month {
            return month-1
        }

        @SuppressLint("NewApi")
        fun nextMonth(month: Month): Month {
            return month+1
        }

        @SuppressLint("NewApi")
        fun getHijriDate(date: LocalDate): HijriDate {
            val hijri = HijrahDate.from(date)
            val day = DateTimeFormatter.ofPattern("dd", Locale("id")).format(hijri).toInt()
            val month = DateTimeFormatter.ofPattern("MM", Locale("id")).format(hijri).toInt()
            val monthName = DateTimeFormatter.ofPattern("MMMM", Locale("id")).format(hijri)
            val year = DateTimeFormatter.ofPattern("YYYY", Locale("id")).format(hijri).toInt()

            return HijriDate(
                day = day,
                month = month,
                monthName = monthName,
                year = year
            )
        }

        @SuppressLint("NewApi")
        fun isHoliday(date: LocalDate): Boolean {
            return getHoliday(date).isNotEmpty()
        }

        @SuppressLint("NewApi")
        fun getHoliday(date: LocalDate): List<Event> {
            val list = ArrayList<Event>()
            val masehiHoliday = listMasehiHoliday()
            masehiHoliday.forEach {
                if (date.month.value == it.third && date.dayOfMonth == it.second) {
                    list.add(Event(
                        date = LocalDate.of(date.year, it.third, it.second),
                        holiday = it.first,
                        day = it.second,
                        month = it.third,
                        monthName = Month.of(it.third).getDisplayName(TextStyle.SHORT, Locale("id")),
                        year = date.year
                    ))
                }
            }

            val hijriHoliday = listHijriHoliday()
            hijriHoliday.forEach {
                if (getHijriDate(LocalDate.of(date.year, date.month, date.dayOfMonth)).day == it.second && getHijriDate(LocalDate.of(date.year, date.month, date.dayOfMonth)).month == it.third) {
                    list.add(Event(
                        date = date,
                        holiday = it.first,
                        day = it.second,
                        month = it.third,
                        monthName = getHijriDate(LocalDate.of(date.year, date.month, date.dayOfMonth)).monthName,
                        year = getHijriDate(LocalDate.of(date.year, date.month, date.dayOfMonth)).year,
                    ))
                }
            }
            return list
        }

        @SuppressLint("NewApi")
        fun getHoliday(year: Year, month: Month): List<Event> {
            val list = ArrayList<Event>()
            getDatesOfMonth(year, month).forEach {
                val holidays = getHoliday(LocalDate.of(year.value, month, it))
                if (holidays.isNotEmpty()) {
                    list.addAll(holidays)
                }
            }
            return list
        }

        private fun listHijriPinned(): ArrayList<Triple<String, Int, Int>> {
            var holiday = arrayListOf<Triple<String, Int, Int>>()
            holiday.add(Triple("Hari Asyura", 10, MonthHijri.MUHARRAM.value))
            holiday.add(Triple("Ramadan", 1, MonthHijri.RAMADAN.value))
            holiday.add(Triple("Nuzulul Qur'an", 17, MonthHijri.RAMADAN.value))
            holiday.add(Triple("Hari Tarwiyah", 8, MonthHijri.DHU_AL_HIJJAH.value))
            holiday.add(Triple("Wukuf", 9, MonthHijri.DHU_AL_HIJJAH.value))
            holiday.add(Triple("Hari Tasyrik", 11, MonthHijri.DHU_AL_HIJJAH.value))
            holiday.add(Triple("Hari Tasyrik", 12, MonthHijri.DHU_AL_HIJJAH.value))
            holiday.add(Triple("Hari Tasyrik", 13, MonthHijri.DHU_AL_HIJJAH.value))
            return holiday
        }

        private fun listHijriHoliday(): ArrayList<Triple<String, Int, Int>> {
            var holiday = arrayListOf<Triple<String, Int, Int>>()
            holiday.add(Triple("Tahun Baru Islam", 1, MonthHijri.MUHARRAM.value))
            holiday.add(Triple("Maulid Nabi Muhammad SAW", 12, MonthHijri.RABI_AL_AWWAL.value))
            holiday.add(Triple("Isra' Mi'raj Nabi Muhammad SAW", 27, MonthHijri.RAJAB.value))
            holiday.add(Triple("Hari Raya Idulfitri", 1, MonthHijri.SHAWWAL.value))
            holiday.add(Triple("Hari Raya Idulfitri", 2, MonthHijri.SHAWWAL.value))
            holiday.add(Triple("Hari Raya Iduladha", 10, MonthHijri.DHU_AL_HIJJAH.value))
            return holiday
        }

        @SuppressLint("NewApi")
        private fun listMasehiHoliday(): ArrayList<Triple<String, Int, Int>> {
            var holiday = arrayListOf<Triple<String, Int, Int>>()
            holiday.add(Triple("Tahun Baru Masehi", 1, Month.JANUARY.value))
            holiday.add(Triple("Hari Buruh Internasional", 1, Month.MAY.value))
            holiday.add(Triple("Hari Lahir Pancasila", 1, Month.JUNE.value))
            holiday.add(Triple("Hari Kemerdekaan Republik Indonesia", 17, Month.AUGUST.value))
            holiday.add(Triple("Hari Raya Natal", 25, Month.DECEMBER.value))
            return holiday
        }
    }
}