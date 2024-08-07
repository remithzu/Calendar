package com.rmtz.calendar.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import com.rmtz.calendar.service.KalenderWorker

class RefreshWidget: ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) { prefs ->
            prefs.clear()
        }
        KalenderWidget().update(context, glanceId)

        GlanceAppWidgetManager(context).getAppWidgetSizes(glanceId).forEach { size ->
            KalenderWorker.enqueue(context, glanceId, force = true)
        }
    }
}