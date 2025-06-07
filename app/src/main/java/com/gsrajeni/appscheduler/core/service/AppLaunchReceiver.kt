package com.gsrajeni.appscheduler.core.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

class AppLaunchReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val packageName = intent.getStringExtra("packageName") ?: return
        val serviceIntent = Intent(context, AppLaunchService::class.java).apply {
            putExtra("packageName", packageName)
        }
        ContextCompat.startForegroundService(context, serviceIntent)
    }
}
