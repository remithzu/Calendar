package com.rmtz.calendar.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rmtz.calendar.ui.theme.AppTheme

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


@Composable
fun TestUI() {
    Container()
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