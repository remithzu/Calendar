package com.rmtz.calendar

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.content.res.Configuration
import android.text.Html
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.aligned
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.sharp.KeyboardArrowLeft
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rmtz.calendar.libs.Greetings
import com.rmtz.calendar.libs.Kalender
import com.rmtz.calendar.ui.component.ClockWidget
import com.rmtz.calendar.ui.component.Container
import com.rmtz.calendar.ui.component.KalenderWidget
import com.rmtz.calendar.ui.component.TextMedium
import com.rmtz.calendar.ui.component.TextMediumBold
import com.rmtz.calendar.ui.component.TextMediumThin
import com.rmtz.calendar.ui.component.TextNormal
import com.rmtz.calendar.ui.component.TextSmall
import com.rmtz.calendar.ui.component.TextSmallBold
import com.rmtz.calendar.ui.component.nightGradient
import com.rmtz.calendar.ui.component.roundedBottomCornerShape
import com.rmtz.calendar.ui.component.shineGradient
import com.rmtz.calendar.ui.component.verticalDarkGradientTransparent
import com.rmtz.calendar.ui.component.verticalLightGradientTransparent
import com.rmtz.calendar.ui.theme.AppTheme
import com.rmtz.calendar.ui.theme.FlatUiColors
import com.rmtz.calendar.ui.theme.yellowTransient
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi", "AutoboxingStateCreation")
@Composable
fun KalenderUI(innerPadding: PaddingValues) {
    val date = Kalender.getToday().dayOfMonth
    var month by remember { mutableStateOf(Kalender.getToday().month)}
    var year by remember { mutableStateOf(Kalender.getToday().year)}

    /*Grid Layout*/
    Column(Modifier.padding(0.dp, 32.dp, 0.dp,0.dp)) {
        Container(Modifier.fillMaxWidth().padding(8.dp, 0.dp)) {
            Column {
                TextMediumBold(text = Greetings.Generate())
                TextMediumThin(text = Greetings.Quote())
            }
        }
        HorizontalDivider(color = FlatUiColors.BasicPallete.LightenDark , thickness = 1.dp)
        Container(Modifier.fillMaxWidth().padding(8.dp, 0.dp)) {
            Column {
                Box() {
                    Text(text = "$year")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    TextMediumBold(text = "$month")
                    Row {
                        Box(Modifier.clickable {
                            month = Kalender.previousMonth(month)
                            Log.e("Month", "$year $month ${month.value}")
                            if (month.value == 12) {
                                year -= 1
                            }
                        }) {
                            Icon(imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft, contentDescription = "")
                        }
                        Box(Modifier.clickable {
                            month = Kalender.nextMonth(month)
                            Log.e("Month", "$year $month ${month.value}")
                            if (month.value == 1) {
                                year += 1
                            }
                        }) {
                            Icon(imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight, contentDescription = "")
                        }
                    }
                }
            }
        }
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
            KalenderWidget(month, year)
        }

        Container(Modifier.fillMaxWidth().padding(8.dp, 0.dp)) {
            Column {
                TextMediumBold(text = "Today")
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