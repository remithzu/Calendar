package com.rmtz.calendar.ui.component

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import com.rmtz.calendar.ui.theme.malam1
import com.rmtz.calendar.ui.theme.malam2
import com.rmtz.calendar.ui.theme.pagi1
import com.rmtz.calendar.ui.theme.pagi2
import com.rmtz.calendar.ui.theme.pagi3
import com.rmtz.calendar.ui.theme.siang1
import com.rmtz.calendar.ui.theme.siang2
import com.rmtz.calendar.ui.theme.sore1
import com.rmtz.calendar.ui.theme.sore2

val nightGradient = Brush.horizontalGradient(
    colorStops = arrayOf(
        0.0f to sore2,
        0.4f to malam1,
        0.7f to malam2,
        0.9f to pagi1,
        1.0f to pagi2
    )
)

/*#b5d6e0	(181,214,224)
#c7e1e5	(199,225,229)
#ffef7a	(255,239,122)
#f7c16a	(247,193,106)
#f46a55	(244,106,85)*/
val shineGradient = Brush.horizontalGradient(
    colorStops = arrayOf(
        0.0f to pagi3,
        0.4f to siang1,
        0.7f to siang2,
        0.9f to siang2,
        1.0f to sore1
    )
)

val largeRadialGradient = object : ShaderBrush() {
    override fun createShader(size: Size): Shader {
        val biggerDimension = maxOf(size.height, size.width)
        return RadialGradientShader(
            colors = listOf(Color(0xFF682612), Color(0xFF243484)),
            center = size.center,
            radius = biggerDimension / 2f,
            colorStops = listOf(0f, 0.95f)
        )
    }
}