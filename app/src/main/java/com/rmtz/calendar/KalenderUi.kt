package com.rmtz.calendar

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.rmtz.calendar.libs.Kalender
import com.rmtz.calendar.ui.component.ClockWidget
import com.rmtz.calendar.ui.component.KalenderWidget
import com.rmtz.calendar.ui.component.nightGradient
import com.rmtz.calendar.ui.component.shineGradient
import com.rmtz.calendar.ui.theme.AppTheme
import com.rmtz.calendar.ui.theme.yellowTransient
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HalfCircleProgressBar(modifier: Modifier) {
    var progress by remember { mutableFloatStateOf(0f) }
    var startAngle by remember { mutableFloatStateOf(-180f) }
    val iconSun = painterResource(id = R.drawable.ic_sun)
    val iconMoon = painterResource(id = R.drawable.ic_moon)
    var icon by remember { mutableStateOf(iconMoon) }
    var colorProgress by remember { mutableStateOf(nightGradient) }
    var colorCircle by remember { mutableStateOf(Color.LightGray) }

    Box( modifier = modifier .background(Color.Transparent) ) {
        Canvas(modifier) {
            /* Base ProgressBar */
            drawArc(
                color = Color.LightGray,
                startAngle = -180f,
                sweepAngle = 180f,
                useCenter = false,
                size = Size(size.width, size.height * 2),
                style = Stroke(8.dp.toPx(), cap = StrokeCap.Round)
            )
            /* Running Progress ProgressBar */
            drawArc(
                brush = colorProgress,
                startAngle = startAngle,
                sweepAngle = progress,
                useCenter = false,
                size = Size(size.width, size.height * 2),
                style = Stroke(8.dp.toPx(), cap = StrokeCap.Round)
            )

            val iconSize = 24.dp.toPx()
            val angleInRadians = Math.toRadians(-180.0 + progress.toDouble())
            val iconX = (size.width / 2) + (size.width / 2) * Math.cos(angleInRadians).toFloat() - iconSize / 2
            val iconY = (size.height) + (size.height) * Math.sin(angleInRadians).toFloat() - iconSize / 2
            /* Draw Circle Point */
            /*drawCircle(
                color = colorCircle,
                radius = 40f,
                center = Offset(iconX+27f, iconY+26f)
            )*/
            /* Draw Icon top of Circle Point */
            translate(left = iconX+8f, top = iconY) {
                with(icon) {
                    draw(
                        size = Size(20.dp.toPx(), 20.dp.toPx())
                    )
                }
            }
        }
        // Clock component is placed at the bottom center of the progress bar
        ClockWidget(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(horizontal = 0.dp, vertical = 12.dp),
            onUpdateProgress = {
                progress = it
                startAngle = -180f
                Log.d("HalfCircleProgressBar", progress.toString())
            },
            onUpdateHour = {
                if (it in 6..18) {
                    colorProgress = shineGradient
                    icon = iconSun
                    colorCircle = yellowTransient
                } else {
                    colorProgress = nightGradient
                    icon = iconMoon
                    colorCircle = Color.LightGray
                }
            }
        )
    }
}



@SuppressLint("NewApi")
@Composable
fun HeaderUI(today: LocalDate) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(1f),
        verticalArrangement = aligned(alignment = Alignment.CenterVertically)
    ) {
        item {
            Column(Modifier.padding(16.dp)) {
                Row {
                    Text(
                        text = today.format(DateTimeFormatter.ofPattern("dd")),
                        style = typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.End,
                        fontSize = 68.sp,
                        modifier = Modifier.fillMaxHeight(1f)
                    )
                    Column {
                        Text(
                            text = today.format(DateTimeFormatter.ofPattern("MMMM")),
                            style = typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.End,
                            fontSize = 32.sp,
                            modifier = Modifier.fillMaxWidth(1f)
                        )
                        Text(
                            text = today.format(DateTimeFormatter.ofPattern("YYYY")),
                            style = typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.End,
                            fontSize = 27.sp,
                            modifier = Modifier.fillMaxWidth(1f)
                        )
                    }
                }
                Text(
                    text = today.format(DateTimeFormatter.ofPattern("YYYY")),
                    style = typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(1f)
                )
            }
        }

        item {
            Box(modifier = Modifier.fillMaxWidth(1f)) {
                HalfCircleProgressBar(modifier = Modifier
                    .clipToBounds()
                    .fillMaxWidth(1f)
                    .height(145.dp)
                    .padding(16.dp)
                )
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun KalenderUI(innerPadding: PaddingValues) {
    val month = Kalender.getToday().month
    val year = Kalender.getToday().year
    val daysOfMonth = Kalender.getDatesOfMonth(year, month)
    val firstDayOfMonth = Kalender.getDayInFirstMonth(year, month)
    val startOffset = if (firstDayOfMonth == DayOfWeek.MONDAY) 0 else firstDayOfMonth.value - 1
    val daysOfWeek = Kalender.getDaysInWeeks()

    /*Grid Layout*/
    Column() {
        HeaderUI(Kalender.getToday())
        KalenderWidget(innerPadding)
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