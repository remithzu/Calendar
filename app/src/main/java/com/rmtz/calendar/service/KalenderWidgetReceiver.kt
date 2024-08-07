package com.rmtz.calendar.service

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.rmtz.calendar.widget.KalenderWidget

class KalenderWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = KalenderWidget()
}