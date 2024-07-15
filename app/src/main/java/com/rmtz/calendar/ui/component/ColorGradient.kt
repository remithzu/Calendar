package com.rmtz.calendar.ui.component

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import com.rmtz.calendar.ui.theme.CanadianDark
import com.rmtz.calendar.ui.theme.GermanDark
import com.rmtz.calendar.ui.theme.GermanLight
import com.rmtz.calendar.ui.theme.inversePrimaryDark
import com.rmtz.calendar.ui.theme.onSurfaceVariantLightHighContrast
import com.rmtz.calendar.ui.theme.outlineLightHighContrast
import com.rmtz.calendar.ui.theme.primaryContainerDark
import com.rmtz.calendar.ui.theme.secondaryContainerDark

val nightGradient = Brush.horizontalGradient(
    colorStops = arrayOf(
        0.0f to inversePrimaryDark,
        0.1f to secondaryContainerDark,
        0.7f to outlineLightHighContrast,
        0.9f to secondaryContainerDark,
        1.0f to inversePrimaryDark)
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