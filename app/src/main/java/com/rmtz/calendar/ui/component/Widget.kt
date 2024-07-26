package com.rmtz.calendar.ui.component

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rmtz.calendar.libs.Kalender
import com.rmtz.calendar.libs.toHari
import com.rmtz.calendar.ui.theme.AppTheme
import com.rmtz.calendar.ui.theme.FlatUiColors
import com.rmtz.calendar.ui.theme.FlatUiColors.inverse
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.util.Date
import java.util.Locale
import kotlin.math.abs


@Composable
fun ClockWidget(modifier: Modifier? = Modifier, onUpdateProgress: (Float) -> Unit, onUpdateHour: (Int) -> Unit) {
    var currentTime by remember { mutableStateOf(Date()) }
    val updateTimeMillis = 1000L
    val updateProgressMillis = 1000 * 60L

    val combinedModifier = if (modifier == Modifier) {
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    } else {
        modifier
    }!!

    LaunchedEffect(true) {
        while (true) {
            currentTime = Calendar.getInstance().time
            delay(updateTimeMillis)
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY) % 24
            val currentMinute = calendar.get(Calendar.MINUTE)
            Log.d("Clock", "currentHour: $currentHour, currentMinute: $currentMinute")

            val elapsedMinutes = (currentHour - 6) * 60 + currentMinute
            val totalMinutes = 12 * 60
            val calcDegrees = abs((elapsedMinutes.toFloat() / totalMinutes.toFloat()) * 180f)
            var degrees: Float
            Log.d("Clock Degree", calcDegrees.toString())

            degrees = if (currentHour in 6..18) {
                calcDegrees
            } else {
                calcDegrees - 180f
            }
            if (degrees >= 180f) degrees = 180f

            Log.d("Clock currentHour", currentHour.toString())
            Log.d("Clock Degree", degrees.toString())
            onUpdateProgress(abs(degrees))
            onUpdateHour(currentHour)

            delay(updateProgressMillis)
        }
    }

    Column(
        modifier = combinedModifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(currentTime),
            style = typography.labelMedium,
            color = colorScheme.onSurface,
            fontSize = 20.sp
        )
    }
}

@SuppressLint("NewApi")
@Composable
fun KalenderWidget(month: Month, year: Int, onClick: (LocalDate) -> Unit = {}) {
    val daysOfMonth = Kalender.getDatesOfMonth(Year.of(year), month)
    val firstDayOfMonth = Kalender.getDayInFirstMonth(Year.of(year), month)
    val startOffset = if (firstDayOfMonth == DayOfWeek.MONDAY) 0 else firstDayOfMonth.value - 1
    val daysOfWeek = Kalender.getDaysInWeeks()

    /*Grid Layout*/
    Box(Modifier.padding(0.dp)) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                daysOfWeek.forEach { day ->
                    Box(
                        Modifier
                            .padding(8.dp)
                            .weight(1f)
                            .background(
                                color = FlatUiColors.GermanPallet.NycTaxi,
                                shape = roundedCornerShape
                            ),
                        contentAlignment = Alignment.Center) {
                        Text(
                            text = day.toHari(),
                            style = typography.labelMedium,
                            color = FlatUiColors.GermanPallet.RoyalBlue,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(5.dp, 1.dp)

                        )
                    }
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(7), // 7 columns for 7 days of the week
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp)
            ) {
                repeat(startOffset) {
                    item { Spacer(modifier = Modifier.aspectRatio(1f)) }
                }
                itemsIndexed(daysOfMonth) { _, day ->
                    val isSelected = LocalDate.of(year, month, day) == Kalender.getToday()
                    val isFriday = LocalDate.of(year, month, day).dayOfWeek == DayOfWeek.SATURDAY
                    val isWeekend = LocalDate.of(year, month, day).dayOfWeek == DayOfWeek.SUNDAY
                    Box(Modifier.padding(2.dp, 6.dp)) {
                        WidgetItemDate(
                            date = LocalDate.of(year, month, day),
                            isSelected = isSelected,
                            isFriday = isFriday,
                            isWeekend = isWeekend,
                            offMessage = Kalender.getDayOfJawa(LocalDate.of(year, month, day)), //javaDayOfWeek(LocalDate.of(year, month, day)),
                            isHoliday = Kalender.isHoliday(LocalDate.of(year, month, day))
                        ) { onClick ->
                            onClick(onClick)
                            Log.d("DateListener", "Date: ${onClick.dayOfMonth}, ${onClick.month.name} - ${onClick.year}")
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun WidgetItemDate(
    date: LocalDate,
    isSelected: Boolean,
    isFriday: Boolean,
    isWeekend: Boolean,
    offMessage: String,
    isHoliday: Boolean,
    onClick: (LocalDate) -> Unit = {}
) {
    val backgroundColor = if(isSelected) {
        if (isHoliday) FlatUiColors.GermanPallet.Desire.inverse()
        else FlatUiColors.GermanPallet.HighBlue
    } else {
        Color.Transparent
    }

    var textColor = if (isSelected) {
        FlatUiColors.BasicPallete.LightenDark
    } else if (isFriday) {
        FlatUiColors.GermanPallet.ReptileGreen
    } else if (isWeekend) {
        FlatUiColors.GermanPallet.Desire
    } else {
        colorScheme.onSurface
    }
    if (isHoliday) textColor = FlatUiColors.GermanPallet.Desire

    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .background(
                color = backgroundColor, // Inner content color
                shape = RoundedCornerShape(50.dp) // Same radius as outer box
            )
            .clickable {
                onClick(date)
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = textColor,
                            fontSize = 16.sp,
                        )
                    ) {
                        append(date.dayOfMonth.toString())
                    }
                    withStyle(
                        style = SpanStyle(
                            color = if (isSelected) textColor else FlatUiColors.GermanPallet.ReptileGreen,
                            fontSize = 6.sp,
                        )
                    ) {
                        append(Kalender.getHijriDate(date).day.toString())
                    }
                }
            )
            Text(
                text = offMessage,
                style = typography.labelSmall,
                color = textColor
            )
        }
    }
}

@SuppressLint("NewApi")
@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    showSystemUi = true
)

@Composable
fun GreetingPreview() {
    AppTheme {
        KalenderWidget(Month.JULY, 2024)
    }
}