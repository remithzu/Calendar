package com.rmtz.calendar

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rmtz.calendar.libs.Image
import com.rmtz.calendar.libs.Kalender
import com.rmtz.calendar.ui.component.nightGradient
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
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalendarUI()
                }
            }
        }
    }
}

@Composable
fun HalfCircleProgressBar(modifier: Modifier) {
    var progress by remember { mutableStateOf(0f) }
    val ctx = LocalContext.current
    val icon = painterResource(id = R.drawable.ic_moon)
    val bitmap = Image.getBitmapFromImage(ctx, R.drawable.ic_moon)

    Box(
        modifier = modifier
            .background(Color.Transparent)
    ) {
        Canvas(modifier) {
            // Draw gray background arc
            drawArc(
                color = Color.LightGray,
                startAngle = -180f,
                sweepAngle = 180f,
                useCenter = false,
                size = Size(size.width, size.height * 2),
                style = Stroke(8.dp.toPx(), cap = StrokeCap.Round)
            )

            // Draw green progress arc
            drawArc(
                brush = nightGradient,
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

            drawCircle(
                color = Color.LightGray,
                radius = 40f,
                center = Offset(iconX, iconY)
            )

            translate(left = iconX, top = iconY) {
                with(icon) {
                    //   draw(size = painter.intrinsicSize)
                    draw(
                        size = Size(20.dp.toPx(), 20.dp.toPx()),
                        alpha = 1f
                    )
                }
            }

            /*drawImage(
                image = bitmap.asImageBitmap(),
                dstRect = Rect(
                    left = iconX,
                    top = iconY,
                    right = iconX + iconSize,
                    bottom = iconY + iconSize
                )
            )*/


            /*val iconSize = 24.dp.toPx()
            val angleInRadians = Math.toRadians(-180.0 + progress.toDouble())
            val iconX = (size.width / 2) + (size.width / 2) * Math.cos(angleInRadians).toFloat() - iconSize / 2
            val iconY = (size.height) + (size.height) * Math.sin(angleInRadians).toFloat() - iconSize / 2

            drawIntoCanvas { canvas ->
                canvas.drawArc(
                    rect = Rect(),
                    startAngle = iconX,
                    sweepAngle = iconY
                )
                val iconPainter = icon
                with(iconPainter) {
                    draw(
                        size = Size(iconSize, iconSize),
                    )
                }
            }*/
        }
        // Clock component is placed at the bottom center of the progress bar
        Clock(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(horizontal = 0.dp, vertical = 12.dp),
            onUpdateProgress = {
                progress = it
                Log.d("HalfCircleProgressBar", it.toString())
            }
        )
    }
}

@Composable
fun Clock(modifier: Modifier, onUpdateProgress: (Float) -> Unit) {
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

// Calculate progress in degrees (0 to 180 degrees)
            /*val hourProgress = (currentHour * 180f / 12f) + (currentMinute * 15f / 60f) // Each hour = 15 degrees
            val minuteProgress = currentMinute * 3f // Each minute = 3 degrees
            val secondProgress = currentSecond * 3f // Each second = 3 degrees*/

            val elapsedMinutes = (currentHour - 6) * 60 + currentMinute
            val totalMinutes = 12 * 60
            val progress = (elapsedMinutes.toFloat() / totalMinutes.toFloat()) * 180f
            Log.d("Clock Degree", progress.toString())

            // Update progress callback for HalfCircleProgressBar
            onUpdateProgress(progress)

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
                    .height(130.dp)
                    .padding(16.dp))
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun CalendarUI() {
    val today = remember { LocalDate.now() }
    val month = today.month
    val year = today.year

    // Generate days of the month
    val daysOfMonth = Kalender.getDatesOfMonth(year, month)
    val firstDayOfMonth = Kalender.getDayInFirstMonth(year, month)
    val startOffset = if (firstDayOfMonth == DayOfWeek.MONDAY) 0 else firstDayOfMonth.value - 1
    val daysOfWeek = Kalender.getDaysInWeeks()

    /*Grid Layout*/
    Column {
        HeaderUI(today)
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
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
                val isSelected = LocalDate.of(year, month, day) == today
                val isWeekend = LocalDate.of(year, month, day).dayOfWeek == DayOfWeek.SATURDAY ||
                        LocalDate.of(year, month, day).dayOfWeek == DayOfWeek.SUNDAY
                CalendarDay(
                    day = day,
                    isSelected = isSelected,
                    isWeekend = isWeekend,
                    offMessage = Kalender.getDayOfJawa(LocalDate.of(year, month, day)), //javaDayOfWeek(LocalDate.of(year, month, day)),
                    isFirstRow = index < 7
                )
            }
        }
    }
}

@Composable
fun CalendarDay(day: Int, isSelected: Boolean, isWeekend: Boolean, offMessage: String, isFirstRow: Boolean) {
    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primaryContainer
        isWeekend -> Color.LightGray
        else -> Color.Transparent
    }
    val contentColor = if (isSelected || isWeekend) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .padding(4.dp),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.toString(),
                style = typography.headlineSmall,
                color = contentColor
            )
            Text(
                text = offMessage,
                style = typography.labelSmall,
                color = contentColor
            )
        }
    }
}

@SuppressLint("NewApi")
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
        CalendarUI()
    }
}