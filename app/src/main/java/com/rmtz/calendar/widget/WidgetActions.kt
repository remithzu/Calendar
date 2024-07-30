package com.rmtz.calendar.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.action.ActionCallback
import com.rmtz.calendar.worker.KalenderWorker

class WidgetActions: ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        KalenderWidget().update(context, glanceId)

        GlanceAppWidgetManager(context).getAppWidgetSizes(glanceId).let { size ->
            KalenderWorker.enqueue(context, size.first(), glanceId, force = true)
        }
    }
}