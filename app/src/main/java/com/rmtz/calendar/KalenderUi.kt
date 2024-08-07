package com.rmtz.calendar

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rmtz.calendar.libs.Greetings
import com.rmtz.calendar.libs.Kalender
import com.rmtz.calendar.model.Event
import com.rmtz.calendar.ui.component.Container
import com.rmtz.calendar.ui.component.EventItem
import com.rmtz.calendar.ui.component.KalenderWidget
import com.rmtz.calendar.ui.component.TextMediumBold
import com.rmtz.calendar.ui.component.TextMediumThin
import com.rmtz.calendar.ui.component.TextSmall
import com.rmtz.calendar.ui.component.roundedBottomCornerShape
import com.rmtz.calendar.ui.component.verticalDarkGradientTransparent
import com.rmtz.calendar.ui.component.verticalLightGradientTransparent
import com.rmtz.calendar.ui.theme.AppTheme
import com.rmtz.calendar.ui.theme.FlatUiColors
import java.time.Year
import java.time.format.TextStyle
import java.util.Locale

@SuppressLint("NewApi", "AutoboxingStateCreation")
@Composable
fun KalenderUI(innerPadding: PaddingValues) {
    var date by remember { mutableStateOf(Kalender.getToday()) }
    var month by remember { mutableStateOf(Kalender.getToday().month) }
    var year by remember { mutableStateOf(Kalender.getToday().year) }
    val holidays = Kalender.getHoliday(Year.of(year), month)
    var message by remember { mutableStateOf("") }
    var events by remember { mutableStateOf(listOf<Event>()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 32.dp, 0.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header section
        Container(
            Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp)
        ) {
            Column {
                TextMediumBold(text = Greetings.Generate())
                TextMediumThin(text = Greetings.Quote())
            }
        }

        // Divider
        HorizontalDivider(color = FlatUiColors.BasicPallete.LightenDark, thickness = 1.dp)

        // Calendar Controls
        Container(
            Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp)
        ) {
            Column {
                Box {
                    Text(text = "$year")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        TextMediumBold(text = month.getDisplayName(TextStyle.FULL, Locale("id")))
                        Text(
                            text = "${Kalender.getHijriDate(date).monthName} ${Kalender.getHijriDate(date).year}H",
                            color = FlatUiColors.GermanPallet.ReptileGreen,
                            fontStyle = FontStyle.Italic,
                            fontSize = 12.sp,
                            lineHeight = 10.sp
                        )
                    }
                    Row {
                        IconButton(onClick = {
                            month = Kalender.previousMonth(month)
                            if (month.value == 12) {
                                year -= 1
                            }
                            date = Kalender.getToday()
                        }) {
                            Icon(imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft, contentDescription = null)
                        }
                        IconButton(onClick = {
                            month = Kalender.nextMonth(month)
                            if (month.value == 1) {
                                year += 1
                            }
                            date = Kalender.getToday()
                        }) {
                            Icon(imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight, contentDescription = null)
                        }
                    }
                }
            }
        }

        // Calendar Widget
        Container(
            Modifier
                .fillMaxWidth()
                .background(
                    brush = if (isSystemInDarkTheme()) {
                        verticalDarkGradientTransparent
                    } else {
                        verticalLightGradientTransparent
                    },
                    shape = roundedBottomCornerShape
                )
        ) {
            KalenderWidget(month, year) { date = it }
        }

        // Event Section
        Container(
            Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp)
        ) {
            Column {
                if (holidays.isNotEmpty()) {
                    if (date != Kalender.getToday()) {
                        message = "Event pada ${date.dayOfMonth} ${month.getDisplayName(TextStyle.FULL, Locale("id"))}"
                        events = Kalender.getHoliday(date)
                    } else {
                        message = "Event pada ${month.getDisplayName(TextStyle.FULL, Locale("id"))}"
                        events = holidays
                    }
                } else {
                    message = "Tidak ada event pada ${month.getDisplayName(TextStyle.FULL, Locale("id"))}"
                    events = listOf()
                }

                TextMediumThin(text = message)
                // Use LazyColumn for a list of events
                LazyColumn {
                    items(events) { event ->
                        Spacer(modifier = Modifier.height(16.dp))
                        EventItem(event = event)
                    }
                }
            }
        }
    }
}


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
        KalenderUI(PaddingValues(0.dp))
    }
}