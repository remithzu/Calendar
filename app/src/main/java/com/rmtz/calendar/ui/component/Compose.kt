package com.rmtz.calendar.ui.component

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rmtz.calendar.R
import com.rmtz.calendar.model.DateTag
import com.rmtz.calendar.model.Event
import com.rmtz.calendar.ui.theme.AppTheme
import com.rmtz.calendar.ui.theme.FlatUiColors
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

val roundedTopCornerShape = RoundedCornerShape(
    topStart = 12.dp,
    topEnd = 12.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)

val roundedBottomCornerShape = RoundedCornerShape(
    topStart = 0.dp,
    topEnd = 0.dp,
    bottomStart = 12.dp,
    bottomEnd = 12.dp
)

val roundedCornerShape = RoundedCornerShape(
    topStart = 12.dp,
    topEnd = 12.dp,
    bottomStart = 12.dp,
    bottomEnd = 12.dp
)

@Composable
fun Container(modifier: Modifier? = Modifier, content: @Composable () -> Unit? = {}) {
    val combinedModifier = if (modifier == Modifier) {
        Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(
                color = Color.LightGray,
                shape = roundedCornerShape
            )
    } else {
        modifier
    }!!

    Box(modifier = combinedModifier, content = {
        Box(modifier = Modifier.padding(16.dp,14.dp)){
            content()
        }
    })
}


@Composable
fun TextSmall(modifier: Modifier? = Modifier, text: String) {
    val combinedModifier = if (modifier == Modifier) {
        Modifier.padding(0.dp)
    } else {
        modifier
    }!!

    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.Light,
        modifier = combinedModifier
    )
}

@Composable
fun TextNormal(modifier: Modifier? = Modifier, text: String ) {
    val combinedModifier = if (modifier == Modifier) {
        Modifier.padding(0.dp)
    } else {
        modifier
    }!!

    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        modifier = combinedModifier
    )
}

@Composable
fun TextMedium(modifier: Modifier? = Modifier, text: String ) {
    val combinedModifier = if (modifier == Modifier) {
        Modifier.padding(0.dp)
    } else {
        modifier
    }!!

    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
        modifier = combinedModifier
    )
}

@Composable
fun TextLarge(modifier: Modifier? = Modifier, text: String ) {
    val combinedModifier = if (modifier == Modifier) {
        Modifier.padding(0.dp)
    } else {
        modifier
    }!!

    Text(
        text = text,
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = combinedModifier
    )
}

@Composable
fun TextSmallThin(modifier: Modifier? = Modifier, text: String ) {
    val combinedModifier = if (modifier == Modifier) {
        Modifier.padding(0.dp)
    } else {
        modifier
    }!!

    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.Thin,
        modifier = combinedModifier
    )
}

@Composable
fun TextNormalThin(modifier: Modifier? = Modifier, text: String ) {
    val combinedModifier = if (modifier == Modifier) {
        Modifier.padding(0.dp)
    } else {
        modifier
    }!!

    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Thin,
        modifier = combinedModifier
    )
}

@Composable
fun TextMediumThin(modifier: Modifier? = Modifier, text: String ) {
    val combinedModifier = if (modifier == Modifier) {
        Modifier.padding(0.dp)
    } else {
        modifier
    }!!

    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Thin,
        modifier = combinedModifier
    )
}

@Composable
fun TextLargeThin(modifier: Modifier? = Modifier, text: String ) {
    val combinedModifier = if (modifier == Modifier) {
        Modifier.padding(0.dp)
    } else {
        modifier
    }!!

    Text(
        text = text,
        fontSize = 28.sp,
        fontWeight = FontWeight.Thin,
        modifier = combinedModifier
    )
}

@Composable
fun TextSmallBold(modifier: Modifier? = Modifier, text: String ) {
    val combinedModifier = if (modifier == Modifier) {
        Modifier.padding(0.dp)
    } else {
        modifier
    }!!

    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        modifier = combinedModifier
    )
}

@Composable
fun TextNormalBold(modifier: Modifier? = Modifier, text: String ) {
    val combinedModifier = if (modifier == Modifier) {
        Modifier.padding(0.dp)
    } else {
        modifier
    }!!

    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = combinedModifier
    )
}

@Composable
fun TextMediumBold(modifier: Modifier? = Modifier, text: String ) {
    val combinedModifier = if (modifier == Modifier) {
        Modifier.padding(0.dp)
    } else {
        modifier
    }!!

    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = combinedModifier
    )
}

@Composable
fun TextLargeBold(modifier: Modifier? = Modifier, text: String ) {
    val combinedModifier = if (modifier == Modifier) {
        Modifier.padding(0.dp)
    } else {
        modifier
    }!!

    Text(
        text = text,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        modifier = combinedModifier
    )
}

@SuppressLint("NewApi")
@Composable
fun EventItem(event: Event, onClickListener: (Event) -> Unit? = {}) {
    Box(modifier = Modifier.clickable { onClickListener(event) }) {
        Container(Modifier.background(
            brush = if(isSystemInDarkTheme()) {
                horizontalStartDarkGradientTransparent
            } else {
                horizontalStartLightGradientTransparent
            },
            shape = roundedCornerShape
        )) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .paint(
                            painter = painterResource(id = R.drawable.ic_calendar_frame),
                        )
                ) {
                    Column() {
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(8.dp,4.dp,4.dp,4.dp),
                            textAlign = TextAlign.Start,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(700),
                            color = Color.DarkGray,
                            text = event.date.month.getDisplayName(TextStyle.SHORT, Locale("id"))
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(4.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight(700),
                            color = Color.DarkGray,
                            text = event.date.dayOfMonth.toString()
                        )
                    }
                }
                Column(Modifier.padding(16.dp, 16.dp)) {
                    TextMediumThin(text = event.holiday)
                    TextSmall(text = "${event.date.dayOfMonth} ${event.date.month.getDisplayName(TextStyle.SHORT, Locale("id"))} ${event.date.year}")
                }
            }
        }
    }
}


@SuppressLint("NewApi")
@Composable
fun TestUI() {
    val event = Event(
        date = LocalDate.now(),
        holiday = "Holiday",
        day = 3,
        month = 5,
        monthName = "Mey",
        year = 2024,
        tag = DateTag.Holiday
    )
    EventItem(event)
}

@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    showSystemUi = true
)

@Composable
fun ComposePreview() {
    AppTheme {
        TestUI()
    }
}