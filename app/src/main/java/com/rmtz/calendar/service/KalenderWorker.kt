package com.rmtz.calendar.service

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.ui.unit.DpSize
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.rmtz.calendar.libs.Kalender
import com.rmtz.calendar.widget.KalenderWidget
import java.time.Duration

class KalenderWorker (
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    companion object {
        private val uniqueWorkName = KalenderWorker::class.java.simpleName

        @SuppressLint("NewApi")
        fun enqueue(context: Context, glanceId: GlanceId, force: Boolean) {
            val manager = WorkManager.getInstance(context)
            val requestBuilder = PeriodicWorkRequestBuilder<KalenderWorker>(Duration.ofMinutes(30))
            var workPolicy = ExistingPeriodicWorkPolicy.KEEP
            if (force) {
                workPolicy = ExistingPeriodicWorkPolicy.REPLACE
            }
            manager.enqueueUniquePeriodicWork(
                uniqueWorkName,
                workPolicy,
                requestBuilder.build()
            )
        }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(uniqueWorkName)
        }
    }

    override suspend fun doWork(): Result {
        val manager = GlanceAppWidgetManager(context)
        val glanceIds = manager.getGlanceIds(KalenderWidget::class.java)

        return try {
            updateWidget()
            Result.success()
        } catch (e: Exception) {
            Log.e(uniqueWorkName, "Error while loading widget", e)
            if (runAttemptCount < 10) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

    private suspend fun updateWidget() {
        val manager = GlanceAppWidgetManager(context)
        val glanceIds = manager.getGlanceIds(KalenderWidget::class.java)
        glanceIds.forEach { glanceId ->
            updateAppWidgetState(context, glanceId) { prefs ->
                prefs[KalenderWidget.kalender_today] = Kalender.getToday().toString()
                prefs[KalenderWidget.kalender_holidays] = "Picsum Photos"
            }
        }

        KalenderWidget().updateAll(context)
    }
}