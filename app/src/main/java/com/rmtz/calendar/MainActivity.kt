package com.rmtz.calendar

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.aligned
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rmtz.calendar.libs.Image
import com.rmtz.calendar.libs.Kalender
import com.rmtz.calendar.ui.component.nightGradient
import com.rmtz.calendar.ui.component.shineGradient
import com.rmtz.calendar.ui.theme.FlatUiColors
import com.rmtz.calendar.ui.theme.FlatUiColors.inverse
import com.rmtz.calendar.ui.theme.pagi2
import com.rmtz.calendar.ui.theme.yellowTransient
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = FlatUiColors.BasicPallete.LightenDark
                ) {
                    CalendarUI()
                }
            }
        }
    }
}

@Composable
fun HalfCircleProgressBar(modifier: Modifier) {
    var progress by remember { mutableFloatStateOf(0f) }
    val iconSun = painterResource(id = R.drawable.ic_sun)
    val iconMoon = painterResource(id = R.drawable.ic_moon)
    var icon by remember { mutableStateOf(iconMoon) }
    var colorProgress by remember { mutableStateOf(nightGradient)}
    var colorCircle by remember { mutableStateOf(Color.LightGray)}

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
                startAngle = -180f,
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
            drawCircle(
                color = colorCircle,
                radius = 40f,
                center = Offset(iconX+27f, iconY+26f)
            )
            /* Draw Icon top of Circle Point */
            translate(left = iconX, top = iconY) {
                with(icon) {
                    draw(
                        size = Size(20.dp.toPx(), 20.dp.toPx())
                    )
                }
            }
        }
        // Clock component is placed at the bottom center of the progress bar
        Clock(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(horizontal = 0.dp, vertical = 12.dp),
            onUpdateProgress = {
                progress = it
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

@Composable
fun Clock(modifier: Modifier, onUpdateProgress: (Float) -> Unit, onUpdateHour: (Int) -> Unit) {
    var currentTime by remember { mutableStateOf(Date()) }

    val updateTimeMillis = 1000L // Update current time every second
    val updateProgressMillis = 1000 * 60L // Update progress every minute

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
            val calcDegrees = (elapsedMinutes.toFloat() / totalMinutes.toFloat()) * 180f
            var degrees = 0f
            Log.d("Clock Degree", calcDegrees.toString())

            if (currentHour in 6..18) {
                degrees = calcDegrees
            } else {
                degrees = calcDegrees - 180f
            }

            onUpdateProgress(degrees)
            onUpdateHour(currentHour)

            delay(updateProgressMillis)
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(currentTime),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 20.sp
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
            Box() {
                HalfCircleProgressBar(modifier = Modifier
                    .clipToBounds()
                    .fillMaxWidth(1f)
                    .height(140.dp)
                    .padding(16.dp))
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun CalendarUI() {
    val month = Kalender.getToday().month
    val year = Kalender.getToday().year

    // Generate days of the month
    val daysOfMonth = Kalender.getDatesOfMonth(year, month)
    val firstDayOfMonth = Kalender.getDayInFirstMonth(year, month)
    val startOffset = if (firstDayOfMonth == DayOfWeek.MONDAY) 0 else firstDayOfMonth.value - 1
    val daysOfWeek = Kalender.getDaysInWeeks()

    /*Grid Layout*/
    Column {
        HeaderUI(Kalender.getToday())
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    style = typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7), // 7 columns for 7 days of the week
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            repeat(startOffset) {
                item { Spacer(modifier = Modifier.aspectRatio(1f)) }
            }
            itemsIndexed(daysOfMonth) { index, day ->
                val isSelected = LocalDate.of(year, month, day) == Kalender.getToday()
                val isFriday = LocalDate.of(year, month, day).dayOfWeek == DayOfWeek.SATURDAY
                val isWeekend = LocalDate.of(year, month, day).dayOfWeek == DayOfWeek.SUNDAY
                ItemDate(
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

/* Kalender */
@Composable
fun ItemDate(day: Int, isSelected: Boolean, isFriday: Boolean, isWeekend: Boolean, offMessage: String, isHoliday: Boolean) {
    val backgroundColor = if(isSelected) {
        if (isHoliday) {
            FlatUiColors.GermanPallet.Desire.inverse()
        } else {
            FlatUiColors.GermanPallet.HighBlue
        }
    } else {
        Color.Transparent
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

@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    showSystemUi = true
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    showSystemUi = true
)

@Composable
fun GreetingPreview() {
    MaterialTheme {
        CalendarUI()
    }
}