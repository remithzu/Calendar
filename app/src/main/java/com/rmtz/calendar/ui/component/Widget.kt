package com.rmtz.calendar.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rmtz.calendar.libs.Kalender
import com.rmtz.calendar.libs.toHari
import com.rmtz.calendar.ui.theme.FlatUiColors
import com.rmtz.calendar.ui.theme.FlatUiColors.inverse
import java.time.DayOfWeek
import java.time.LocalDate

@SuppressLint("NewApi")
@Composable
fun KalenderWidget(innerPadding: PaddingValues) {
    val month = Kalender.getToday().month
    val year = Kalender.getToday().year
    val daysOfMonth = Kalender.getDatesOfMonth(year, month)
    val firstDayOfMonth = Kalender.getDayInFirstMonth(year, month)
    val startOffset = if (firstDayOfMonth == DayOfWeek.MONDAY) 0 else firstDayOfMonth.value - 1
    val daysOfWeek = Kalender.getDaysInWeeks()

    /*Grid Layout*/
    Box(Modifier.padding(innerPadding)) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                daysOfWeek.forEach { day ->
                    Box(
                        Modifier
                            .padding(3.dp)
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
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                repeat(startOffset) {
                    item { Spacer(modifier = Modifier.aspectRatio(1f)) }
                }
                itemsIndexed(daysOfMonth) { index, day ->
                    val isSelected = LocalDate.of(year, month, day) == Kalender.getToday()
                    val isFriday = LocalDate.of(year, month, day).dayOfWeek == DayOfWeek.SATURDAY
                    val isWeekend = LocalDate.of(year, month, day).dayOfWeek == DayOfWeek.SUNDAY
                    WidgetItemDate(
                        day = day,
                        isSelected = isSelected,
                        isFriday = isFriday,
                        isWeekend = isWeekend,
                        offMessage = Kalender.getDayOfJawa(LocalDate.of(year, month, day)), //javaDayOfWeek(LocalDate.of(year, month, day)),
                        isHoliday = false
                    )
                }
            }
        }
    }
}

@Composable
fun WidgetItemDate(day: Int, isSelected: Boolean, isFriday: Boolean, isWeekend: Boolean, offMessage: String, isHoliday: Boolean) {
    val backgroundColor = if(isSelected) {
        if (isHoliday) {
            FlatUiColors.GermanPallet.Desire.inverse()
        } else {
            FlatUiColors.GermanPallet.HighBlue
        }
    } else {
        MaterialTheme.colorScheme.background
    }

    val textColor = if (isSelected) {
        FlatUiColors.BasicPallete.LightenDark
    } else if (isFriday) {
        FlatUiColors.GermanPallet.ReptileGreen
    } else if (isWeekend) {
        FlatUiColors.GermanPallet.Desire
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = backgroundColor, // Inner content color
                    shape = RoundedCornerShape(50.dp) // Same radius as outer box
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = day.toString(),
                    style = typography.labelLarge,
                    color = textColor
                )
                Text(
                    text = offMessage,
                    style = typography.labelSmall,
                    color = textColor
                )
                LazyRow {
                }
            }
        }
    }
}