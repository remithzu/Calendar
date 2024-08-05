package com.rmtz.calendar.widget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.action.Action
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.rmtz.calendar.MainActivity
import com.rmtz.calendar.R
import com.rmtz.calendar.libs.Kalender
import java.util.Locale

class KalenderWidget: GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val intent = Intent(context, MainActivity::class.java).apply {
        }
        val action = actionStartActivity(intent)

        provideContent {
            GlanceTheme {
                GlanceKalenderUI(action)
            }
        }
    }

    override fun onCompositionError(
        context: Context,
        glanceId: GlanceId,
        appWidgetId: Int,
        throwable: Throwable
    ) {
        super.onCompositionError(context, glanceId, appWidgetId, throwable)
        val rv = RemoteViews(context.packageName, R.layout.widget_error_layout)
        rv.setTextViewText(
            R.id.error_text_view,
            "Error was thrown. \nThis is a custom view \nError Message: `${throwable.message}`"
        )
        rv.setOnClickPendingIntent(R.id.error_icon, getErrorIntent(context, throwable))
        AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, rv)
    }

    private fun getErrorIntent(context: Context, throwable: Throwable): PendingIntent {
        val intent = Intent(context, KalenderWidget::class.java)
        intent.setAction("widgetError")
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    @SuppressLint("NewApi")
    @Composable
    fun GlanceKalenderUI(action: Action) {
        var today by remember { mutableStateOf(Kalender.getToday())}
        val holidays = Kalender.getHoliday(today)
        val context = LocalContext.current
        Box(GlanceModifier
            .fillMaxSize()
            .padding(16.dp, 8.dp)
            .clickable{
                Log.d("Kalender", "Clicked")
                action
            }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = today.dayOfMonth.toString(),
                    style = TextStyle(
                        fontSize = 54.sp,
                        color = ColorProvider(Color(0xFFd1d8e0))
                    )
                )

                Column {
                    Text(
                        text = today.month.getDisplayName(java.time.format.TextStyle.FULL, Locale("id")),
                        style = TextStyle(
                            fontSize = 22.sp,
                            color = ColorProvider(Color(0xFF4b7bec))
                        )
                    )
                    Text(
                        text = "${today.dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale("id"))} - ${Kalender.getDayOfJawa(today)}",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = ColorProvider(Color(0xff222f3e))
                        )
                    )
                }
            }

            Column(GlanceModifier.fillMaxSize(),Alignment.Bottom) {
                if (holidays.isNotEmpty()) {
                    holidays.forEach {
                        Text(
                            text = it.holiday,
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = ColorProvider(Color(0xFFeb3b5a))
                            )
                        )
                    }
                }
            }
        }
    }
}

