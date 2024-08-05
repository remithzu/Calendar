package com.rmtz.calendar.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.work.impl.utils.ForceStopRunnable

class WidgetBackgroundService: Service() {
    private val TAG = "WidgetBackground"
    @SuppressLint("RestrictedApi")
    private lateinit var mMinuteTickReceiver: ForceStopRunnable.BroadcastReceiver

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
    }
}